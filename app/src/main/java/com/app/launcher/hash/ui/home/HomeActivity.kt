package com.app.launcher.hash.ui.home

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v4.view.ViewPager.OnPageChangeListener
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import com.app.launcher.hash.R
import com.app.launcher.hash.rxBus.MainFragmentVisibleEvent
import com.app.launcher.hash.rxBus.RxBus
import com.app.launcher.hash.ui.adapter.ViewPagerAdapter
import com.app.launcher.hash.ui.home.main.MainFragment
import com.app.launcher.hash.ui.home.news.NewsFragment
import com.app.launcher.hash.ui.onboarding.OnboardingActivity
import com.app.launcher.hash.util.Constants
import com.github.nisrulz.sensey.Sensey
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


class HomeActivity : AppCompatActivity(), HasSupportFragmentInjector{

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var viewPagerAdapter: ViewPagerAdapter

    @Inject
    lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(!sharedPreferences.getBoolean(Constants.ONBOARDING_DONE,false)){
            var intent = Intent(this@HomeActivity,OnboardingActivity::class.java)
            startActivity(intent)
            finish()
        }

        Sensey.getInstance().init(this);

//        startAwarenessApi();

        init()

    }

    private fun init() {
        val viewpager : ViewPager = findViewById(R.id.viewpager)
        viewPagerAdapter?.addFragment(NewsFragment.newInstance())
        viewPagerAdapter?.addFragment(MainFragment.newInstance())
        viewpager.adapter = viewPagerAdapter
        viewpager.currentItem = 1
        viewpager.addOnPageChangeListener(object: OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                if(position == 1)
                    RxBus.publish(MainFragmentVisibleEvent(true))
                else
                    RxBus.publish(MainFragmentVisibleEvent(false))
            }
        })
    }

    private fun startAwarenessApi() {

//        Awareness.SnapshotApi.getDetectedActivity()
    }

    override fun onBackPressed() {
        //Do Nothing
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentDispatchingAndroidInjector
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        Sensey.getInstance().setupDispatchTouchEvent(event);
        return super.dispatchTouchEvent(event)
    }

    override fun onDestroy() {
        super.onDestroy()
        Sensey.getInstance().stop();
    }
}
