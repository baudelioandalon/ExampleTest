package com.boreal.test.one.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.boreal.test.one.domain.movie.MovieDao
import com.boreal.test.one.domain.movie.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class TestDataBase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}