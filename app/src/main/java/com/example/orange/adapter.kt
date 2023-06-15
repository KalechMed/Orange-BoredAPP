package com.example.testtt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.orange.R
import com.example.orange.saveArrayList


class Adapter(private val data: ArrayList<String>) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTextView: TextView = itemView.findViewById(R.id.txtActivity)
        val delete: TextView = itemView.findViewById(R.id.delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.itemTextView.text = item

        holder.delete.setOnClickListener {

            data.removeAt(position)
            saveArrayList(holder.itemView.context, "myArrayListKey", data)
            notifyDataSetChanged()

        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}


