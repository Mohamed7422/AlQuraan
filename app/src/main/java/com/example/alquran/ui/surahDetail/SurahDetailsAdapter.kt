package com.example.alquran.ui.surahDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.alquran.data.remote.dto.SurahData
import com.example.alquran.data.remote.suraTranslationDTO.SurahTranslationDetails
import com.example.alquran.databinding.SurahDetailsItemBinding
import com.example.alquran.databinding.SurahItemBinding


class SurahDetailsAdapter :
    ListAdapter<SurahTranslationDetails, SurahDetailsAdapter.SurahPropertyViewHolder>(DiffCallback){



    companion object DiffCallback : DiffUtil.ItemCallback<SurahTranslationDetails>() {
     override fun areItemsTheSame(oldItem: SurahTranslationDetails, newItem: SurahTranslationDetails): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: SurahTranslationDetails, newItem: SurahTranslationDetails): Boolean {
            return oldItem.id == newItem.id
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int): SurahPropertyViewHolder {
           return SurahPropertyViewHolder(
               SurahDetailsItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
           )

    }



    override fun onBindViewHolder(holder: SurahPropertyViewHolder, position: Int) {
        val surah = getItem(position)
        holder.bind(surah)
    }



     inner class SurahPropertyViewHolder(private var binding: SurahDetailsItemBinding)
         :RecyclerView.ViewHolder(binding.root)
     {
         fun bind(surah: SurahTranslationDetails){
             binding.surahDetails = surah
             binding.executePendingBindings()
         }



     }


}
