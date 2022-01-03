package com.admin_official.codeforcesstalker.logic

import android.util.Log
import com.admin_official.codeforcesstalker.data.Username
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.lang.StringBuilder
import java.net.URL

object URLS {
    const val USERINFO = "https://codeforces.com/api/user.info?handles=%s"
    const val USER_STATUS = "https://codeforces.com/api/user.status?handle=%s&from=1&count=%d"
    const val CONTESTS = "https://codeforces.com/api/contest.list"
    const val STANDINGS = "https://codeforces.com/api/contest.standings?contestId=%d&handles=%s"
}

enum class DownloadStatus {
    OK, FAILED
}

private const val TAG = "De_Downloader"

class Downloader {

    var status = DownloadStatus.OK

    fun authenticate(username: String): String {
        status = DownloadStatus.OK

        val string = try {
            val url = URLS.USERINFO.format(username)
            URL(url).readText()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d(TAG, "authenticate: ${e.message}")
            status = DownloadStatus.FAILED
            ""
        }

        return string
    }

    fun userInfo(handles: List<Username>): String {
        status = DownloadStatus.OK

        val string = try {
            val handlesString = appendHandles(handles)
            val url = String.format(URLS.USERINFO, handlesString)
            URL(url).readText()
        } catch(e: Exception) {
            status = DownloadStatus.FAILED
            e.printStackTrace()
            Log.d(TAG, "userInfo: ${e.message}")
            ""
        }
        return string
    }

    fun contests(): String {
        status = DownloadStatus.OK

        val string = try {
            URL(URLS.CONTESTS).readText()
        } catch (e: Exception) {
            status = DownloadStatus.FAILED
            e.printStackTrace()
            Log.d(TAG, "contests: ${e.message}")
            ""
        }

        return string
    }

    fun standings(handles: List<Username>, contestId: Int): String {
        status = DownloadStatus.OK

        val string = try {
            val handlesString = appendHandles(handles)
            val url = String.format(URLS.STANDINGS, contestId, handlesString)
            URL(url).readText()
        } catch (e: Exception) {
            status = DownloadStatus.FAILED
            e.printStackTrace()
            Log.d(TAG, "standings: ${e.message}")
            ""
        }

        return string
    }

    fun userStatus(username: String, count: Int): String {
        status = DownloadStatus.OK

        val string = try {
            val url = String.format(URLS.USER_STATUS, username, 20)
            URL(url).readText()
        } catch (e: Exception) {
            status = DownloadStatus.FAILED
            e.printStackTrace()
            Log.d(TAG, "userStatus: ${e.message}")
            ""
        }

        return string
    }

    private fun appendHandles(handles: List<Username>): String {
        val sb = StringBuilder()
        for(str in handles) {
            sb.append("${str.name};")
        }
        return sb.toString()
    }

    //-----------------------------------PREVIOUS MATERIAL----------------------------------------//
    /*fun download(type: DownloadType, handles: List<Username>?, contestId: Int?): String {

        status = DownloadStatus.OK

        if(type != DownloadType.CONTESTS && handles == null) {
            throw IllegalArgumentException("Handles not provided to download query $type")
        }

        if(type == DownloadType.STANDINGS && contestId == null) {
            throw IllegalArgumentException("Contest Id not provided to download query $type")
        }

        val ret = try {
            when (type) {
                DownloadType.AUTHENTICATE -> {
                    val url = String.format(URLS.USERINFO, handles!![0].name)
                    URL(url).readText()
                }

                DownloadType.USERINFO -> {
                    val handlesString = appendHandles(handles!!)
                    val url = String.format(URLS.USERINFO, handlesString)
                    URL(url).readText()
                }

                DownloadType.CONTESTS -> {
                    URL(URLS.CONTESTS).readText()
                }

                DownloadType.STANDINGS -> {
                    val handlesString = appendHandles(handles!!)
                    val url = String.format(URLS.STANDINGS, contestId!!, handlesString)
                    URL(url).readText()
                }

                DownloadType.USER_STATUS -> {
                    val url = String.format(URLS.USER_STATUS, handles!![0].name)
                    URL(url).readText()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d(TAG, "download: Error downloading: error message -> ${e.message}" +
                    "\nDownload type -> $type" +
                    "\nHandles -> $handles")
            status = DownloadStatus.FAILED
            "Error downloading: error message -> ${e.message}"
        }

        return ret
    }*/
}



