package com.maksimka101.git_repo.models

import org.json.JSONException
import org.json.JSONObject

class GitRepository(json: JSONObject) {

    var id: Int? = null
    var name: String? = null
    var fullName: String? = null
    var isPrivate: Boolean? = null
    var url: String? = null
    var language: String? = null
    var description: String? = null
    var score: Double? = null
    var owner: Owner? = null

    init {
        try {
            owner = Owner(json.getJSONObject("owner"))
            id = json.getInt("id")
            name = json.getString("name")
            fullName = json.getString("full_name")
            url = json.getString("html_url")
            language = json.getString("language")
            description = json.getString("description")
            score = json.getDouble("score")
            isPrivate = json.getBoolean("private")
        }
        catch (e: JSONException) {
            println("JSON EXCEPTION ${e.message}")
        }
    }
/*
    constructor(id: Int,
                name: String,
                fullName: String,
                isPrivate: Boolean,
                url: String,
                language: String,
                description: String,
                score: Double,
                owner: Owner
    ) {
        this.id = id
        this.name = name
        this.description = description
        this.fullName = fullName
        this.url = url
        this.isPrivate = isPrivate
        this.score = score
        this.owner = owner
        this.language = language
    }
*/
}