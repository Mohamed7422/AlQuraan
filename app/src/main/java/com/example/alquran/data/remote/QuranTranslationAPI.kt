package com.example.alquran.data.remote

 import com.example.alquran.data.remote.suraTranslationDTO.SurahTranslationDTO
 import retrofit2.http.GET
 import retrofit2.http.Path

interface QuranTranslationAPI {

    @GET("sura/{language}/{id}")
    suspend fun getSurahDetailsWithTranslation(@Path("language") lan:String ,
                                               @Path("id") surahId:Int) : SurahTranslationDTO
}