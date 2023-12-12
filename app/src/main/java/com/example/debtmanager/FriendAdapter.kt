package com.example.debtmanager

import Friend
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

class FriendAdapter(private val friendList:ArrayList<Friend>) : RecyclerView.Adapter<FriendAdapter.FriendViewHolder>() {

    private lateinit var mListener : onItemClickListener

    interface onItemClickListener{

        fun onItemClick(position : Int)

    }

    fun setOnItemClickListener(listener: onItemClickListener){

        mListener = listener

    }

    class FriendViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val imageView : ShapeableImageView = itemView.findViewById(R.id.title_image)
        val nameView : TextView = itemView.findViewById(R.id.name)
        val debtView : TextView = itemView.findViewById(R.id.debt)

        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.friend, parent, false)
        return FriendViewHolder(view, mListener)
    }

    override fun getItemCount(): Int {
        return friendList.size
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val friend = friendList[position]
        holder.imageView.setImageResource(friend.image)
        holder.nameView.text = friend.name
        holder.debtView.text = friend.debt.toString()

        if (friend.debt < 0) {
            holder.debtView.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.green))
        } else if (friend.debt > 0) {
            holder.debtView.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.red))
        }
    }
}