package com.boreal.test.core.utils.core.firestore

import com.boreal.commonutils.text.capitalizeName
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*

fun Timestamp.getFormatWithDay(
    format: String = "EEEE dd MMM yy HH:mm:ss",
    locale: Locale = Locale("es", "MX")
) = SimpleDateFormat(format, locale).format(Date(toDate().time)).capitalizeName()