package com.boreal.test.core.utils.core

import android.util.Log

fun String.log(key: String, error: Boolean = true){
    if(!error){
        Log.i(key,this)
    }else{
        Log.e(key,this)
    }
}