package com.admin_official.codeforcesstalker

import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

fun timeStampUtil(timeStampInMillis: Long, pattern: String): String = SimpleDateFormat(pattern).format(timeStampInMillis)

class Handle (val firstName: String,
              val lastName: String,
              val rating: Int,
              val maxRating: Int,
              val rank: String,
              val dp: String,
              val maxRank: String) {

    var solvedToday = 0
    lateinit var problems: List<Problem>

    fun calcProblemsSolved(problems: List<Problem>) {
        this.problems = problems

        for(problem in problems) {
            if(timeStampUtil(problem.timeStamp*1000L, "dd") ==
                timeStampUtil(System.currentTimeMillis(), "dd")) solvedToday++
        }
    }

    override fun toString(): String {
        return "\nHandle(firstName='$firstName', lastName='$lastName', rating=$rating, maxRating=$maxRating, rank='$rank', dp='$dp', maxRank='$maxRank', solvedToday=$solvedToday, problems=$problems)"
    }
}