package com.androidtest.moovup.api.deserializer

import com.androidtest.moovup.db.model.People
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type

class PeopleTypeAdapter(val delegate: JsonAdapter<People>): JsonAdapter<People>() {

    companion object {
        fun createFactory(): Factory {
            return object : Factory {
                override fun create(
                    type: Type,
                    annotations: MutableSet<out Annotation>,
                    moshi: Moshi
                ): JsonAdapter<*>? {
                    if (Types.getRawType(type) == People::class.java) {
                        val delegate = moshi.nextAdapter<People>(this, type, annotations)
                        return PeopleTypeAdapter(delegate)
                    }
                    return null
                }

            }
        }
    }

    override fun fromJson(reader: JsonReader): People? {
        var id: String = ""
        var email: String = ""
        var picture: String = ""
        var firstName: String = ""
        var lastName: String = ""

        var latitude: Double? = null
        var longitude: Double? = null

        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.nextName()) {
                "_id" -> {
                    id = reader.nextString()
                }
                "email" -> {
                    email = reader.nextString()
                }
                "picture" -> {
                    picture = reader.nextString()
                }
                "name" -> {
                    reader.beginObject()
                    while (reader.hasNext()) {
                       when (reader.nextName()) {
                           "first" -> {
                               firstName = reader.nextString()
                           }
                           "last" -> {
                               lastName = reader.nextString()
                           }
                       }
                    }
                    reader.endObject()
                }
                "location" -> {
                    reader.beginObject()
                    while (reader.hasNext()) {
                        when (reader.nextName()) {
                            "latitude" -> {
                                latitude = reader.nextDouble()
                            }
                            "last" -> {
                                longitude = reader.nextDouble()
                            }
                        }
                    }
                    reader.endObject()
                }
            }
        }
        reader.endObject()

        return People(id, email, picture, firstName, lastName, latitude, longitude)
    }

    override fun toJson(writer: JsonWriter, value: People?) {
        delegate.toJson(writer, value)
    }

}