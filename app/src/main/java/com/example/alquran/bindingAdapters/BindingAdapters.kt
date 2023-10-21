package com.example.alquran.bindingAdapters

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.alquran.data.remote.dto.SurahData
import com.example.alquran.data.remote.suraTranslationDTO.SurahTranslationDTO
import com.example.alquran.data.remote.suraTranslationDTO.SurahTranslationDetails
import com.example.alquran.domain.model.SurahDetailModel
import com.example.alquran.domain.model.SurahModel
import com.example.alquran.ui.main.SurahAdapter
import com.example.alquran.ui.surahDetail.SurahDetailsAdapter


@BindingAdapter("goneIfNotNull")
fun goneIfNotNull(view: View, it: Any?) {
    view.visibility = if (it != null) View.GONE else View.VISIBLE
}

@BindingAdapter("listData")
fun RecyclerView.bindRecyclerView(data: SurahModel?) {
        val list= ArrayList<SurahData>()

        data?.list?.let  { surahData ->
        list.addAll(surahData)
        }
//        data?.list?.filter {
//                surahData ->
//        }
        val adapter = this.adapter as? SurahAdapter
        adapter?.submitList(list)

}

@BindingAdapter("listTransData")
fun RecyclerView.bindRecyclerView(data: SurahTranslationDTO?) {
        val list= ArrayList<SurahTranslationDetails>()
        data?.result?.let  { surahDataTranslation ->  list.addAll(surahDataTranslation) }
        val adapter = this.adapter as? SurahDetailsAdapter
        adapter?.submitList(list)


}










