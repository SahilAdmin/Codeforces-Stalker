package com.admin_official.codeforcesstalker

import org.json.JSONArray
import org.json.JSONObject

interface ParseListener {
    fun authenticateCallback(boolean: Boolean)
    fun userInfoCallback(handles: List<Handle>)
    fun userStatusCallback(problems: List<Problem>)
    fun contestsCallback(contests: List<Contest>)
    fun standingsCallback(handles: List<ContestHandle>)
}

private const val TAG = "De_JsonParse"

object JsonParse {
    fun parse(downloadType: DownloadType, json: String, listener: ParseListener, downloadStatus: DownloadStatus) {

        if(downloadStatus == DownloadStatus.FAILED) {
            if(downloadType == DownloadType.AUTHENTICATE) {
                listener.authenticateCallback(false)
            }
            return
        }

        val response = JSONObject(json)

        when(downloadType) {
            DownloadType.AUTHENTICATE -> {
                listener.authenticateCallback(downloadStatus != DownloadStatus.FAILED)
            }

            DownloadType.USERINFO -> {
                val userJsonArray = response.getJSONArray("result")
                var handles = mutableListOf<Handle>()
                for(i in 0 until userJsonArray.length()) {
                    val userJsonObject = userJsonArray.getJSONObject(i)
                    val handle = Handle(
                        userJsonObject.getString("firstName"),
                        userJsonObject.getString("lastName"),
                        userJsonObject.getInt("rating"),
                        userJsonObject.getInt("maxRating"),
                        userJsonObject.getString("rank"),
                        userJsonObject.getString("titlePhoto"),
                        userJsonObject.getString("maxRank")
                    )

                    handles.add(handle)
                }
                listener.userInfoCallback(handles)
            }

            DownloadType.USER_STATUS -> {
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

            DownloadType.CONTESTS -> {
                val contestJsonArray = response.getJSONArray("result")
                val contests = mutableListOf<Contest>()

                for(i in 0 until contestJsonArray.length()) {
                    val contestJsonObject = contestJsonArray.getJSONObject(i)
                    val contest = Contest(
                        contestJsonObject.getInt("id"),
                        contestJsonObject.getString("name"),
                        contestJsonObject.getLong("startTimeSeconds"),
                        contestJsonObject.getInt("durationSeconds")/60)

                    contests.add(contest)
                }

                listener.contestsCallback(contests)
            }

            DownloadType.STANDINGS -> {
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
        }
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