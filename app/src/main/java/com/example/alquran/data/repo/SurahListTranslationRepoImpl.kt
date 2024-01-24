package com.example.alquran.data.repo

import com.example.alquran.data.remote.QuranAPI

import com.example.alquran.data.remote.suraTranslationDTO.SurahTranslationDTO
import com.example.alquran.di.SurahDetails

import com.example.alquran.domain.repo.SurahListTranslationRepo

import javax.inject.Inject

class SurahListTranslationRepoImpl  @Inject constructor (
     @SurahDetails private val api: QuranAPI
        ) :SurahListTranslationRepo
{
    override suspend fun getSurahListTranslation(
        language: String,
        surahId: Int
    ) : SurahTranslationDTO {
        return api.getSurahDetailsWithTranslation(language,surahId)
    }

}