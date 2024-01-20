package com.example.debtmanager

import Friend
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

class FriendAdapter(private val mListener: OnItemClickListener) :
    ListAdapter<Friend, FriendAdapter.FriendViewHolder>(FriendDiffCallback()) {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    class FriendViewHolder(itemView: View, private val listener: OnItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val imageView: ShapeableImageView = itemView.findViewById(R.id.title_image)
        val nameView: TextView = itemView.findViewById(R.id.name)
        val debtView: TextView = itemView.findViewById(R.id.debt)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.friend, parent, false)
        return FriendViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val friend = getItem(position)
        holder.imageView.setImageResource(friend.image)
        holder.nameView.text = friend.name
        holder.debtView.text = friend.debt.toString()

        if (friend.debt < 0) {
            holder.debtView.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.green
                )
            )
        } else if (friend.debt > 0) {
            holder.debtView.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.red
                )
            )
        }
    }

    class FriendDiffCallback : DiffUtil.ItemCallback<Friend>() {
        override fun areItemsTheSame(oldItem: Friend, newItem: Friend): Boolean {
            return oldItem.name == newItem.name // Assuming name is unique
        }

        override fun areContentsTheSame(oldItem: Friend, newItem: Friend): Boolean {
            return oldItem == newItem
        }
    }
}