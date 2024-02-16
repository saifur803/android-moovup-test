package com.androidtest.moovup.api

import com.androidtest.moovup.db.model.People
import retrofit2.http.GET

interface ApiInterface {

    @GET("templates/-xdNcNKYtTFG/data")
    suspend fun getPeopleList(): List<People>
}