package com.boreal.test.main

import android.app.Application
import androidx.room.Room
import com.boreal.test.main.di.activityModule
import com.boreal.test.one.data.TestDataBase
import com.boreal.test.one.di.oneModule
import com.boreal.test.three.di.threeModule
import com.boreal.test.two.di.twoModule
import org.koin.core.context.startKoin

class TestApplication : Application() {
    companion object {
        lateinit var roomDatabase: TestDataBase
    }

    override fun onCreate() {
        super.onCreate()
        startKoin { modules(activityModule, oneModule, twoModule, threeModule) }

        roomDatabase = Room.databaseBuilder(applicationContext, TestDataBase::class.java, "db_test")
            .build()
    }
}