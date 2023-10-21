package com.example.alquran.domain.model

 import com.example.alquran.data.remote.suraTranslationDTO.SurahTranslationDetails


data class SurahDetailModel(
    val result: List<SurahTranslationDetails>,
)


