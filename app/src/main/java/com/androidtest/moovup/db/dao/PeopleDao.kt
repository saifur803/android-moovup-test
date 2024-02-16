package com.androidtest.moovup.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.androidtest.moovup.db.model.People

@Dao
abstract class PeopleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addPeopleList(people: List<People>)

    @Query("SELECT * FROM PEOPLE")
    abstract suspend fun getPeopleList(): List<People>

    @Query("DELETE FROM PEOPLE")
    abstract suspend fun deletePeopleList()
}