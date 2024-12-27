package com.dicoding.picodiploma.loginwithanimation.view.main.ui.order

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.data.database.datasampah.JemputSampah

class OrderAdapter(private var orderList: List<JemputSampah>, private val onDeleteClick: (JemputSampah) -> Unit) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_list, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orderList[position]
        holder.tvNama.text = order.nama
        holder.tvKategori.text = order.kategori
        holder.tvBerat.text = "${order.berat} kg"
        holder.tvTanggal.text = order.tanggal

        holder.imageDelete.setOnClickListener {
            onDeleteClick(order)
        }
    }

    override fun getItemCount(): Int = orderList.size

    fun updateOrders(newOrders: List<JemputSampah>) {
        orderList = newOrders
        notifyDataSetChanged()
    }

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNama: TextView = itemView.findViewById(R.id.tvNama)
        val tvKategori: TextView = itemView.findViewById(R.id.tvKategori)
        val tvBerat: TextView = itemView.findViewById(R.id.tvBerat)
        val tvTanggal: TextView = itemView.findViewById(R.id.tvTanggal)
        val imageDelete: ImageView = itemView.findViewById(R.id.imageDelete)
    }
}