package com.example.alquran.data.remote.dto


import com.google.gson.annotations.SerializedName

data class SurahDto(

    @SerializedName("data")
    val list: List<SurahData>,
)




