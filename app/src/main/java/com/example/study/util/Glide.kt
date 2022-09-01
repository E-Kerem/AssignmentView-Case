package com.example.study.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.study.model.ImageProperties
import com.example.study.network.RequestClient
import com.example.study.view.ListAdapter
import kotlin.math.abs

// Glide’s primary focus is on making scrolling any kind of a list of images as smooth and fast as possible,
// but Glide is also effective for almost any case where you need to fetch, resize, and display a remote image
fun ListAdapter.ListViewHolder.getGlide(context: Context, image: ImageProperties?,loadTime: MutableList<Long> ?) {
    var client = RequestClient()
    val imgLink = image?.imgLink
    val startTime = System.currentTimeMillis()
    Glide.with(context).load(imgLink).diskCacheStrategy(DiskCacheStrategy.ALL)
        // For most applications DiskCacheStrategy.RESOURCE is ideal.
        // Applications that use the same resource multiple times in multiple sizes and are willing to trade off some speed and disk space
        // in return for lower bandwidth usage may want to consider using DiskCacheStrategy.DATA or DiskCacheStrategy.ALL
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: com.bumptech.glide.load.DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                val endTime = System.currentTimeMillis()
                val result = abs(startTime - endTime)
                image?.apply { loadingTime = result }
                Log.d(
                    "LoadingTime",
                    "Image Loading Time: $result milliseconds")
                loadTime?.add(result)
                return false
            }
        }
        ).into(bindView.itemImg)
    Thread {
        Thread.sleep(500)
        image?.let { loadTime?.let { it1 -> client.sendRequest(it, it1) } }
    }.start()
}

//Smart and automatic downsampling and caching minimize storage overhead and decode times.
//Aggressive re-use of resources like byte arrays and Bitmaps minimizes expensive garbage collections and heap fragmentation.
//Deep lifecycle integration ensures that only requests for active Fragments and Activities are prioritized and that Applications release resources when necessary to avoid being killed when backgrounded.