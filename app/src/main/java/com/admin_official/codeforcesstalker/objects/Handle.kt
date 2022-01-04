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
    var ratingToday = arrayOf(0, 0, 0) // sum, count, avg
    var rating10Days = arrayOf(0, 0, 0)
    var ratingTotal = arrayOf(0, 0, 0)
    var ratingMax = arrayOf(0, 0, 0) // today, 10 days, total

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
                if(problem.rating != -1) {
                    ratingToday[0]+=problem.rating
                    ratingToday[1]++
                    ratingMax[0] = Math.max(ratingMax[1], problem.rating)
                }
                if(problem.verdict == "OK") acceptedToday++
            }
            if(days <= 10) {
                submissions10Days++
                if(problem.rating != -1) {
                    rating10Days[0]+=problem.rating
                    rating10Days[1]++
                    ratingMax[1] = Math.max(ratingMax[1], problem.rating)
                }
                if(problem.verdict == "OK") accepted10Days++
            }
            if(problem.verdict == "OK") accepted++
            if(problem.rating != -1) {
                ratingTotal[0]+=problem.rating
                ratingTotal[1]++
                ratingMax[2] = Math.max(ratingMax[2], problem.rating)
            }
        }
        ratingToday[2] = if(ratingToday[1] == 0) 0 else ratingToday[0]/ratingToday[1]
        rating10Days[2] = if(rating10Days[1] == 0) 0 else rating10Days[0]/rating10Days[1]
        ratingTotal[2] = if(ratingTotal[1] == 0) 0 else ratingTotal[0]/ratingTotal[1]
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