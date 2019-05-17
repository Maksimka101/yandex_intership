package com.maksimka101.git_repo.handler

import com.maksimka101.git_repo.models.GitRepository
import com.maksimka101.git_repo.models.RequestHead
import com.maksimka101.git_repo.repository.OkHttpRequest
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import org.intellij.lang.annotations.Language
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class RepositoryHandler {

    private val client = OkHttpClient()
    private val request = OkHttpRequest(client)
    private var url = "https://api.github.com/search/repositories?q="

    fun getRepository(name: String, language: String, listener: OnRepositoryLoad) {
        request.get(parseUrl(name, language, url), object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("FAIL ${e.message}")
            }

            override fun onResponse(call: Call?, response: Response) {
                val responseData = response.body()?.string()
                try {
                    val json = JSONObject(responseData)
                    val requestHead = RequestHead(json.getInt("total_count"))
                    val jsonRepositoryArr = json.getJSONArray("items")
                    val repositoryArr = ArrayList<GitRepository>()

                    for (i in 0 until jsonRepositoryArr.length()-1) {
                        repositoryArr.add(GitRepository(jsonRepositoryArr.getJSONObject(i)))
                    }

                    listener.onGetRepository(repositoryArr, requestHead)
                }
                catch (e: JSONException) {
                    println("REPO HANDLER except ${e.message}")
                }
            }
        })
    }

    private fun parseUrl(name: String, language: String, url: String): String {
        var requestUrl = url
        if (name.isNotEmpty()) {
            requestUrl += name
            requestUrl += "+"
        }

        requestUrl += "language:$language"

        return requestUrl
    }
}

interface OnRepositoryLoad {
    fun onGetRepository(repository: ArrayList<GitRepository>, requestHead: RequestHead)
}