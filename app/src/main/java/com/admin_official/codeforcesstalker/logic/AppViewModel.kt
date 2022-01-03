package com.admin_official.codeforcesstalker.logic

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.admin_official.codeforcesstalker.*
import com.admin_official.codeforcesstalker.data.Username
import com.admin_official.codeforcesstalker.data.UsernameDatabase
import com.admin_official.codeforcesstalker.objects.Contest
import com.admin_official.codeforcesstalker.objects.ContestHandle
import com.admin_official.codeforcesstalker.objects.Handle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.*

private const val TAG = "De_AppViewModel"

class AppViewModel(application: Application): AndroidViewModel(application), ParseListener {

    private var usernameDao = UsernameDatabase.getInstance(application).userDao()

    // Live Data------------------------------------------------------------------------------------
    val usernames: LiveData<List<Username>>
        get() = usernameDao.query()

    private var pHandles = MutableLiveData<List<Handle>>()
    val handles: LiveData<List<Handle>>
        get() = pHandles

    private var pAuthenticate = MutableLiveData<Boolean>()
    val authenticate: LiveData<Boolean>
        get() = pAuthenticate

    private var pContests = MutableLiveData<List<Contest>>()
    val contests: LiveData<List<Contest>>
        get() = pContests

    private var pStandings = MutableLiveData<List<ContestHandle>>()
    val standings: LiveData<List<ContestHandle>>
        get() = pStandings

    //----------------------------------------------------------------------------------------------

    init {
        Log.d(TAG, "init: initialized")
    }

    fun loadHandles(handles: List<Username>) {
        if(handles.isEmpty()) pHandles.postValue(Collections.emptyList())
        viewModelScope.launch(Dispatchers.IO) {
            Downloader().apply {
                val handlesJson = userInfo(handles)
                if(status == DownloadStatus.OK) JsonParse(this@AppViewModel).run {parseUserInfo(handlesJson)}
            }
        }
    }

    fun loadContests() {
        viewModelScope.launch(Dispatchers.IO) {
            Downloader().apply {
                val contestJson = contests()
                if(status == DownloadStatus.OK) JsonParse(this@AppViewModel).run {parseContests(contestJson)}
            }
        }
    }

    fun loadStandings(handles: List<Username>, id: Int) {

        viewModelScope.launch(Dispatchers.IO) {
            Downloader().apply {
                val standingsJson = standings(handles, id)
                if(status == DownloadStatus.OK) JsonParse(this@AppViewModel).run {parseStandings(standingsJson)}
            }
        }
    }

    fun addHandle(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            Downloader().apply {
                val authenticateJson = authenticate(username)
                if(status == DownloadStatus.OK) {
                    pAuthenticate.postValue(true)

                    usernameDao.addUsername(Username(JSONObject(authenticateJson)
                        .getJSONArray("result")
                        .getJSONObject(0)
                        .getString("handle")))

                } else pAuthenticate.postValue(false)
            }
        }
    }

    // helper function
    fun addHandle2(username: String) {
        viewModelScope.launch(Dispatchers.IO){
            usernameDao.addUsername(Username(username))
        }
    }

    fun delHandle(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(TAG, "delHandle: $username")
            usernameDao.delUsername(username)
        }
    }

    override fun authenticateCallback(boolean: Boolean) {
        pAuthenticate.postValue(boolean)
    }

    override fun userInfoCallback(problems: List<Handle>) {
        pHandles.postValue(problems)
    }

    override fun contestsCallback(contests: List<Contest>) {
        pContests.postValue(contests)
    }

    override fun standingsCallback(handles: List<ContestHandle>) {
        pStandings.postValue(handles)
    }
}