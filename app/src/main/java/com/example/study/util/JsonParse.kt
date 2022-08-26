package com.example.study.util

import android.content.Context
import com.example.study.model.ImageList
import com.example.study.model.ImageProperties
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

fun getJsonDataFromAsset(context: Context, fileName: String): List<ImageProperties> {
    lateinit var jsonString: String
    try {
        jsonString = context.assets.open(fileName)
        .bufferedReader()
            .use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        return emptyList()
    }

    val listOfImages = object : TypeToken<List<ImageProperties>>() {}.type
    return Gson().fromJson(jsonString, listOfImages)
}