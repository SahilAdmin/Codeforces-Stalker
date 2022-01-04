package com.admin_official.codeforcesstalker.objects

class Problem(val contestId: Int,
              val index: String,
              val name: String,
              val tags: List<String>,
              val verdict: String,
              val programmingLanguage: String,
              val timeStamp: Long,
              val rating: Int) {

    override fun toString(): String {
        return "\nProblem(contestId=$contestId, index='$index', name='$name', tags=$tags, verdict='$verdict', programmingLanguage='$programmingLanguage', timeStamp='$timeStamp')"
    }
}