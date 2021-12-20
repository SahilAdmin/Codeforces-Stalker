package com.admin_official.codeforcesstalker

import java.text.SimpleDateFormat

fun timeStampUtil(timeStampInMillis: Long, pattern: String): String = SimpleDateFormat(pattern).format(timeStampInMillis)

class Handle (val username: String,
              val firstName: String,
              val lastName: String,
              val rating: Int,
              val maxRating: Int,
              val rank: String,
              val dp: String,
              val maxRank: String) {

    var submissionsToday = 0
    lateinit var problems: List<Problem>

    fun calcProblemsSolved() {
        for(problem in problems) {
            if(timeStampUtil(problem.timeStamp*1000L, "dd") ==
                timeStampUtil(System.currentTimeMillis(), "dd")) submissionsToday++
        }
    }

    override fun toString(): String {
        return "\nHandle(firstName='$firstName', lastName='$lastName', rating=$rating, maxRating=$maxRating, rank='$rank', dp='$dp', maxRank='$maxRank', solvedToday=$submissionsToday, problems=$problems)"
    }
}