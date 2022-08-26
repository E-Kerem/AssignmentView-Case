package com.example.AssignmentViewCaseStudy.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.AssignmentViewCaseStudy.databinding.ListItemBinding
import com.example.AssignmentViewCaseStudy.model.ImageProperties
import com.example.AssignmentViewCaseStudy.network.RequestClient
import com.example.AssignmentViewCaseStudy.util.getGlide
import com.example.AssignmentViewCaseStudy.util.getJsonDataFromAsset

class ListAdapter(private val context: Context, private val imageDataSet: List<ImageProperties>):
    RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    private var client = RequestClient()
    val loadingTimes: MutableList<Long> = mutableListOf()

    inner class ListViewHolder(val bindView: ListItemBinding): RecyclerView.ViewHolder(bindView.root) {
        fun getImageView() = bindView.itemImg
        fun bindData(itemImage: ImageProperties?) {
            bindView.apply {
                imageItemValue.text = (itemImage?.imgNum?.plus(1)).toString()
                getGlide(this@ListViewHolder.itemView.context,itemImage,loadingTimes)
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
        //val imgLink = imageDataSet[position].imgLink
    }

    override fun getItemCount(): Int {
        return imageDataSet.size
    }

    fun getJsonDataFromAsset2(fileName: String) : List<ImageProperties> {
        return getJsonDataFromAsset(context, fileName)
    }

}