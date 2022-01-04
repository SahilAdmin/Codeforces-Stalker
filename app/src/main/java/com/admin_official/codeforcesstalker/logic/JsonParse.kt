package com.admin_official.codeforcesstalker.logic

import com.admin_official.codeforcesstalker.data.Username
import com.admin_official.codeforcesstalker.objects.Contest
import com.admin_official.codeforcesstalker.objects.ContestHandle
import com.admin_official.codeforcesstalker.objects.Handle
import com.admin_official.codeforcesstalker.objects.Problem
import org.json.JSONArray
import org.json.JSONObject

interface ParseListener {
    fun authenticateCallback(boolean: Boolean)
    fun userInfoCallback(handles: List<Handle>)
    fun contestsCallback(contests: List<Contest>)
    fun standingsCallback(handles: List<ContestHandle>)
    fun userStatusCallback(problems: List<Problem>)
}

private const val TAG = "De_JsonParse"

class JsonParse(val listener: ParseListener) {
    fun parseUserInfo(json: String) {
        val response = JSONObject(json)

        val userJsonArray = response.getJSONArray("result")
        var handles = mutableListOf<Handle>()
        for(i in 0 until userJsonArray.length()) {
            val userJsonObject = userJsonArray.getJSONObject(i)
            val handle = Handle(
                username = userJsonObject.optString("handle"),
                rating = userJsonObject.optInt("rating"),
                maxRating = userJsonObject.optInt("maxRating"),
                rank = userJsonObject.optString("rank", "not rated"),
                dp = userJsonObject.optString("titlePhoto"),
                maxRank = userJsonObject.optString("maxRank", "not rated")
            )
            handles.add(handle)
        }
        listener.userInfoCallback(handles)
    }

    fun parseUserStatus(json: String) {
        val response = JSONObject(json)

        val problemsJsonArray = response.getJSONArray("result")
        val problems = mutableListOf<Problem>()
        for(i in 0 until problemsJsonArray.length()) {
            val jsonObject = problemsJsonArray.getJSONObject(i)
            val problemJsonObject = jsonObject.getJSONObject("problem")

            val problem = Problem(
                problemJsonObject.getInt("contestId"),
                problemJsonObject.getString("index"),
                problemJsonObject.getString("name"),
                getTags(problemJsonObject.getJSONArray("tags")),
                jsonObject.getString("verdict"),
                jsonObject.getString("programmingLanguage"),
                jsonObject.getLong("creationTimeSeconds"))

            problems.add(problem)
        }

        listener.userStatusCallback(problems)
    }

    fun parseContests(json: String) {
        val response = JSONObject(json)

        val contestJsonArray = response.getJSONArray("result")
        val contests = mutableListOf<Contest>()

        for(i in 0 until contestJsonArray.length()) {
            val contestJsonObject = contestJsonArray.getJSONObject(i)
            val contest = Contest(
                contestJsonObject.getInt("id"),
                contestJsonObject.getString("name"),
                contestJsonObject.getLong("startTimeSeconds"),
                contestJsonObject.getInt("durationSeconds")/60,
                contestJsonObject.getString("phase"))

            contests.add(contest)
        }

        listener.contestsCallback(contests)
    }

    fun parseStandings(json: String) {
        val response = JSONObject(json)

        val statusJsonArray = response.getJSONObject("result").getJSONArray("rows")
        val handles = mutableListOf<ContestHandle>()

        for(i in 0 until statusJsonArray.length()) {
            val statusJsonObject = statusJsonArray.getJSONObject(i)
            val contestHandle = ContestHandle(
                statusJsonObject.getJSONObject("party").getJSONArray("members").getJSONObject(0).getString("handle"),
                statusJsonObject.getInt("rank"),
                statusJsonObject.getDouble("points"),
                statusJsonObject.getInt("penalty"),
                problemSolved(statusJsonObject.getJSONArray("problemResults")))

            handles.add(contestHandle)
        }

        listener.standingsCallback(handles)
    }

    private fun problemSolved(array: JSONArray): Int {
        var count = 0
        for(i in 0 until array.length()) {
            if(array.getJSONObject(i).has("bestSubmissionTimeSeconds")) count++
        }
        return count
    }

    private fun getTags(array: JSONArray):List<String> {
        val list = mutableListOf<String>()
        for(i in 0 until array.length()) {
            list.add(array.getString(i))
        }
        return list
    }
}