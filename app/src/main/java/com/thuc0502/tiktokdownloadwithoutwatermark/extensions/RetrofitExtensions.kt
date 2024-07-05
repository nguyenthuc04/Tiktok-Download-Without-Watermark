package com.thuc0502.tiktokdownloadwithoutwatermark.extensions



//suspend fun <T> retrofit2.Call<T>.safeApiCall(): Result<T> {
//    return try {
//        val response = execute()
//        if (response.isSuccessful) {
//            Result.success(response.body()!!)
//        } else {
//            Result.failure(Throwable(response.message()))
//        }
//    } catch (e: Exception) {
//        Result.failure(e)
//    }
//}