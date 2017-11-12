package com.app.launcher.hash.ui.applist

import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import com.app.launcher.hash.R
import com.app.launcher.hash.data.model.App
import com.app.launcher.hash.ui.adapter.GridAdapter
import io.realm.Realm
import io.realm.RealmChangeListener
import io.realm.RealmResults

class AppsListActivity : AppCompatActivity() {

    private lateinit var manager: PackageManager
    private lateinit var applications: ArrayList<App>
    private lateinit var grid: GridView
    private var realm: Realm? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apps_list)
        grid = findViewById(R.id.grid)
        loadApps()
        addClickListener()
    }

    private fun loadApps(){
        manager = packageManager
        applications = ArrayList<App>()

        var realm = Realm.getDefaultInstance()
        var apps: RealmResults<App> = realm.where(App::class.java).findAllSortedAsync("name")
        apps.addChangeListener( object : RealmChangeListener<RealmResults<App>>{
            override fun onChange(t: RealmResults<App>?) {
                for(app in t!!){
                    applications.add(app)
                }
                loadGridView()
            }
        })
    }

    private fun loadGridView(){
        val adapterGrid = GridAdapter(applications)
        grid.adapter=adapterGrid

    }

    private fun addClickListener(){
        grid.setOnItemClickListener(object : AdapterView.OnItemClickListener{
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var intent: Intent = manager.getLaunchIntentForPackage(applications.get(p2).pckgName.toString())
                startActivity(intent)
                finish()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        realm?.close()
    }

}
