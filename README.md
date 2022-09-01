## AssignmentView - Task
Sample project with custom list view that extends from RecyclerView. Which logs for image load times to platform’s logger and send request the loading times of the images

## Dependencies -Requirements-
Third party libraries that implemented in the project:
`build.gradle(Module: Study.app)`
```kotlin   
implementation 'com.github.bumptech.glide:glide:4.13.2' // Glide
annotationProcessor 'com.github.bumptech.glide:compiler:4.13.2' // Glide
implementation "com.google.code.gson:gson:2.8.8" // gson
implementation "com.squareup.okhttp3:okhttp:4.9.0" // okhttp
```
## Libraries Usages

# Why Glide
Glide is an open source image loading and caching library for Android APPs developed by bumptech.
It is fast, efficient and widely used image loading library, primarily focused on smooth scrolling.
Managing Memory:
It is important to reduce the amount of network requests an Android app needs to make when loading images. 
Getting rid of unnecessary network requests is achieved with Glide’s default use of memory and disk caching.
In most cases, when you use Glide you don’t have to think about releasing memory,Glide handle that. 
Each bitmap is released when the activity or the fragment passed to theGlide.with is destroyed.
How to Use Glide:
Glide can load the image from url. It loads image from cache if there exits.
`getGlide(context: Context, image: ImageProperties?,loadTime: MutableList<Long> ?)`
getGlide function loads images and logs their loading time. Which is called from `ListAdapter.ListViewHolder.kt`
```kotlin
    inner class ListViewHolder(val bindView: ListItemBinding): RecyclerView.ViewHolder(bindView.root) {
        fun getImageView() = bindView.itemImg
        fun bindData(itemImage: ImageProperties?) {
            bindView.apply {
                imageItemValue.text = (itemImage?.imgNum?.plus(1)).toString()
                getGlide(this@ListViewHolder.itemView.context,itemImage,loadingTimes)
            }
        }
    }
```
```kotlin
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
```
# Why GSON
Google's Gson library provides a powerful framework for converting between JSON strings and Java objects. 
This library helps to avoid needing to write boilerplate code to parse JSON responses yourself. 
It can be used with any networking library, including the Android Async HTTP Client and OkHttp.
Advantages:
- Easy to use − Gson API provides a high-level facade to simplify commonly used use-cases.
- Performance − Gson is quite fast and is of low memory footprint. It is suitable for large object graphs or systems.
- No Dependency − Gson library does not require any other library apart from JDK.
- Standardized − Gson is a standardized library that is managed by Google.
It used to parse JSON data:
```kotlin
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
```
# Why OkHttp
OkHttp is an HTTP client from Square for Java and Android applications. It’s designed to load resources faster and save bandwidth. 
OkHttp is widely used in open-source projects and is the backbone of libraries like Glide, Picasso and many others.
Advantages:
- HTTP/2 support (efficient socket usage)
- Connection pooling (reduces request latency in the absence of HTTP/2)
- Response caching (avoids re-fetching the same data)
- Synchronous and asynchronous call support
OkHttp used to send image loading times from RecyclerView to the service:
```kotlin
fun sendRequest(item: ImageProperties, list: MutableList<Long> ) {
        var client = OkHttpClient()
        val url = item.imgLink
        val itemNo = item.imgNum
        val loadingTime = item.loadingTime
        val loadingTimeFromList = list

        val body = url?.let {
            FormBody.Builder()
                .add(it, "Item No: ${itemNo?.plus(1)} loaded in $loadingTime milliseconds")
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
```

## DemoPage
Main activity page that binds ListView and gets the JsonUrls.
```kotlin
binding.imageListView.apply {
            adapter = ListAdapter1(this@DemoPage, imageUrls)
        }
```
```kotlin
  val imageUrls = JsonList().getUrls()
```
If the Json is in the assets, sample projects is also capable of getting urls from asset and add to `List<ImageProperties>`.
```kotlin
 lateinit var adapters: ListAdapter1
val imageUrls = adapters.getJsonDataFromAsset2("jsonList.json")
```

## Sample ShowCases
```kotlin
2022-08-26 14:13:18.608 5837-5868/com.example.study D/OkHttp: Item No: 0 loaded in 103 milliseconds
2022-08-26 14:13:21.504 5837-5896/com.example.study D/OkHttp: Item No: 1 loaded in 84 milliseconds
2022-08-26 14:13:21.535 5837-5897/com.example.study D/OkHttp: Item No: 2 loaded in 0 milliseconds
2022-08-26 14:13:22.258 5837-5898/com.example.study D/OkHttp: Item No: 3 loaded in 0 milliseconds
2022-08-26 14:13:25.785 5837-5907/com.example.study D/OkHttp: Item No: 4 loaded in 0 milliseconds
2022-08-26 14:13:27.598 5837-5913/com.example.study D/OkHttp: Item No: 5 loaded in 90 milliseconds
2022-08-26 14:13:29.422 5837-5917/com.example.study D/OkHttp: Item No: 6 loaded in 38 milliseconds
2022-08-26 14:13:31.046 5837-5921/com.example.study D/OkHttp: Item No: 7 loaded in 0 milliseconds
2022-08-26 14:13:34.144 5837-5929/com.example.study D/OkHttp: Item No: 8 loaded in 391 milliseconds
2022-08-26 14:13:37.434 5837-5937/com.example.study D/OkHttp: Item No: 9 loaded in 395 milliseconds
2022-08-26 14:13:38.991 5837-5942/com.example.study D/OkHttp: Item No: 10 loaded in 366 milliseconds
``` 