package com.admin_official.codeforcesstalker

import android.util.Log
import com.admin_official.codeforcesstalker.data.Username
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.lang.StringBuilder
import java.net.URL

object URLS {
    const val USERINFO = "https://codeforces.com/api/user.info?handles=%s"
    const val USER_STATUS = "https://codeforces.com/api/user.status?handle=%s&from=1&count=100"
    const val CONTESTS = "https://codeforces.com/api/contest.list"
    const val STANDINGS = "https://codeforces.com/api/contest.standings?contestId=%d&handles=%s"
}

enum class DownloadType {
    USERINFO, USER_STATUS, AUTHENTICATE, CONTESTS, STANDINGS
}

enum class DownloadStatus {
    OK, FAILED
}

private const val TAG = "De_Downloader"

class Downloader {

    var status = DownloadStatus.OK

    fun download(type: DownloadType, handles: List<Username>?, contestId: Int?): String {

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
                    val url = String.format(URLS.USERINFO, handles!![0])
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
    }

    private fun appendHandles(handles: List<Username>): String {
        val sb = StringBuilder()
        for(str in handles) {
            sb.append("${str.name};")
        }
        return sb.toString()
    }
}



