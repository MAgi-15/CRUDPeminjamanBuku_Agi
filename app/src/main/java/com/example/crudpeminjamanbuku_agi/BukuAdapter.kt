package com.example.crudpeminjamanbuku_agi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crudpeminjamanbuku_agi.room.Buku
import kotlinx.android.synthetic.main.list_buku.view.*

class BukuAdapter (private val bukus: ArrayList<Buku>, private val listener: OnAdapterListener)
    : RecyclerView.Adapter<BukuAdapter.BukuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BukuViewHolder {
        return BukuViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_buku, parent, false)
        )
    }

    override fun getItemCount() = bukus.size

    override fun onBindViewHolder(holder: BukuViewHolder, position: Int) {
        val buku = bukus[position]
        holder.view.text_judul.text = buku.judul
        holder.view.text_judul.setOnClickListener {
            listener.onClick(buku)
        }
        holder.view.icon_edit.setOnClickListener {
            listener.onUpdate(buku)
        }
        holder.view.icon_delete.setOnClickListener {
            listener.onDelete(buku)
        }
    }

    class BukuViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(list: List<Buku>) {
        bukus.clear()
        bukus.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(buku: Buku)
        fun onUpdate(buku: Buku)
        fun onDelete(buku: Buku)
    }
}