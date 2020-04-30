package org.wit.mapit.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_mapit.*
import kotlinx.android.synthetic.main.activity_mapit.description
import kotlinx.android.synthetic.main.card_mapit.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.mapit.models.MapitModel
import org.wit.mapit.R
import org.wit.mapit.helpers.readImage
import org.wit.mapit.helpers.readImageFromPath
import org.wit.mapit.helpers.showImagePicker
import org.wit.mapit.main.MainApp
import org.wit.mapit.models.Location
import kotlinx.android.synthetic.main.activity_mapit.mapitTitle as mapitTitle1

class MapitActivity : AppCompatActivity(), AnkoLogger {

    var mapit = MapitModel()
    lateinit var app : MainApp
    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2
    //var location = Location(52.245696, -7.139102, 15f)
    var edit = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapit)
        app = application as MainApp
        //var edit = false
       // edit=true

        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)


        if (intent.hasExtra("mapit_edit")) {
            edit = true
            mapit = intent.extras.getParcelable<MapitModel>("mapit_edit")
            mapitTitle.setText(mapit.title)
            description.setText(mapit.description)
            mapitImage.setImageBitmap(readImageFromPath(this, mapit.image))
            if (mapit.image != null) {
                chooseImage.setText(R.string.change_mapit_image)
            }
            btnAdd.setText(R.string.save_mapit)
        }

        mapitLocation.setOnClickListener {
            val location = Location(52.245696, -7.139102, 15f)
            if (mapit.zoom != 0f) {
                location.lat =  mapit.lat
                location.lng = mapit.lng
                location.zoom = mapit.zoom
            }
            startActivityForResult(intentFor<MapsActivity>().putExtra("location", location), LOCATION_REQUEST)
        }

        btnAdd.setOnClickListener() {
            mapit.title = mapitTitle.text.toString()
            mapit.description = description.text.toString()
            if (mapit.title.isEmpty()) {
                toast(R.string.enter_mapit_title)
            } else {
                if (edit) {
                    app.mapits.update(mapit.copy())
                } else {
                    app.mapits.create(mapit.copy())
                }
            }
            info("add Button Pressed: $mapitTitle")
            setResult(AppCompatActivity.RESULT_OK)
            finish()
        }

        chooseImage.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
        }

    }

 /*   override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_mapit, menu)
        return super.onCreateOptionsMenu(menu)
    }*/

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_mapit, menu)
        if (edit && menu != null) menu.getItem(0).setVisible(true)
        return super.onCreateOptionsMenu(menu)
    }

/*    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }*/


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_delete -> {
                app.mapits.delete(mapit)
                finish()
            }
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    mapit.image = data.getData().toString()
                    mapitImage.setImageBitmap(readImage(this, resultCode, data))
                    chooseImage.setText(R.string.change_mapit_image)
                }
            }

            LOCATION_REQUEST -> {
                if (data != null) {
                    val location = data.extras.getParcelable<Location>("location")
                    mapit.lat = location.lat
                    mapit.lng = location.lng
                    mapit.zoom = location.zoom
                }
            }

        }
}}