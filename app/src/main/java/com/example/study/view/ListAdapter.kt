package com.example.study.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.study.databinding.ListItemBinding
import com.example.study.model.ImageProperties
import com.example.study.util.getJsonDataFromAsset
import kotlin.math.abs

class ListAdapter(private val context: Context, private val imageDataSet: List<ImageProperties>):
    RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    inner class ListViewHolder(val bindView: ListItemBinding): RecyclerView.ViewHolder(bindView.root) {
        fun getImageView() = bindView.itemImg
        fun bindData(itemImage: ImageProperties?) {
            bindView.apply {
                imageItemValue.text = (itemImage?.imgNum?.plus(1)).toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val item = ListItemBinding.inflate(layoutInflater, parent, false)
        return ListViewHolder(item)
    }

    override fun onBindViewHolder(viewHold: ListViewHolder, @SuppressLint("RecyclerView") position: Int) {
        viewHold.bindData(imageDataSet[position])
        val imgLink = imageDataSet[position].imgLink
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
                    imageDataSet[position].apply { loadingTime = abs(startTime - endTime) }
                    Log.d(
                        "LoadingTime",
                        "Image Loading Time: ${abs(startTime - endTime)} milliseconds")
                    return false
                }
            }
            ).into(viewHold.getImageView())
    }

    override fun getItemCount(): Int {
        return imageDataSet.size
    }

    fun getJsonDataFromAsset2(fileName: String) : List<ImageProperties> {
        return getJsonDataFromAsset(context, fileName)
    }

}