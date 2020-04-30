package org.wit.mapit.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class MapitMemStore : MapitStore, AnkoLogger {

    val mapits = ArrayList<MapitModel>()

    override fun findAll(): List<MapitModel> {
        return mapits
    }

    override fun create(mapit: MapitModel) {
        mapit.id = getId()
        mapits.add(mapit)
        logAll()
    }

    override fun update(mapit: MapitModel) {
        var foundMapit: MapitModel? = mapits.find { p -> p.id == mapit.id }
        if (foundMapit != null) {
            foundMapit.title = mapit.title
            foundMapit.description = mapit.description
            foundMapit.image = mapit.image
            foundMapit.lat = mapit.lat
            foundMapit.lng = mapit.lng
            foundMapit.zoom = mapit.zoom
            logAll()
        }
    }

    override fun delete(mapit: MapitModel) {
        mapits.remove(mapit)
    }

    fun logAll(){
        mapits.forEach {info("${it}")}
    }

}