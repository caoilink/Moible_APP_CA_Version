package org.wit.mapit.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.*
import kotlinx.android.synthetic.main.activity_mapit_list.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.wit.mapit.R
import org.wit.mapit.main.MainApp
import org.wit.mapit.models.MapitModel

class MapitListActivity : AppCompatActivity(),
    MapitListener {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapit_list)
        app = application as MainApp

        //layout and populate for display
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager   //recyclerView is a widget in activity_mapit_list.xml
        loadMapits()

        //enable action bar and set title
        toolbarMain.title = title
        setSupportActionBar(toolbarMain)
    }

  /*  override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapit_list)
        app = application as MainApp

        val layoutManager =
            LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = MapitAdapter(
            app.mapits.findAll(),
            this
        )

        toolbarMain.title = title
        setSupportActionBar(toolbarMain)
    }*/

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_add -> startActivityForResult<MapitActivity>(0)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onMapitClick(mapit: MapitModel) {
        startActivityForResult(intentFor<MapitActivity>().putExtra("mapit_edit", mapit), 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadMapits()
        super.onActivityResult(requestCode, resultCode, data)
    }

/*    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //recyclerView is a widget in activity_mapit_list.xml
        recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }*/

    private fun loadMapits() {
        showMapits(app.mapits.findAll())
    }

    fun showMapits (mapits: List<MapitModel>) {
        recyclerView.adapter = MapitAdapter(mapits, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }
}