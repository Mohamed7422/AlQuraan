package com.example.alquran.data.remote.suraTranslationDTO

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class SurahTranslationDetails(
    val id: String,
    val sura: String,
    val aya: String,
    @SerializedName("arabic_text")
    val arabicText: String,
    val translation: String,
    val footnotes: String,

): Parcelable