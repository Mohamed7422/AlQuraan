package com.example.alquran.domain.get_surahListTranslation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.alquran.Resources
import com.example.alquran.data.remote.suraTranslationDTO.SurahTranslationDTO
import com.example.alquran.domain.repo.SurahListTranslationRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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


//    fun invoke():Flow<Resources<>>

    operator fun invoke(language:String,surahId:Int):Flow<Resources< SurahTranslationDTO>> = flow {
        emit(Resources.Loading<SurahTranslationDTO>())
        try {
            val surahListTranslation = repo.getSurahListTranslation(language, surahId)
            emit(Resources.Success<SurahTranslationDTO>(surahListTranslation))

        }catch (e:HttpException){
            emit(Resources.Error< SurahTranslationDTO>(e.localizedMessage?:"Unexpected Error"))
        }catch (e:IOException){
            emit(Resources.Error< SurahTranslationDTO>(e.localizedMessage?:"Unexpected Error"))

        }
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