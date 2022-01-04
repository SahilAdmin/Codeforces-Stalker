package com.admin_official.codeforcesstalker.objects

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Contest (val id: Int, val name: String, val timeStamp: Long, val duration: Int, val phase: String): Parcelable {
    override fun toString(): String {
        return "\nContest(id=$id, name='$name', timeStamp=$timeStamp, duration=$duration, phase=$phase)"
    }
}