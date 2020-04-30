package org.wit.mapit.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.mapit.models.MapitJSONStore
import org.wit.mapit.models.MapitMemStore
import org.wit.mapit.models.MapitStore

class MainApp : Application(), AnkoLogger {

    lateinit var mapits: MapitStore

    override fun onCreate() {
        super.onCreate()
        mapits = MapitJSONStore(applicationContext)
        info("mapit started")
    }
}