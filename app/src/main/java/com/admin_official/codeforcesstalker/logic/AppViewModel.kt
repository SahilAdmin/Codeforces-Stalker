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
import com.admin_official.codeforcesstalker.objects.Problem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.*

private const val TAG = "De_AppViewModel"

class AppViewModel(application: Application): AndroidViewModel(application), ParseListener {

    private var usernameDao = UsernameDatabase.getInstance(application).userDao()

    // Live Data------------------------------------------------------------------------------------
    val pUsernames: LiveData<List<Username>>
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

    private var pStandings = MutableLiveData<List<ContestHandle>?>()
    val standings: MutableLiveData<List<ContestHandle>?>
        get() = pStandings

    private var pUserStatus = MutableLiveData<List<Problem>?>()
    val userStatus: MutableLiveData<List<Problem>?>
        get() = pUserStatus

    private var currUsers: List<Username> = Collections.emptyList()

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

    fun loadStandings(id: Int, handles: List<Username>) {

        viewModelScope.launch(Dispatchers.IO) {
            Downloader().apply {
                val standingsJson = standings(handles, id)
                if (status == DownloadStatus.OK) JsonParse(this@AppViewModel).run {
                    parseStandings(
                        standingsJson
                    )

                }
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

    fun loadStatus(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            Downloader().apply {
                val statusJson = userStatus(username, 100)
                if(status == DownloadStatus.OK) {
                    JsonParse(this@AppViewModel).run {parseUserStatus(statusJson)}
                }
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
            usernameDao.delUsername(username)
        }
    }

    fun nullContestStandings() {pStandings.value = null}

    fun nullUserStatus() {pUserStatus.value = null}

    override fun authenticateCallback(boolean: Boolean) {
        pAuthenticate.postValue(boolean)
    }

    override fun userInfoCallback(handles: List<Handle>) {
        pHandles.postValue(handles)
    }

    override fun contestsCallback(contests: List<Contest>) {
        pContests.postValue(contests)
    }

    override fun standingsCallback(handles: List<ContestHandle>) {
        pStandings.postValue(handles)
    }

    override fun userStatusCallback(problems: List<Problem>) {
        pUserStatus.postValue(problems)
    }
}