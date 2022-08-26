package com.example.AssignmentViewCaseStudy.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.AssignmentViewCaseStudy.model.ImageProperties
import com.example.AssignmentViewCaseStudy.network.RequestClient
import com.example.AssignmentViewCaseStudy.view.ListAdapter
import kotlin.math.abs

fun ListAdapter.ListViewHolder.getGlide(context: Context, image: ImageProperties?,loadTime: MutableList<Long> ?) {
    var client = RequestClient()
    val imgLink = image?.imgLink
    val startTime = System.currentTimeMillis()
    Glide.with(context).load(imgLink).diskCacheStrategy(DiskCacheStrategy.ALL)
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