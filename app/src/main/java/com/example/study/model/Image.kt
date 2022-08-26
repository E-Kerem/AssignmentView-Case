package com.example.study.model


data class ImageProperties(val imgLink : String?, val imgNum : Int?, var loadingTime: Long)

data class ImageList (val imageList: List<ImageProperties>?)

