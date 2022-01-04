package com.admin_official.codeforcesstalker.objects

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.util.*
import java.util.concurrent.TimeUnit

fun timeStampUtil(timeStampInMillis: Long, pattern: String): String = SimpleDateFormat(pattern).format(timeStampInMillis)
val now = System.currentTimeMillis()
val today = timeStampUtil(now, "dd/MM/yyyy")
val sdf = SimpleDateFormat("dd/MM/yyyy")

@Parcelize
class Handle (val username: String,
              val rating: Int = 0,
              val maxRating: Int = 0,
              val rank: String = "Null",
              val dp: String = "Null",
              val maxRank: String = "Null"): Parcelable {

    var submissionsToday = 0
    var submissions10Days = 0
    var acceptedToday = 0
    var accepted10Days = 0
    var accepted = 0
    var problems: List<Problem> = Collections.emptyList()
    var todayProblems: List<Problem> = Collections.emptyList()
    var calculated = false

    fun calc() {
        if(calculated) return
        for(problem in problems) {
            val pDay = timeStampUtil(problem.timeStamp*1000L, "dd/MM/yyyy")

            val stDay = sdf.parse(pDay)
            val endDay = sdf.parse(today)

            val diff = endDay.time - stDay.time
            val days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
            if(days <= 0) {
                submissionsToday++
                if(problem.verdict == "OK") acceptedToday++
            }
            if(days <= 10) {
                submissions10Days++
                if(problem.verdict == "OK") accepted10Days++
            }
            if(problem.verdict == "OK") accepted++
        }
        calculated = true
    }

    fun calcProblemsSolved() {
        for(problem in problems) {
            if(timeStampUtil(problem.timeStamp*1000L, "dd/MM/yyyy") ==
                timeStampUtil(now, "dd/MM/yyyy")) {
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