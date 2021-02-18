package com.example.academy_proj2_githubapp.search.data

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TempTestNetworking {

    companion object {
        private const val CLIENT_ID = "Iv1.285bc9168541e271"
        private const val CLIENT_SECRET = "22b361ae6e2b0e23ec3edbb4e39eb89bceedce63"
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(SearchUsersService::class.java)

    fun getUsers() {
        val result = service.searchUsers("N", 10, 1).execute().body()
        val jsonResult = Gson().toJson(result)
        println(jsonResult)
    }

}
