package com.admin_official.codeforcesstalker

class Contest (val id: Int, val name: String, val timeStamp: Long, val duration: Int) {
    override fun toString(): String {
        return "\nContest(id=$id, name='$name', timeStamp=$timeStamp, duration=$duration)"
    }
}