package com.androidtest.moovup.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity
@JsonClass(generateAdapter = true)
data class People(
    @PrimaryKey
    @Json(name = "_id")
    val id: String,
    val email: String,
    val picture: String,

    val firstName: String,
    val lastName: String,

    val latitude: Double?,
    val longitude: Double?,
) {
    fun getLocation(): LatLng? {
        if (latitude == null || longitude == null) {
            return null
        }
        return LatLng(latitude, longitude)
    }
}