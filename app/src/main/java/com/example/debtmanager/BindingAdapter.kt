package com.example.debtmanager


import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = Uri.parse(imgUrl)

        Glide.with(imgView)
            .load(imgUri)
            .into(imgView)
    }
}