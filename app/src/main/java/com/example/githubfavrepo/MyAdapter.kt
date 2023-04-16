package com.example.githubfavrepo

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    private val dataList = mutableListOf<Pair<Long, Quadruple<String, String, String, String>>>()

    fun addData(data: List<Pair<Long, Quadruple<String, String, String, String>>>) {
        dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.repo_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.nameView.text = item.second.first
        holder.descriptionView.text = item.second.second
        holder.linkView.text = item.second.third
        holder.ownerView.text = item.second.fourth
        holder.shareButton.setOnClickListener {
            val name = item.second.first
            val description = item.second.second
            val link = item.second.third
            val owner = item.second.fourth
            val shareText = "Repo name: $name\n\n Description:$description\n\nUrl:$link\n\nOwner name:$owner"
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, shareText)
            }
            holder.itemView.context.startActivity(Intent.createChooser(intent, "Share via"))
        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameView = itemView.findViewById<TextView>(R.id.repo_name)
        val descriptionView = itemView.findViewById<TextView>(R.id.repo_description)
        val linkView = itemView.findViewById<TextView>(R.id.repo_link)
        val ownerView = itemView.findViewById<TextView>(R.id.repo_owner)
        val shareButton = itemView.findViewById<ImageButton>(R.id.shareButton)
        val cardLayout = itemView.findViewById<RelativeLayout>(R.id.card_layout)
        init {


        cardLayout.setOnClickListener {
            val link = linkView.text.toString()
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            itemView.context.startActivity(intent)
        }}
    }
}