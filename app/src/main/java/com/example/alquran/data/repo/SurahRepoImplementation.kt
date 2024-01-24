package com.example.alquran.data.repo


import com.example.alquran.data.remote.QuranAPI
import com.example.alquran.data.remote.dto.SurahDto
import com.example.alquran.di.SurasList

import com.example.alquran.domain.repo.SurahListRepo
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

class SurahRepoImplementation  @Inject constructor (
    @SurasList private val api: QuranAPI) :SurahListRepo {
    override suspend fun getSurahList()
    :  SurahDto   {
        return api.getSurahList()
    }
}