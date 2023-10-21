package com.example.alquran.domain.repo

import com.example.alquran.domain.model.SurahModel
import retrofit2.http.GET

interface SurahListRepo {

        @GET("surah")
        suspend fun getSurahList(): SurahModel
}