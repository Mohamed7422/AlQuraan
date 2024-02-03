package com.example.alquran.bindingAdapters

import android.opengl.Visibility
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.alquran.R
import com.example.alquran.Resources
import com.example.alquran.data.remote.dto.SurahData
import com.example.alquran.data.remote.dto.SurahDto
import com.example.alquran.data.remote.suraTranslationDTO.SurahTranslationDTO
import com.example.alquran.data.remote.suraTranslationDTO.SurahTranslationDetails
import com.example.alquran.domain.model.SurahDetailModel
import com.example.alquran.domain.model.SurahModel
import com.example.alquran.ui.main.SurahAdapter
import com.example.alquran.ui.surahDetail.SuraDetailsState
import com.example.alquran.ui.surahDetail.SurahDetailsAdapter
import kotlinx.coroutines.flow.StateFlow


@BindingAdapter("setVisibleListSuccess")
fun RecyclerView.setVisibleListSuccess(resources: Resources<SurahDto>?){
       visibility = if(resources is Resources.Success && !resources.data?.list.isNullOrEmpty()) View.VISIBLE else
           View.GONE
        if (resources is Resources.Success){
              val adapter = this.adapter as? SurahAdapter
                adapter?.submitList(resources.data?.list )
        }

}

@BindingAdapter("setVisibleSearchEditText")
fun setVisibleSearchEditText(view: EditText,resources: Resources<*>){

    if (resources is Resources.Success ){
        view.visibility = View.VISIBLE
    }else{
        view.visibility = View.GONE
    }
}

@BindingAdapter("setVisibleError")
fun setVisibleError(view: TextView,resources: Resources<*>){

        if (resources is Resources.Error ){
                view.visibility = View.VISIBLE
                view.text =
                    view.context.getString(R.string.unexpected_error_check_your_internet_connection)
        }else{
            view.visibility = View.GONE
        }
}

@BindingAdapter("setVisibleRetryBtn")
fun setVisibleRetryBtn(view: Button,resources: Resources<*>){

        if (resources is Resources.Error ){
                view.visibility = View.VISIBLE

        }else{
            view.visibility = View.GONE
        }
}



@BindingAdapter("setVisibleLoading")
fun setVisibleLoading(view: View,resources: Resources<*>){
        if (resources is Resources.Loading){
                view.visibility = View.VISIBLE
        }else{
                view.visibility = View.GONE
        }
}

@BindingAdapter("setVisibleLoadingFroSurahDetails")
fun setVisibleLoadingFroSurahDetails(view: View,state: StateFlow<SuraDetailsState>){


       view.visibility = if (state.value.loading) View.VISIBLE else   View.GONE

}

@BindingAdapter("setVisibleErrorForSurahDetails")
fun setVisibleErrorForSurahDetails(view: TextView,stateFlow: StateFlow<SuraDetailsState>){

    view.visibility = if (!stateFlow.value.error.isNullOrBlank()) View.VISIBLE else View.GONE
    view.text =  view.context.getString(R.string.unexpected_error_check_your_internet_connection)


}

@BindingAdapter("goneIfNotNull")
fun goneIfNotNull(view: View, it: Any?) {
    view.visibility = if (it != null) View.GONE else View.VISIBLE
}

@BindingAdapter("listTransData")
fun RecyclerView.listTransData(surahDetailsState:  StateFlow<SuraDetailsState> ) {


    val surahDetailsList = surahDetailsState.value.surahDetailsList
      println("surahDetailsList in binding adapter ${surahDetailsList?.result}" )
//    val list= ArrayList<SurahTranslationDetails>()
//        data?.result?.let  { surahDataTranslation ->  list.addAll(surahDataTranslation) }

    if (surahDetailsList?.result!=null){
        surahDetailsState.value.loading = false
        val adapter = this.adapter as? SurahDetailsAdapter
        adapter?.submitList(surahDetailsList.result)
    }

//    surahDetailsState?.asLiveData()?.observe(this.context as LifecycleOwner) { state ->
//        val surahDetailsList = state.surahDetailsList?.result
//        if (!state.loading && surahDetailsList != null) {
//            val adapter = this.adapter as? SurahDetailsAdapter
//            adapter?.submitList(surahDetailsList)
//        }
//    }


}










