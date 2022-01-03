package com.admin_official.codeforcesstalker.objects

class Contest (val id: Int, val name: String, val timeStamp: Long, val duration: Int, val phase: String) {
    override fun toString(): String {
        return "\nContest(id=$id, name='$name', timeStamp=$timeStamp, duration=$duration, phase=$phase)"
    }
}