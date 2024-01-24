package com.example.alquran.data.remote

import com.example.alquran.data.remote.dto.SurahDto
import com.example.alquran.data.remote.suraTranslationDTO.SurahTranslationDTO
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path

interface QuranAPI {

    @GET("surah")
    suspend fun getSurahList():  SurahDto

    @GET("sura/{language}/{id}")
    suspend fun getSurahDetailsWithTranslation(@Path("language") lan:String,
                                               @Path("id") surahId:Int) : SurahTranslationDTO

}