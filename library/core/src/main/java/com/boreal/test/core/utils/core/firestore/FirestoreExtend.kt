package com.boreal.test.core.utils.core.firestore

import com.google.firebase.firestore.DocumentSnapshot


inline fun <reified T> DocumentSnapshot.convertData(idData: String) = this[idData, T::class.java]!!

inline fun <reified T> List<DocumentSnapshot>.convertDataToList(idData: String) =
    with(ArrayList<T>()) {
        this@convertDataToList.forEach {
            val item = it[idData, T::class.java]!!
            item
            add(it.convertData(idData))
        }
        this
    }

inline fun <reified T> List<DocumentSnapshot>.convertData(idData: String) =
    this@convertData.first().convertData<T>(idData)

inline fun <reified T> List<DocumentSnapshot>.convertDataToList() =
    with(ArrayList<T>()) {
        this@convertDataToList.forEach {
            if (it.data == null) {
                return@forEach
            }
            add(it.toObject(T::class.java)!!)
        }
        this
    }