package com.thuc0502.tiktokdownloadwithoutwatermark

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.thuc0502.tiktokdownloadwithoutwatermark.Model.TikTokVideoInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.MalformedURLException
import java.net.URL
import java.util.UUID


suspend fun getTikTokVideoInfo(link: String): TikTokVideoInfo? {

    val client = OkHttpClient()
    val request = Request.Builder()
        .url("https://tiktok-download-without-watermark.p.rapidapi.com/analysis?url=$link")
        .get()
//        .addHeader("x-rapidapi-key" ,"c62d376226msh62c5e1c2cb3b6ccp1cbd50jsn2b59bcff3362")
        .addHeader("x-rapidapi-key","b90c2b49a8msh5db6277d2c47139p165cfdjsna3adbf9fc5b2")
        .addHeader("x-rapidapi-host", "tiktok-download-without-watermark.p.rapidapi.com")
        .build()

    val response = client.newCall(request).execute()
    val responseBody = response.body?.string()

    if (!response.isSuccessful || responseBody.isNullOrEmpty()) {
        println("Response was not successful or body is empty")
        return null
    }

    val gson = Gson()
    val jsonObject = gson.fromJson(responseBody ,JsonObject::class.java)

    if (!jsonObject.has("data")) {
        println("Response does not contain 'data' field")
        return null
    }

    val data = jsonObject.getAsJsonObject("data")
    val awemeId = data.get("aweme_id").asString
    val title = data.get("title").asString
    val playUrl = data.get("play").asString
    val author = data.getAsJsonObject("author")
    val authorAvatar = author.get("avatar").asString
    val authorUniqueId = author.get("unique_id").asString
    val nickname = author.get("nickname").asString
    val music = data.get("music").asString
    val size = convertBytesToReadableSize(data.get("size").asLong)
    val musicSize = withContext(Dispatchers.IO) { getSizeUrl(data.get("music").asString) }

    return TikTokVideoInfo(
        awemeId ,
        title ,
        playUrl ,
        authorAvatar ,
        authorUniqueId ,
        music ,
        nickname,
        size,
        musicSize
    )
}

fun convertBytesToReadableSize(sizeInBytes: Long): String {
    val sizeInKB = sizeInBytes / 1024.0
    val sizeInMB = sizeInKB / 1024.0

    return if (sizeInMB > 1) {
        "%.2f MB".format(sizeInMB)
    } else {
        "%.2f KB".format(sizeInKB)
    }
}

suspend fun getSizeUrl(url: String): String? {
    val client = OkHttpClient()
    val request = Request.Builder().url(url).head()
        .build()
    val response = client.newCall(request).execute()
    val sizeInBytes = response.header("Content-Length")?.toLongOrNull()

    return sizeInBytes?.let { convertBytesToReadableSize(it) }
}

fun generateRandomFileName(extension: String): String {
    return UUID.randomUUID().toString() + extension
}
fun isValidUrl(url: String): Boolean {
    return try {
        URL(url)
        true
    } catch (e: MalformedURLException) {
        false
    }
}
 suspend fun main() {
    val link = "https://www.tiktok.com/@thuy_lcc/video/7359534766216973575?_r=1&_t=8nhb69725mT"
    val videoInfo = getTikTokVideoInfo(link)
    if (videoInfo != null) {
        println("Aweme ID: ${videoInfo.awemeId}")
        println("Title: ${videoInfo.title}")
        println("Play URL: ${videoInfo.playUrl}")
        println("Author Avatar: ${videoInfo.authorAvatar}")
        println("Author Unique ID: ${videoInfo.authorUniqueId}")
        println("Music URL: ${videoInfo.music}")
        println("Nickname: ${videoInfo.nickname}")

    } else {
        println("Failed to fetch video information")
    }
    val size = getSizeUrl("https:\\/\\/v16m-default.akamaized.net\\/d998495548a5d60f84b9edfe495c6188\\/66851f4c\\/video\\/tos\\/alisg\\/tos-alisg-pve-0037c001\\/oMmPcnhgTBpQIAFRgBNIBfwhqMEvgof56zGDEa\\/?a=0&bti=OUBzOTg7QGo6OjZAL3AjLTAzYCMxNDNg&ch=0&cr=0&dr=0&lr=all&cd=0%7C0%7C0%7C0&cv=1&br=4924&bt=2462&cs=0&ds=6&ft=XE5bCqT0m7jPD12gnaF73wUSx3yKMeF~O5&mime_type=video_mp4&qs=0&rc=Mzg8MzY1Njk0Ojs1ZTM8OUBpam54M2o5cm47cjMzODczNEA0NDJiYzYvXi4xMTQ2My1iYSNqYTM1MmRzbWxgLS1kMWBzcw%3D%3D&vvpl=1&l=2024070303491557C93F004EC40D33A332&btag=e00090000")
    println(size)

}
