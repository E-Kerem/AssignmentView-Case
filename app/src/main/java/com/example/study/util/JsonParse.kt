package com.example.study.util

import android.content.Context
import com.example.study.model.ImageProperties
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

fun getJsonDataFromAsset(context: Context, fileName: String): List<ImageProperties> {
    lateinit var jsonString: String
    try {
        jsonString = context.assets.open(fileName) // We get AssetManager object from context by context.assets, then use AssetManager.open() method to open a file in assets folder using
        .bufferedReader().use { it.readText() } // read data and readText() to transform the buffer into a String.
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        return emptyList()
    }

    val listOfImages = object : TypeToken<List<ImageProperties>>() {}.type
    // Gson is a Java library for converting JSON string to an equivalent Java object.
    return Gson().fromJson(jsonString, listOfImages) // com.google.gson.Gson package provides fromJson() for deserializing JSON.
}