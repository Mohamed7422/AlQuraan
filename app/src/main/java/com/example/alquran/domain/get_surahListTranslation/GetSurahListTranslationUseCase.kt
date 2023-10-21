package com.example.alquran.domain.get_surahListTranslation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.alquran.data.remote.suraTranslationDTO.SurahTranslationDTO
import com.example.alquran.domain.model.SurahDetailModel

import com.example.alquran.domain.repo.SurahListTranslationRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetSurahListTranslationUseCase @Inject constructor(
    private val repo: SurahListTranslationRepo
) {

     private val _surahListTranslation = MutableLiveData<SurahTranslationDTO>()
     fun surahListTranslation():LiveData<SurahTranslationDTO>{
        return _surahListTranslation
    }


    suspend fun getSurahListTranslation(language:String,surahId:Int){

             try {
              val result = withContext(Dispatchers.IO){

                  repo.getSurahListTranslation(language,surahId)
              }
                 _surahListTranslation.value = result
                 Log.i("DataFromRepoDetails", "${_surahListTranslation.value}")
             }catch (e:IOException){

                 Log.i("ErrorFromIOdetails", "An IO error occurred: ${e.message}")
             }catch (e:HttpException){

                 Log.i("ErrorFromIOdetails", "An HTTP error occurred: ${e.message}")

              }
         }





}