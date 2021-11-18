package com.example.mtx.di

import com.example.mtx.BuildConfig
import com.example.mtx.datasource.RetrofitService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideNetworkService(): RetrofitService {

        val okHttpClientBuilder = OkHttpClient.Builder()
            .readTimeout(2*30, TimeUnit.SECONDS)
            .connectTimeout(2*30, TimeUnit.SECONDS)
            .writeTimeout(2*30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BASIC
            okHttpClientBuilder.addInterceptor(logging)
        }

        return Retrofit.Builder()
            .baseUrl("http://mobiletraderapi.com:9100")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(okHttpClientBuilder.build())
            .build()
            .create(RetrofitService::class.java)
    }

}