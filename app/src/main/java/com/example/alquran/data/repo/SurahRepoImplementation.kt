package com.example.alquran.data.repo


import com.example.alquran.data.remote.QuranAPI
import com.example.alquran.data.remote.dto.toSurahModel
import com.example.alquran.domain.model.SurahModel
import com.example.alquran.domain.repo.SurahListRepo

import javax.inject.Inject

class SurahRepoImplementation  @Inject constructor (
    private val api: QuranAPI
        )
    :SurahListRepo
{
    override suspend fun getSurahList()
    : SurahModel  {
        return api.getSurah().toSurahModel()
    }
}