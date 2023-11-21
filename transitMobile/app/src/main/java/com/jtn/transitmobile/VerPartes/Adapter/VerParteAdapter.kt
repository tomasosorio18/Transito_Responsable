package com.jtn.transitmobile.VerPartes.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.jtn.transitmobile.Commons.Parte
import com.jtn.transitmobile.R

class VerParteAdapter(context: Context, resource: Int, parte: List<Parte>):
    ArrayAdapter<Parte>(context, resource, parte) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.activity_product_adapter, parent, false)
        }

        val currentItem = getItem(position)

        val itemNameTextView: TextView? = itemView?.findViewById(R.id.itemNameTextView)
        val itemDescriptionTextView: TextView? = itemView?.findViewById(R.id.itemDescriptionTextView)

        itemNameTextView?.text = currentItem?.nombre
        itemDescriptionTextView?.text = currentItem?.apellido_paterno


        return itemView!!
    }
}