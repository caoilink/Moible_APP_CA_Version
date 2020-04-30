package org.wit.mapit.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.mapit.helpers.*
import java.util.*

val JSON_FILE = "mapits.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<MapitModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class MapitJSONStore : MapitStore, AnkoLogger {

    val context: Context
    var mapits = mutableListOf<MapitModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<MapitModel> {
        return mapits
    }

    override fun create(mapit: MapitModel) {
        mapit.id = generateRandomId()
        mapits.add(mapit)
        serialize()
    }


    override fun update(mapit: MapitModel) {
        val mapitsList = findAll() as ArrayList<MapitModel>
        var foundMapit: MapitModel? = mapitsList.find { p -> p.id == mapit.id }
        if (foundMapit != null) {
            foundMapit.title = mapit.title
            foundMapit.description = mapit.description
            foundMapit.image = mapit.image
            foundMapit.lat = mapit.lat
            foundMapit.lng = mapit.lng
            foundMapit.zoom = mapit.zoom
        }
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(mapits, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        mapits = Gson().fromJson(jsonString, listType)
    }

    override fun delete(mapit: MapitModel) {
        mapits.remove(mapit)
        serialize()
    }
}
