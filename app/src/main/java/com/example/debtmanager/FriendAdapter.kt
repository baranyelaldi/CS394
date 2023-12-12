package com.example.debtmanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

class FriendAdapter(private val friendList:ArrayList<Friend>) : RecyclerView.Adapter<FriendAdapter.FriendViewHolder>() {

    class FriendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView : ShapeableImageView = itemView.findViewById(R.id.title_image)
        val nameView : TextView = itemView.findViewById(R.id.name)
        val debtView : TextView = itemView.findViewById(R.id.debt)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.friend, parent, false)
        return FriendViewHolder(view)
    }

    override fun getItemCount(): Int {
        return friendList.size
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val friend = friendList[position]
        holder.imageView.setImageResource(friend.image)
        holder.nameView.text = friend.name
        holder.debtView.text = friend.debt.toString()
    }
}