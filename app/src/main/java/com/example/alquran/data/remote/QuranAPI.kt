package com.example.alquran.data.remote

import com.example.alquran.data.remote.dto.SurahDto
import retrofit2.http.GET

interface QuranAPI {

    @GET("surah")
    suspend fun getSurah(): SurahDto

}