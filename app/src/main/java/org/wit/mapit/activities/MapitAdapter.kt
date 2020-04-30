package org.wit.mapit.activities

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.card_mapit.view.*
import org.wit.mapit.R
import org.wit.mapit.helpers.readImageFromPath
import org.wit.mapit.models.MapitModel

interface MapitListener {
    fun onMapitClick(mapit: MapitModel)
}

class MapitAdapter constructor(private var mapits: List<MapitModel>,
                                   private val listener: MapitListener
) : RecyclerView.Adapter<MapitAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_mapit, parent, false))
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val mapit = mapits[holder.adapterPosition]
        holder.bind(mapit, listener)
    }



    override fun getItemCount(): Int = mapits.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(mapit: MapitModel, listener : MapitListener) {
            itemView.mapitTitle.text = mapit.title
            itemView.description.text = mapit.description
            itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, mapit.image))
            itemView.setOnClickListener { listener.onMapitClick(mapit) }
        }
    }
}