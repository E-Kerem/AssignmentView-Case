package com.example.study

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.study.data.JsonList
import com.example.study.databinding.DemoListViewBinding
import com.example.study.view.ListAdapter as ListAdapter1

class DemoPage : AppCompatActivity() {
    private lateinit var binding: DemoListViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DemoListViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val imageUrls = JsonList().getUrls()

        //lateinit var adapters: ListAdapter1
        //val imageUrls = adapters.getJsonDataFromAsset2("jsonList.json")

        binding.imageListView.apply {
            adapter = ListAdapter1(this@DemoPage, imageUrls)
        }
    }
}