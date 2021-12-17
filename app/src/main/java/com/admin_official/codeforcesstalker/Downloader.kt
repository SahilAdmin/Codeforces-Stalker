package com.admin_official.codeforcesstalker

import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.lang.StringBuilder
import java.net.URL

object URLS {
    const val USERINFO = "https://codeforces.com/api/user.info?handles=%s"
    const val USER_STATUS = "https://codeforces.com/api/user.status?handle=%s&from=1&count=10"
    const val CONTESTS = "https://codeforces.com/api/contest.list"
    const val STANDINGS = "https://codeforces.com/api/contest.standings?contestId=%d&handles=%s"
}

enum class DownloadType {
    USERINFO, USER_STATUS, AUTHENTICATE, CONTESTS, STANDINGS
}

enum class DownloadStatus {
    OK, FAILED
}

interface DownloadListener {
    fun onDownloadComplete(string: String, status: DownloadStatus, type: DownloadType)
}

private const val TAG = "De_Downloader"

class Downloader(private val listener: DownloadListener) {

    var downloadStatus = DownloadStatus.OK

    fun execute(type: DownloadType, handles: List<String>?, contestId: Int?) {
        GlobalScope.launch {
            download(type, handles, contestId)
        }
    }

    fun download(type: DownloadType, handles: List<String>?, contestId: Int?) {
        downloadStatus = DownloadStatus.OK

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
                    val url = String.format(URLS.USER_STATUS, handles!![0])
                    URL(url).readText()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d(TAG, "download: Error downloading: error message -> ${e.message}")
            downloadStatus = DownloadStatus.FAILED
            "download: Error downloading: error message -> ${e.message}"
        }

        listener.onDownloadComplete(ret, downloadStatus, type)
    }

    private fun appendHandles(handles: List<String>): String {
        val sb = StringBuilder()
        for(str in handles) {
            sb.append("$str;")
        }
        return sb.toString()
    }
}



