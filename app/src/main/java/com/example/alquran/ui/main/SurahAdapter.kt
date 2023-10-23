package com.example.alquran.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.alquran.data.remote.dto.SurahData
import com.example.alquran.data.remote.suraTranslationDTO.SurahTranslationDetails
import com.example.alquran.databinding.SurahDetailsItemBinding
import com.example.alquran.databinding.SurahItemBinding
import com.example.alquran.domain.model.SurahModel


class SurahAdapter(private val listener:(SurahData) -> Unit) :
    ListAdapter<SurahData, SurahAdapter.SurahPropertyViewHolder>(DiffCallback){

    companion object DiffCallback : DiffUtil.ItemCallback<SurahData>() {
     override fun areItemsTheSame(oldItem: SurahData, newItem: SurahData): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: SurahData, newItem: SurahData): Boolean {
            return oldItem.number == newItem.number
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int): SurahPropertyViewHolder {
           return SurahPropertyViewHolder(
               SurahItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
           )

    }



    override fun onBindViewHolder(holder: SurahPropertyViewHolder, position: Int) {
        val surah = getItem(position)
        holder.bind(surah)
    }



     inner class SurahPropertyViewHolder(private var binding: SurahItemBinding)
         :RecyclerView.ViewHolder(binding.root)
     {
         fun bind(surah: SurahData){
              binding.surah= surah
             binding.executePendingBindings()
         }

         init {
             binding.root.setOnClickListener{
                 val position  = bindingAdapterPosition
                 if(position!=RecyclerView.NO_POSITION){
                    val item =  getItem(position)
                     if (item != null) {
                         listener.invoke(item) //or listener(item)
                     }
                 }
             }
         }
     }


    interface OnSurahClickListener {
        fun onSurahClick(surah: SurahData) // (SurahData) -> Unit
    }
}
