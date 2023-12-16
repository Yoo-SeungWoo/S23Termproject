package kr.ac.kumoh.ce.s20210734.s23termproject

import retrofit2.http.GET

interface SkinApi {
    @GET("lol_skin")
    suspend fun getSkins(): List<Skin>
}