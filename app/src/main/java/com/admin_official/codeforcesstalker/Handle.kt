package com.admin_official.codeforcesstalker

import java.text.SimpleDateFormat
import java.util.*

fun timeStampUtil(timeStampInMillis: Long, pattern: String): String = SimpleDateFormat(pattern).format(timeStampInMillis)

class Handle (val username: String,
//              val firstName: String,
//              val lastName: String,
              val rating: Int,
              val maxRating: Int,
              val rank: String,
              val dp: String,
              val maxRank: String) {

    var submissionsToday = 0
    var acceptedToday = 0;
    var problems: List<Problem> = Collections.emptyList()

    fun calcProblemsSolved() {
        for(problem in problems) {
            if(timeStampUtil(problem.timeStamp*1000L, "dd/MM/yyyy") ==
                timeStampUtil(System.currentTimeMillis(), "dd/MM/yyyy")) {
                    submissionsToday++
            }
        }
    }

    fun calcAcceptedToday() {
        for(problem in problems) {
            if(timeStampUtil(problem.timeStamp*1000L, "dd/MM/yyyy") ==
                    timeStampUtil(System.currentTimeMillis(), "dd/MM/yyyy") && problem.verdict == "OK") acceptedToday++
        }
    }

    override fun toString(): String {
        return "\nHandle(username=$username, rating=$rating, maxRating=$maxRating, rank='$rank', dp='$dp', maxRank='$maxRank', solvedToday=$submissionsToday, problems=$problems)"
    }
}