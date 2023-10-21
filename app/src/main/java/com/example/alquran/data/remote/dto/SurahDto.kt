package com.example.alquran.data.remote.dto

import com.example.alquran.domain.model.SurahModel
import com.google.gson.annotations.SerializedName

data class SurahDto(

    @SerializedName("data")
    val list: List<SurahData>,
)


fun SurahDto.toSurahModel(): SurahModel {
    return SurahModel(
            list=list
    )
}




