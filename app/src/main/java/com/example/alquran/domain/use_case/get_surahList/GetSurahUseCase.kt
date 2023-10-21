package com.example.alquran.domain.use_case.get_surahList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.example.alquran.domain.model.SurahModel
import com.example.alquran.domain.repo.SurahListRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetSurahUseCase @Inject constructor(
    private val repo: SurahListRepo
) {

    private val surahList = MutableLiveData<SurahModel>()
    fun getSurahListLiveData():LiveData<SurahModel>{
        return surahList
    }

    suspend fun getSurahList(){

             try {
              val result = withContext(Dispatchers.IO){
                  repo.getSurahList()
              }
                 surahList.value = result
                 Log.i("DataFromRepo", "${surahList.value?.list}")
             }catch (e:IOException){

                 Log.i("ErrorFromIO", "An IO error occurred: ${e.message}")
             }catch (e:HttpException){

                 Log.i("ErrorFromIO", "An HTTP error occurred: ${e.message}")

              }
         }





}