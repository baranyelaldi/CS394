package com.example.debtmanager

import android.graphics.Movie
import android.net.Uri
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        // Use Uri.parse to create a Uri from the imgUrl
        val imgUri = Uri.parse(imgUrl)

        // Use the Glide library to load the image into the ImageView
        Glide.with(imgView)
            .load(imgUri)
            .into(imgView)
    }
}