package com.example.examenitems.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.examenitems.R
import com.example.examenitems.models.Item

class ItemAdapter : ListAdapter<Item, ItemAdapter.ItemHolder>(DiffCallback()) {

    class ItemHolder(view: View) : RecyclerView.ViewHolder(view)

    private lateinit var listener: RecyclerClickListener

    fun setItemListener(listener: RecyclerClickListener) {
        this.listener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.card_list, parent, false)

        val itemHolder = ItemHolder(v)

        val itemClick = itemHolder.itemView.findViewById<CardView>(R.id.cardView)
        itemClick.setOnClickListener {
            listener.onItemClick(itemHolder.adapterPosition)
        }

        return itemHolder
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val currentItem = getItem(position)
        val itemName = holder.itemView.findViewById<TextView>(R.id.idNombre)
        val itemPrice = holder.itemView.findViewById<TextView>(R.id.idPreu)
        val itemImg = holder.itemView.findViewById<ImageView>(R.id.idImagen)

        itemName.text = currentItem.nom
        itemPrice.text = "${currentItem.preu.toString()}€"
        itemImg.setBackgroundResource(searchImage(currentItem.nom))
    }

    class DiffCallback : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item) =
            oldItem.nom == newItem.nom

        override fun areContentsTheSame(oldItem: Item, newItem: Item) =
            oldItem == newItem
    }

    fun searchImage(imgName: String): Int {
        when(imgName) {
            "Cadira" -> return R.drawable.cadira
            "Taula" -> return R.drawable.taula
            "Sofa" -> return R.drawable.sofa
            "Paperera" -> return R.drawable.paperera
        }
        return R.drawable.baseline_disabled_by_default_24
    }
}