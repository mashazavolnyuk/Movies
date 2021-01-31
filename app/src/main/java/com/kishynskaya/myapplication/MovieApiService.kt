package com.kishynskaya.myapplication

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object MovieApiService {

    private const val BASE_URL = "https://api.themoviedb.org/3/"

    private var httpClient = OkHttpClient.Builder()
        .addInterceptor(add())
        .addInterceptor(
            (Interceptor { chain ->
                val original = chain.request()
                val originalHttpUrl = original.url
                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("api_key", "9923828436395f38e0dfa969acfd653c")
                    .build()
                val requestBuilder = original.newBuilder().url(url)
                val request = requestBuilder.build()
                chain.proceed(request)
            })
        ).build()

    fun add(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    private var builder: Retrofit.Builder =
        Retrofit.Builder()
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BASE_URL)

    private val retrofit = builder.build()

    var netService: MovieAPI = retrofit.create(MovieAPI::class.java)

}
