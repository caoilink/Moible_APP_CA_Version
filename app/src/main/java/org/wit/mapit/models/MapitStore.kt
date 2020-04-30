package org.wit.mapit.models

interface MapitStore {
    fun findAll(): List<MapitModel>
    fun create(mapit: MapitModel)
    fun update(mapit: MapitModel)
    fun delete(mapit: MapitModel)
}