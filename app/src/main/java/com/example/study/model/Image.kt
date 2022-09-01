package com.example.study.model

data class ImageProperties(val imgLink : String?, val imgNum : Int?, var loadingTime: Long)

// We frequently create a class to do nothing but hold data.
// In such a class some standard functionality is often mechanically derivable from the data.
// https://kotlinlang.org/docs/data-classes.html