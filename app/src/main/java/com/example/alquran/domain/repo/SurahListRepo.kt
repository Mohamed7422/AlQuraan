package com.example.alquran.domain.repo

import com.example.alquran.data.remote.dto.SurahDto
import com.example.alquran.domain.model.SurahModel
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface SurahListRepo {

        @GET("surah")
        suspend fun getSurahList(): SurahDto
}