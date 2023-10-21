package com.example.alquran.data.remote.suraTranslationDTO

import com.example.alquran.domain.model.SurahDetailModel
import com.google.gson.annotations.SerializedName

data class SurahTranslationDTO(
    @SerializedName("result")
    val result: List<SurahTranslationDetails>
)

//fun SurahTranslationDTO.toSurahDetailModel():SurahDetailModel{
//    return SurahDetailModel(
//        result = result
//    )
//}