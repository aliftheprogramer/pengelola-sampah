package com.dicoding.picodiploma.loginwithanimation.view.main.ui.profil

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.loginwithanimation.R

class PanduanAdapter(private val panduanList: Array<String>) : RecyclerView.Adapter<PanduanAdapter.ViewHolder>() {

    private val expandedItems = mutableSetOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_panduan, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = panduanList[position]
        holder.itemView.setOnClickListener {
            if (expandedItems.contains(position)) {
                expandedItems.remove(position)
            } else {
                expandedItems.add(position)
            }
            notifyItemChanged(position)
        }

        // Mengatur tampilan berdasarkan status expand/collapse
        holder.detailsView.visibility = if (expandedItems.contains(position)) View.VISIBLE else View.GONE
    }

    override fun getItemCount(): Int = panduanList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textPanduan)
        val detailsView: View = itemView.findViewById(R.id.detailsView) // Tambahkan View untuk detail
    }
}
