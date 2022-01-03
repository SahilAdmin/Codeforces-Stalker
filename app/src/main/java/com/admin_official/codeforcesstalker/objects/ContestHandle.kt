package com.admin_official.codeforcesstalker.objects

class ContestHandle (val name: String, val rank: Int, val points: Double, val penalty: Int, val problemSolved: Int) {
    override fun toString(): String {
        return "\nContestHandle(name='$name', rank=$rank, points=$points, penalty=$penalty, problemSolved=$problemSolved)"
    }
}