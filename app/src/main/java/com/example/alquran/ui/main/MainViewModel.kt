package com.example.alquran.ui.main


import androidx.lifecycle.*
import com.example.alquran.Resources
import com.example.alquran.data.remote.dto.SurahData
import com.example.alquran.data.remote.dto.SurahDto
import com.example.alquran.domain.use_case.get_surahList.GetSurahUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getSurahUseCase: GetSurahUseCase
) : ViewModel() {

    private val _navigateToSelectedProperty = MutableLiveData<Resources<SurahDto>>()
    val navigateToSelectedProperty: LiveData<Resources<SurahDto>> = _navigateToSelectedProperty

    private val originalList = MutableLiveData<Resources<SurahDto>>()

    private val _selectedItem = MutableLiveData<SurahData>()
    val selectedItem: LiveData<SurahData> = _selectedItem


    private val _retryAction = MutableLiveData<Unit>()
    val retryAction: LiveData<Unit> get() = _retryAction

    val searchQuery = MutableLiveData<String>()


    init {
        searchQuery.observeForever {

            println(" value of kuery${it}")
            filterList(it)
        }
        getSurah()
    }

    private fun filterList(searchStr: String?) {
        val allList: List<SurahData>? = originalList.value?.data?.list

        val filteredList = if (searchStr.isNullOrBlank()) {
            allList

            //return the full list
        } else {
            //return the filtered list
            allList?.filter {
                it.number.toString().equals(searchStr, ignoreCase = true)
            }

        }

        val result = if (allList == null) {
             return
        } else {
            filteredList?.let { SurahDto(it) }?.let { Resources.Success<SurahDto>(it) }
        }
        _navigateToSelectedProperty.value = result
        println("Filtered List ${result?.data}")

    }


    fun getSurah() {
        viewModelScope.launch {
            getSurahUseCase().collect { result ->
                originalList.value = result

                println(" value of List ${originalList.value}")

                _navigateToSelectedProperty.value = result

            }
        }
    }

//    val listData :LiveData<SurahModel> = getSurahUseCase.getSurahListLiveData()

    fun onSurahItemClick(surahData: SurahData) {
        _selectedItem.value = surahData
    }

    fun onNavigateToSelectedPropertyCompleted() {
        _selectedItem.value = null
    }

    fun onRetryActionButton() {
        _retryAction.value = Unit
    }


}














