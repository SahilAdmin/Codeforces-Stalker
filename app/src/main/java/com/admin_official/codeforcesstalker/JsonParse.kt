package com.admin_official.codeforcesstalker

import com.admin_official.codeforcesstalker.data.Username
import org.json.JSONArray
import org.json.JSONObject
import java.lang.IllegalArgumentException

interface ParseListener {
    fun authenticateCallback(boolean: Boolean)
    fun userInfoCallback(problems: List<Handle>)
    fun contestsCallback(contests: List<Contest>)
    fun standingsCallback(handles: List<ContestHandle>)
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
                userJsonObject.getString("handle"),
                userJsonObject.getInt("rating"),
                userJsonObject.getInt("maxRating"),
                userJsonObject.getString("rank"),
                userJsonObject.getString("titlePhoto"),
                userJsonObject.getString("maxRank")
            )

            Downloader().apply {
                var problemsJson = download(DownloadType.USER_STATUS, listOf(Username(handle.username)), null)
                while(DownloadStatus.OK != status) {
                    status = DownloadStatus.OK
                    problemsJson = download(DownloadType.USER_STATUS, listOf(Username(handle.username)), null)
                }
                handle.problems = parseUserStatus(problemsJson)
                handle.calcProblemsSolved()
                handle.calcAcceptedToday()
                handles.add(handle)
            }
        }

        listener.userInfoCallback(handles)
    }

    private fun parseUserStatus(json: String): List<Problem> {
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

        return problems
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