package com.example.study.network

import android.util.Log
import com.example.study.model.ImageProperties
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

//OkHttp used to send image loading times from RecyclerView to the service:
//HTTP efficiently makes your stuff load faster and saves bandwidth.
class RequestClient {
    fun sendRequest(item: ImageProperties, list: MutableList<Long> ) {
        var client = OkHttpClient()

        val url = item.imgLink
        val itemNo = item.imgNum
        val loadingTime = item.loadingTime
        val loadingTimeFromList = list

        val body = url?.let {
            FormBody.Builder()
                .add(it, "Item No: ${itemNo} loaded in $loadingTime milliseconds")
                .build()
        }

        val request = body?.let {
            Request.Builder()
                .url("https://httpbin.org/post")
                .post(it)
                .build()
        }
        val response = request?.let { client.newCall(it).execute() }
        var result = response?.body?.string()
        Log.d("OkHttp", "Item No: $itemNo loaded in $loadingTime milliseconds ")

    }
}
