package com.boreal.test.core.utils

import android.content.Context
import com.boreal.test.core.PREFERENCE_NAME
import com.boreal.test.core.LOCATION_SERVICE

class SharedPreferenceTest(context: Context) {
    private val fileName = PREFERENCE_NAME
    val prefer = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)

    var isRunningService: Boolean
        get() = prefer.getBoolean(LOCATION_SERVICE, false)
        set(value) = prefer.edit().putBoolean(LOCATION_SERVICE, value).apply()

}