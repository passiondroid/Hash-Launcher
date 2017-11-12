package com.app.launcher.hash.ui.home.main

import android.animation.Animator
import android.annotation.TargetApi
import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatEditText
import android.support.v7.widget.LinearLayoutManager
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.RelativeSizeSpan
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.app.launcher.hash.R
import com.app.launcher.hash.data.model.App
import com.app.launcher.hash.rxBus.MainFragmentVisibleEvent
import com.app.launcher.hash.rxBus.RxBus
import com.app.launcher.hash.rxBus.ScanButtonClickEvent
import com.app.launcher.hash.ui.adapter.SearchRVAdapter
import com.app.launcher.hash.ui.applist.AppsListActivity
import com.app.launcher.hash.ui.base.BaseFragment
import com.app.launcher.hash.ui.interfaces.OnListFragmentInteractionListener
import com.app.launcher.hash.util.Constants
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.github.nisrulz.sensey.Sensey
import com.github.nisrulz.sensey.TouchTypeDetector
import com.github.nisrulz.sensey.TouchTypeDetector.TouchTypListener
import com.google.zxing.integration.android.IntentIntegrator
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.android.support.AndroidSupportInjection
import io.reactivex.functions.Consumer
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.fragment_main.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class MainFragment : BaseFragment(), MainFragmentMvpView, OnListFragmentInteractionListener {

    @Inject
    lateinit var presenter : MainFragmentMvpPresenter<MainFragmentMvpView>

    @Inject
    lateinit var sharedPreferences : SharedPreferences

    @Inject
    lateinit var searchRVAdapter : SearchRVAdapter

    @Inject
    lateinit var linearLayoutManager : LinearLayoutManager

    private var searchClockSubTextFactor = 0.5f
    private lateinit var clockText : TextView
    private lateinit var searchET : AppCompatEditText
    private lateinit var realm: Realm

    //TODO: Add a repository pattern
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view : View = inflater!!.inflate(R.layout.fragment_main, container, false)
        clockText = view.findViewById(R.id.clockText)
        searchET = view.findViewById(R.id.searchET)
        setStatusBarGradiant()
        updateClock()
        setUpGestureListener()
        return view
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun setUp(view: View?) {
        presenter.onAttach(this)
        presenter.searchAppsAndContacts(RxTextView.afterTextChangeEvents(searchET))
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = searchRVAdapter
        searchRVAdapter.setListFragmentInteractionListener(this)

        //TODO: Uncomment
//        presenter.getUnsplashWallpaper()

        setIcons()

        homeBtn.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                var intent = Intent(activity, AppsListActivity::class.java)
                startActivity(intent)
            }
        })
        searchIV.setOnClickListener(object: View.OnClickListener{
            override fun onClick(view: View?) {
                var animation = searchIV.getDrawable();
                if(searchET.visibility == View.INVISIBLE) {
                    if (animation is Animatable) {
                        animation.start()
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            enterReveal()
                        } else {
                            searchET.visibility = View.VISIBLE
                        }

                    }
                }else{
                    if (animation is Animatable) {
                        animation.stop();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            exitReveal()
                            recyclerView.visibility = View.GONE
                        } else {
                            searchET.visibility = View.INVISIBLE
                            recyclerView.visibility = View.GONE
                        }
                        searchET.setText("")
                        hideKeyboard()
                    }
                }
            }
        })

        RxBus.subscribe(object : Consumer<Any>{
            override fun accept(t: Any?) {
                if( t is MainFragmentVisibleEvent) {
                    if(t.isVisible){
                        setUpGestureListener()
                    }else{
                        Sensey.getInstance().stopTouchTypeDetection();
                    }
                }
            }

        }, object: Consumer<Throwable> {
            override fun accept(t: Throwable?) {
                Log.e("Error","Exception", t)
            }

        })

        RxBus.subscribe(object : Consumer<Any>{
            override fun accept(t: Any?) {
                if( t is ScanButtonClickEvent) {
                    try {
                        IntentIntegrator.forSupportFragment(this@MainFragment).initiateScan()
                    }catch (e: Exception){
                        Toast.makeText(activity,"Failed to Start Camera",Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }, object: Consumer<Throwable> {
            override fun accept(t: Throwable?) {
                Log.e("Error","Exception", t)
            }
        })
    }

    private fun setIcons() {
        realm = Realm.getDefaultInstance()
        var results = realm?.where(App::class.java)
                .equalTo("name", "Phone").or()
                .equalTo("name", "Camera").or()
                .equalTo("name", "WhatsApp").or()
                .equalTo("name", "Chrome")
                .findAll()

        for(app in results){
            if(app.name.equals("Phone")) {
                icon4.setImageDrawable(loadIcon(app.pckgName))
                icon4.setOnClickListener(object : View.OnClickListener{
                    override fun onClick(p0: View?) {
                        startAppActivity(app.pckgName)
                    }
                })
            }
            if(app.name.equals("Camera")) {
                icon5.setImageDrawable(loadIcon(app.pckgName))
                icon5.setOnClickListener(object : View.OnClickListener{
                    override fun onClick(p0: View?) {
                        startAppActivity(app.pckgName)
                    }
                })
            }
            if(app.name.equals("WhatsApp")) {
                icon2.setImageDrawable(loadIcon(app.pckgName))
                icon2.setOnClickListener(object : View.OnClickListener{
                    override fun onClick(p0: View?) {
                        startAppActivity(app.pckgName)
                    }
                })
            }
            if(app.name.equals("Chrome")) {
                icon3.setImageDrawable(loadIcon(app.pckgName))
                icon3.setOnClickListener(object : View.OnClickListener{
                    override fun onClick(p0: View?) {
                        startAppActivity(app.pckgName)
                    }
                })
            }
        }
    }

    override fun onListFragmentInteraction(view: View?, `object`: Any?, position: Int) {
        var app = `object` as App
        var intent: Intent = activity.packageManager.getLaunchIntentForPackage(app.pckgName)
        startActivity(intent)
        searchET.visibility = View.INVISIBLE
        searchET.setText("")
        recyclerView.visibility = View.INVISIBLE
        topView.visibility = View.GONE
    }

    private fun startAppActivity(pckgName: String){
        val launchIntent = activity.getPackageManager().getLaunchIntentForPackage(pckgName)
        startActivity(launchIntent)
    }

    private fun setUpGestureListener() {
        var touchTypListener = object: TouchTypListener {
            override fun onSwipe(swipeDirection: Int) {
                when (swipeDirection) {
                // Swipe Up
                    TouchTypeDetector.SWIPE_DIR_UP -> {
                        sharedPreferences.edit().putBoolean(Constants.WALLPAPER_FREEZE, true).commit()
                        Toast.makeText(context, "Wallpaper freezed", Toast.LENGTH_SHORT).show()
                    }
                    TouchTypeDetector.SWIPE_DIR_DOWN -> {
                        sharedPreferences.edit().putBoolean(Constants.WALLPAPER_FREEZE, false).commit()
                        Toast.makeText(context, "Double tap to change the wallpaper", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        //Do Nothing
                    }
                }
            }

            override fun onSingleTap() {
                //TODO("not implemented")
            }

            override fun onScroll(p0: Int) {
                //TODO("not implemented")
            }

            override fun onLongPress() {
                //TODO("not implemented")
            }

            override fun onTwoFingerSingleTap() {
                //TODO("not implemented")
            }

            override fun onThreeFingerSingleTap() {
                //TODO("not implemented")
            }

            override fun onDoubleTap() {
                if(!sharedPreferences.getBoolean(Constants.WALLPAPER_FREEZE,false)) {
                    presenter.getUnsplashWallpaper()
                    Toast.makeText(activity, "Loading New Wallpaper", Toast.LENGTH_SHORT).show()
                }
            }
        }
        Sensey.getInstance().startTouchTypeDetection(context,touchTypListener);
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment NewsFragment.
         */
        fun newInstance(): MainFragment {
            val fragment = MainFragment()
            return fragment
        }
    }


    fun updateClock() {
        val calendar = Calendar.getInstance(Locale.getDefault())
        var sdf: SimpleDateFormat? = SimpleDateFormat("MMMM dd'\n'EEEE',' yyyy", Locale.getDefault())
        val text = sdf?.format(calendar.time)
        val lines = text?.split("\n".toRegex())?.dropLastWhile { it.isEmpty() }?.toTypedArray()
        val span = SpannableString(text)
        span.setSpan(RelativeSizeSpan(searchClockSubTextFactor), lines!![0].length + 1, lines[0].length + 1 + lines[1].length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        clockText.setText(span)
    }

    override fun updateWallpaper(imageUrl: String) {
        val wallpaperManager = WallpaperManager.getInstance(activity.applicationContext)
        Glide.with(activity.applicationContext)
                .load(imageUrl)
                .asBitmap()
                .into(object : SimpleTarget<Bitmap>(){
                    override fun onResourceReady(resource: Bitmap?, glideAnimation: GlideAnimation<in Bitmap>?) {
//                        Palette.from(resource).maximumColorCount(16).generate(object: Palette.PaletteAsyncListener {
//                            override fun onGenerated(palette: Palette?) {
//                                // Get the "vibrant" color swatch based on the bitmap
//                                var vibrantSwatch: Palette.Swatch? = palette?.getVibrantSwatch()
//                                var darkvibrantSwatch: Palette.Swatch? = palette?.getDarkVibrantSwatch()
//                                var lightVibrantSwatch: Palette.Swatch? = palette?.getLightVibrantSwatch()
//                                var mutedSwatch: Palette.Swatch? = palette?.getMutedSwatch()
//                                var darkMutedSwatch: Palette.Swatch? = palette?.getDarkMutedSwatch()
//                                var lightMutedSwatch: Palette.Swatch? = palette?.getLightMutedSwatch()
//                                if (lightVibrantSwatch != null) {
//                                    clockText.setTextColor(lightVibrantSwatch.titleTextColor);
//                                }
//
//
//                                /** Needed to set status bar color when set from color pallete*/
//                                // finally change the color
//                                // if(lightVibrantSwatch != null)
////                                setStatusBarGradiant()
//                            }
//                        });

                        wallpaperManager.setBitmap(resource)
                        Toast.makeText(activity.applicationContext,"New Wallpaper Set",Toast.LENGTH_SHORT).show()
                    }
                })
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun setStatusBarGradiant() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            var window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(activity.getResources().getColor(R.color.colorPrimaryDark));
            window.setNavigationBarColor(activity.getResources().getColor(R.color.transparent));
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun enterReveal() {
        // get the center for the clipping circle
        var cx = searchET.getMeasuredWidth()
        var cy = searchET.getMeasuredHeight()

        // get the final radius for the clipping circle
        var finalRadius = Math.max(searchET.getWidth(), searchET.getHeight()) ;

        // create the animator for this view (the start radius is zero)
        var anim  = ViewAnimationUtils.createCircularReveal(searchET, cx, cy, 0f, finalRadius.toFloat())

        // make the view visible and start the animation
        searchET.setVisibility(View.VISIBLE);
        anim.start();
        anim.addListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(p0: Animator?) {

            }

            override fun onAnimationCancel(p0: Animator?) {

            }

            override fun onAnimationStart(p0: Animator?) {

            }

            override fun onAnimationEnd(animtor: Animator?) {
                topView.visibility = View.VISIBLE
                var window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setNavigationBarColor(activity.getResources().getColor(R.color.dark_gray));
                showKeyboard()
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun exitReveal() {
        // get the center for the clipping circle
        var cx = searchET.getMeasuredWidth()
        var cy = searchET.getMeasuredHeight()

        // get the initial radius for the clipping circle
        var initialRadius = searchET.getWidth();

        // create the animation (the final radius is zero)
        var anim = ViewAnimationUtils.createCircularReveal(searchET, cx, cy, initialRadius.toFloat(), 0f);

        anim.start();
        anim.addListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(p0: Animator?) {

            }

            override fun onAnimationCancel(p0: Animator?) {

            }

            override fun onAnimationStart(p0: Animator?) {

            }

            override fun onAnimationEnd(animtor: Animator?) {
                searchET.setVisibility(View.INVISIBLE);
                topView.visibility = View.GONE
                var window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setNavigationBarColor(activity.getResources().getColor(R.color.transparent));
            }

        })
    }

    override fun showAppsAndContacts(apps: List<App>) {
        println("app = [${apps}]")
        recyclerView.visibility = View.VISIBLE
        searchRVAdapter.refreshItems(apps);

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
            } else {
                println("result === "+result.getContents())
                var uri = Uri.parse(result.contents)
                var intent = Intent()
                intent.setAction(Intent.ACTION_VIEW)
                intent.setData(uri)
                var manager = context.getPackageManager();
                var infos = manager.queryIntentActivities(intent, 0);
                if (infos.size > 0) {
                    startActivity(intent)
                } else {
                    Toast.makeText(activity,"No Activity found to handle this action",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun loadIcon(packageName: String): Drawable {
    try {
        if (packageName != null) {
            val icon = activity.packageManager.getApplicationIcon(packageName)
            return icon;
        }
    } catch (e: PackageManager.NameNotFoundException) {
        Log.e("MainFragment","Exception", e)
    }
    return ContextCompat.getDrawable(activity,R.mipmap.ic_launcher_round)
}

    fun hideKeyboard() {
        if (searchET != null) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(searchET.windowToken, 0)
        }
    }

    fun showKeyboard(){
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(searchET, InputMethodManager.SHOW_IMPLICIT)
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

}

