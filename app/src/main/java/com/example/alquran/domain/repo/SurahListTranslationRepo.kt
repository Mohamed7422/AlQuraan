package com.example.alquran.domain.repo

 import com.example.alquran.data.remote.suraTranslationDTO.SurahTranslationDTO
 import com.example.alquran.domain.model.SurahDetailModel

 import retrofit2.http.GET

interface SurahListTranslationRepo {

        @GET("sura/{language}/{id}")
        suspend fun getSurahListTranslation(
            language:String,
            surahId: Int)
        : SurahTranslationDTO
}