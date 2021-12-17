package com.admin_official.codeforcesstalker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private const val TAG = "De_MainActivity"

class MainActivity : AppCompatActivity(), DownloadListener, ParseListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val downloader = Downloader(this)
        downloader.execute(DownloadType.AUTHENTICATE, listOf("smahajan548424", "ashutosh.2805"), null)
    }

    override fun onDownloadComplete(string: String, status: DownloadStatus, type: DownloadType) {
        Log.d(TAG, "onDownloadComplete: downloadStatus: $status")
        Log.d(TAG, "onDownloadComplete: return: $string")
        
        JsonParse.parse(type, string, this, status)
    }

    override fun userInfoCallback(handles: List<Handle>) {
        Log.d(TAG, "userInfoCallback: handles")
    }

    override fun userStatusCallback(problems: List<Problem>) {
        Log.d(TAG, "userStatusCallback: $problems")
    }

    override fun contestsCallback(contests: List<Contest>) {
        Log.d(TAG, "contestsCallback: $contests")
    }

    override fun standingsCallback(handles: List<ContestHandle>) {
        Log.d(TAG, "standingsCallback: $handles")
    }

    override fun authenticateCallback(boolean: Boolean) {
        Log.d(TAG, "authenticateCallback: $boolean")
    }
}