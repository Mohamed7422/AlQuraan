package com.example.alquran.data.repo

import com.example.alquran.data.remote.QuranTranslationAPI
import com.example.alquran.data.remote.suraTranslationDTO.SurahTranslationDTO
 import com.example.alquran.domain.model.SurahDetailModel
import com.example.alquran.domain.repo.SurahListTranslationRepo

import javax.inject.Inject

class SurahListTranslationRepoImpl  @Inject constructor (
      private val api: QuranTranslationAPI
        ) :SurahListTranslationRepo
{
    override suspend fun getSurahListTranslation(
        language: String,
        surahId: Int
    ) : SurahTranslationDTO {
        return api.getSurahDetailsWithTranslation(language,surahId)
    }

}