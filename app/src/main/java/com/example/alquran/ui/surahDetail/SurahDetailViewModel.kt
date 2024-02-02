package com.example.alquran.ui.surahDetail


import android.telephony.SignalStrength
import android.util.Log
import androidx.lifecycle.*
import com.example.alquran.Resources
import com.example.alquran.data.remote.suraTranslationDTO.SurahTranslationDTO
import com.example.alquran.domain.get_surahListTranslation.GetSurahListTranslationUseCase
import com.example.alquran.domain.model.SurahDetailModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SurahDetailViewModel @Inject constructor(

    private val getSurahDetailUseCase: GetSurahListTranslationUseCase
) : ViewModel() {


   val searchQuery = MutableLiveData<String>()


     private val originalList = MutableLiveData<SurahTranslationDTO?>()

    // ************************************************************* //

    private val _suraDetailsList = MutableStateFlow(SuraDetailsState())

    val suraDetailsList : StateFlow<SuraDetailsState> = _suraDetailsList



    private val _surahQariSelected = MutableLiveData<String>()
      val surahQariSelected :LiveData<String> = _surahQariSelected
    init {
        searchQuery.observeForever{query->
            filterList(query)

        }
    }
    private var language   = ""
    private var surahItemNumber: Int = 0
    private var surahItemNumberForPlayer: Int = 0
    private var surahQariSelection :String = ""

    fun getSurahDetail(language: String, surahId: Int) {


        getSurahDetailUseCase(language, surahId).onEach { result ->
            when (result) {

                is Resources.Success -> {
                    originalList.value =  result.data
                    _suraDetailsList.value = SuraDetailsState(surahDetailsList= result.data)
                        println("FromVieuModel search: ${searchQuery.value}")

//                        _suraDetailsList.value = SuraDetailsState(surahDetailsList = result.data)

                        filterList(searchQuery.value)
                }

                is Resources.Error -> {
                    _suraDetailsList.value =
                        SuraDetailsState(error = result.message ?: "Un expected error")
                    Log.i("VieuModelTag", "result data : ${result.message?:"Null"}")

                }


                is Resources.Loading -> {
                    _suraDetailsList.value = SuraDetailsState(loading = true)
                    Log.i("VieuModelTag", "result data : ${result.message?:"Null"}")

                }

            }

        }.launchIn(viewModelScope)



//            getSurahDetailUseCase.getSurahListTranslation(language,surahId)
//            getSurahDetailUseCase.surahListTranslation().observeForever{
//                suraDetailModelDTO->
//                    originalList.value = suraDetailModelDTO
//                    _list.value =suraDetailModelDTO
//            }



    }

    private fun filterList (search:String?){
        val allList = originalList.value?.result

        val filteredItems =
            if ( search.isNullOrBlank()){
                allList

        }else{
            allList?.filter { it.aya.equals(search,ignoreCase = true) }

        }

        _suraDetailsList.value = SuraDetailsState(surahDetailsList = filteredItems?.let {
            SurahTranslationDTO(it)
        })

        println("FromVieuModel list: $filteredItems")
       println("FromVieuModel search: $search")


    }

//    fun setSearchQuery(query: String) {
//        _searchQuery.value = query
//        filterList(query)
//    }

    /*
    * get the argument of language and The sura item
    * and get the translation of it to append it to user in UI
    * */
    fun addSurahItem(lan: String, surahNum: Int) {
        language = lan
        surahItemNumber = surahNum
        println("language and sura num from addSurahITem  $language and $surahItemNumber")
        getSurahDetail(lan,surahNum)
        println("SurahDetails  ${suraDetailsList.value}")

    }

    fun addSurahNumForPlayer(surahNum: Int){
        surahItemNumberForPlayer = surahNum
    }
    fun prepareSuraNumberForPlayer() = run {
        var surahNumString:String?=""
        surahNumString = if (surahItemNumberForPlayer<10){
            "00$surahItemNumberForPlayer"
        }else if (surahItemNumberForPlayer<100){
            "0$surahItemNumberForPlayer"
        }else{
            "$surahItemNumberForPlayer"
        }
        surahNumString
    }

    fun addQariSelection(qari: String) {
        _surahQariSelected.value  = qari
    }

    fun getQariSelection():String{
        return  surahQariSelection
    }


    /*
    *Take the search text from user and return the list after
    *filtering it by the search text
    */
//     fun filterList(filterText:String){
//        val originList = originalList.value
//        if (filterText.isNullOrBlank()){
//            _list.value = originList
//        }else{
//            val filteredDataList = originList?.result?.filter {
//                it.aya.equals(filterText, ignoreCase = true )
//                //You can add more filters here.
//            }
//            _list.value = filteredDataList?.let { SurahTranslationDTO(it) }
//        }
//
//    }

}















