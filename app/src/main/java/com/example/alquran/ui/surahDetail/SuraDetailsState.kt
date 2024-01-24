package com.example.alquran.ui.surahDetail

import com.example.alquran.data.remote.suraTranslationDTO.SurahTranslationDTO

data class SuraDetailsState(
  var loading:Boolean = false,
  val surahDetailsList :SurahTranslationDTO ?= null,
  val error:String?=""
)
