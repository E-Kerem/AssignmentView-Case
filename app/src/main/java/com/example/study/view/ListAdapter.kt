package com.example.study.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.study.databinding.ListItemBinding
import com.example.study.model.ImageProperties
import com.example.study.network.RequestClient
import com.example.study.util.getGlide
import com.example.study.util.getJsonDataFromAsset

//It is like the main responsible class to bind the views and display it.
class ListAdapter(private val context: Context, private val imageDataSet: List<ImageProperties>): // Adapter Takes the data set  which has to be displayed to the user in RecyclerView.
    RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    private var client = RequestClient()
    val loadingTimes: MutableList<Long> = mutableListOf()

    //ViewHolder is a type of a helper class that helps us to draw the UI for individual items that we want to draw on the screen.
    inner class ListViewHolder(val bindView: ListItemBinding): RecyclerView.ViewHolder(bindView.root) {
        fun getImageView() = bindView.itemImg
        fun bindData(itemImage: ImageProperties?) {
            bindView.apply {
                imageItemValue.text = (itemImage?.imgNum?.plus(1)).toString()
                getGlide(this@ListViewHolder.itemView.context,itemImage,loadingTimes)
            }
        }
    }

    // onCreateViewHolder only creates a new view holder when there are no existing view holders which the RecyclerView can reuse.
    // So, for instance, if your RecyclerView can display 5 items at a time, it will create 5-6 ViewHolders,
    // and then automatically reuse them, each time calling onBindViewHolder.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val item = ListItemBinding.inflate(layoutInflater, parent, false)
        return ListViewHolder(item)
    }

    // Instead of creating new view for each new row, an old view is recycled and reused by binding new data to it.
    // Initially you will get new unused view holders and you have to fill them with data you want to display.
    // But as you scroll you'll start getting view holders that were used for rows that went off screen and you have to replace old data that they held with new data.
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