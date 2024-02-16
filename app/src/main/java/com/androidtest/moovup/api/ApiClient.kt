package com.androidtest.moovup.api

import android.content.Context
import android.util.Log
import com.androidtest.moovup.api.deserializer.PeopleTypeAdapter
import com.androidtest.moovup.db.AppDatabase
import com.androidtest.moovup.util.CoroutineUtil
import com.androidtest.moovup.util.SingletonHolder
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.lang.Exception
import java.util.concurrent.TimeUnit

class ApiClient(val context: Context) {
    companion object: SingletonHolder<ApiClient, Context>(::ApiClient) {
        const val TAG = "ApiClient"
    }

    private val apiInterface: ApiInterface

    init {
        val client = OkHttpClient.Builder().run {
            connectTimeout(10L, TimeUnit.SECONDS)
            writeTimeout(10L, TimeUnit.SECONDS)
            readTimeout(10L, TimeUnit.SECONDS)
            addInterceptor(ApiInterceptor())
            build()
        }

        val moshi = Moshi.Builder().run {
            add(PeopleTypeAdapter.createFactory())
            build()
        }

        apiInterface = Retrofit.Builder().run {
            baseUrl("https://api.json-generator.com/")
            client(client)
            addConverterFactory(MoshiConverterFactory.create(moshi))
            build()
        }.create(ApiInterface::class.java)
    }

    fun fetchPeopleList(
        callback: ((result: Boolean, isDataAvailable: Boolean) -> Unit)
    ) {
        val SUB_TAG = "fetchPeopleList"

        Log.i(TAG, "$SUB_TAG. Start")

        CoroutineUtil.io {
            val peopleDao = AppDatabase.createDatabase(context).peopleDao()

            try {
                val peopleList = apiInterface.getPeopleList()

                Log.i(TAG, "$SUB_TAG. Success")

                peopleDao.deletePeopleList()
                peopleDao.addPeopleList(peopleList)

                CoroutineUtil.main {
                    callback(true, true)
                }
            } catch (e: Exception) {
                Log.e(TAG, "$SUB_TAG. Failed ${e.message}")
                e.printStackTrace()

                val isDataAvailable = peopleDao.getPeopleList().isNotEmpty()

                CoroutineUtil.main {
                    callback(false, isDataAvailable)
                }
            }
        }
    }

    inner class ApiInterceptor: Interceptor {
        val token = "b2atclr0nk1po45amg305meheqf4xrjt9a1bo410"

        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            return chain.proceed(request)
        }
    }
}