package com.example.androidshowcase.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.androidshowcase.database.entities.Component
import com.example.androidshowcase.database.entities.Library

@Dao
interface ComponentsDao {
    @Query("select * from components")
    fun getAllComponents(): List<Component>

    @Query("select * from components where id = :componentId limit 1")
    fun getComponent(componentId: Int): Component

    @Insert
    fun insertComponent(component: Component)

    @Delete
    fun deleteComponent(component: Component)
}