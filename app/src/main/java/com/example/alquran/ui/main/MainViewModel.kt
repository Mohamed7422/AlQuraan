package com.example.alquran.ui.main


import androidx.lifecycle.*
import com.example.alquran.Resources
import com.example.alquran.data.remote.dto.SurahData
import com.example.alquran.data.remote.dto.SurahDto
import com.example.alquran.domain.model.SurahModel
import com.example.alquran.domain.use_case.get_surahList.GetSurahUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (
      private val getSurahUseCase: GetSurahUseCase
): ViewModel() {

     private val _navigateToSelectedProperty = MutableLiveData<Resources<SurahDto>>()
     val navigateToSelectedProperty: LiveData<Resources<SurahDto>> = _navigateToSelectedProperty


    private val _selectedItem = MutableLiveData<SurahData>()
    val selectedItem :LiveData<SurahData> = _selectedItem


    private val _retryAction = MutableLiveData<Unit>()
    val retryAction :LiveData<Unit> get() = _retryAction

    init {
        getSurah()

    }


      fun getSurah() {
        viewModelScope.launch {
            getSurahUseCase().collect{
                    result ->
                _navigateToSelectedProperty.value = result
            }
        }
    }

//    val listData :LiveData<SurahModel> = getSurahUseCase.getSurahListLiveData()

    fun onSurahItemClick(surahData: SurahData){
        _selectedItem.value = surahData

    }

    fun onNavigateToSelectedPropertyCompleted(){
        _selectedItem.value = null
    }

    fun onRetryActionButton(){
        _retryAction.value = Unit
    }


    }














