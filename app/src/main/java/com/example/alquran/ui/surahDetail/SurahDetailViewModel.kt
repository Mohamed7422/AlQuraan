package com.example.alquran.ui.surahDetail


import android.util.Log
import androidx.lifecycle.*
import com.example.alquran.data.remote.dto.SurahData
import com.example.alquran.data.remote.suraTranslationDTO.SurahTranslationDTO
import com.example.alquran.domain.get_surahListTranslation.GetSurahListTranslationUseCase
import com.example.alquran.domain.model.SurahDetailModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SurahDetailViewModel @Inject constructor (
      private val getSurahDetailUseCase: GetSurahListTranslationUseCase
 ): ViewModel() {

     private val originalList = MutableLiveData<SurahTranslationDTO?>()
     private val _list = MutableLiveData<SurahTranslationDTO?>()
     val list: LiveData<SurahTranslationDTO?> = _list

    /*
    * fetch the data
    * */

    private fun getSurahDetail(language:String,surahId:Int) {

        viewModelScope.launch {

            getSurahDetailUseCase.getSurahListTranslation(language,surahId)
            getSurahDetailUseCase.surahListTranslation().observeForever{
                suraDetailModelDTO->
                    originalList.value = suraDetailModelDTO
                    _list.value =suraDetailModelDTO
            }


            }
        }

    /*
    * get the argument of language and The sura item
    * and get the translation of it to append it to user in UI
    * */
    fun addSurahItem(language: String,surahData: SurahData?){
        surahData?.let {
            getSurahDetail(language,it.number)
        }

    }

     /*
     *Take the search text from user and return the list after
     *filtering it by the search text
     */
     fun filterList(filterText:String){
        val originList = originalList.value
        if (filterText.isNullOrBlank()){
            _list.value = originList
        }else{
            val filteredDataList = originList?.result?.filter {
                it.aya.equals(filterText, ignoreCase = true )
                //You can add more filters here.
            }
            _list.value = filteredDataList?.let { SurahTranslationDTO(it) }
        }

    }

    }















