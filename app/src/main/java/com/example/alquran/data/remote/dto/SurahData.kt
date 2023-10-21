package com.example.alquran.data.remote.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class SurahData(
    val number: Int,
    val name: String,
    val englishName: String,
    val englishNameTranslation: String,
    val numberOfAyahs: Int,
    val revelationType: String
) : Parcelable

//fun SurahData.toSurah(): Surah {
//    return Surah(
//        number = number,
//        name = name,
//        englishName = englishName,
//        englishNameTranslation = englishNameTranslation,
//        numberOfAyahs = numberOfAyahs,
//        revelationType = revelationType
//    )
//}

