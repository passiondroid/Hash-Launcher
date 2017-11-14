package com.app.launcher.hash.ui.onboarding

import android.Manifest
import android.annotation.TargetApi
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ResolveInfo
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.WindowManager
import com.app.launcher.hash.R
import com.app.launcher.hash.data.model.App
import com.app.launcher.hash.ui.home.HomeActivity
import com.app.launcher.hash.util.Constants
import com.app.launcher.hash.util.RuntimePermissionHelper
import dagger.android.AndroidInjection
import io.realm.Realm
import javax.inject.Inject
import android.content.pm.PackageManager
import io.reactivex.annotations.NonNull


class OnboardingActivity : AppCompatActivity() {

    private var realm: Realm? = null

    @Inject
    lateinit var sharedPreferences : SharedPreferences

    private lateinit var permissionHelper: RuntimePermissionHelper;

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        setStatusBarColor()
        realm = Realm.getDefaultInstance()
        if (Build.VERSION.SDK_INT >= 23) {
            permissionHelper = RuntimePermissionHelper.getInstance(this)
            if(!permissionHelper.isPermissionAvailable(Manifest.permission.READ_CONTACTS)){
                permissionHelper.requestPermissionsIfDenied(Manifest.permission.READ_CONTACTS)
            }
        }else {
            loadApps()
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            var window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.icon_color));
        }
    }

    private fun loadApps(){
        var manager = packageManager

        realm?.executeTransactionAsync(object: Realm.Transaction{
            override fun execute(realm: Realm?) {
                var intent = Intent(Intent.ACTION_MAIN, null)
                intent.addCategory(Intent.CATEGORY_LAUNCHER)
                var availableActivities: List<ResolveInfo> = manager.queryIntentActivities(intent,0)
                for (resolveInfo in availableActivities){
                    var app = realm?.createObject(App::class.java)
                    app?.pckgName = resolveInfo.activityInfo.packageName
                    app?.name = resolveInfo.loadLabel(manager).toString()
                    app?.iconResource = resolveInfo.iconResource
                }

                runOnUiThread(object: Runnable{
                    override fun run() {
                        sharedPreferences.edit().putBoolean(Constants.ONBOARDING_DONE,true).commit()
                        var intent = Intent(this@OnboardingActivity,HomeActivity::class.java)
                        startActivity(intent)
                    }
                })
            }
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        for (i in grantResults) {
            if (i == PackageManager.PERMISSION_GRANTED) {
                //TODO: Read contacts
                loadApps()
            } else {
                permissionHelper.requestPermissionsIfDenied(Manifest.permission.READ_CONTACTS)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm?.close()
    }
}
