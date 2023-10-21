package com.example.alquran.ui.main


import androidx.lifecycle.*
import com.example.alquran.data.remote.dto.SurahData
import com.example.alquran.domain.model.SurahModel
import com.example.alquran.domain.use_case.get_surahList.GetSurahUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (
      private val getSurahUseCase: GetSurahUseCase
): ViewModel() {

     private val _navigateToSelectedProperty = MutableLiveData<SurahData?>()
     val navigateToSelectedProperty: LiveData<SurahData?>
         get()=_navigateToSelectedProperty


    init {
        getSurah()
    }

    private fun getSurah() {
        viewModelScope.launch {
            //make the request
             getSurahUseCase.getSurahList()
            //getting data from source and add it to the observer
            }
        }
    val listData :LiveData<SurahModel> = getSurahUseCase.getSurahListLiveData()




    fun onSurahItemClick(surahData: SurahData){
        _navigateToSelectedProperty.value = surahData


    }

    fun onNavigateToSelectedPropertyCompleted(){
        _navigateToSelectedProperty.value = null
    }



    }














