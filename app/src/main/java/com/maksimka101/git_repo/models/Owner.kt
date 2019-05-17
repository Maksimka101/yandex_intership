package com.maksimka101.git_repo.models

import org.json.JSONException
import org.json.JSONObject

class Owner(json: JSONObject) {
    var login: String? = null
    var url: String? = null
    var avatarUrl: String? = null
    var id: Int? = null

    init {
        try {
            login = json.getString("login")
            url = json.getString("url")
            id = json.getInt("id")
            avatarUrl = json.getString("avatar_url")
        }
        catch (e: JSONException) {
            println("OWNER JSON EX ${e.message}")
        }
    }
}