package com.example.alquran.ui.main

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController

import com.example.alquran.R
import com.example.alquran.Resources
import com.example.alquran.data.remote.dto.SurahData
import com.example.alquran.databinding.FragmentMainBinding

import com.example.alquran.ui.base.BaseFragment
import com.example.alquran.ui.surahDetail.SurahDetailsAdapter
import dagger.hilt.android.AndroidEntryPoint



@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(R.layout.fragment_main)
//        , SurahAdapter.OnSurahClickListener
    {
        private val viewModel : MainViewModel by viewModels()

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

//            val supportActionBar = (activity as AppCompatActivity)?.supportActionBar
//            supportActionBar?.title = "Home"
        }
        @RequiresApi(Build.VERSION_CODES.O)
        override fun onStart() {
            super.onStart()
            binding.lifecycleOwner = viewLifecycleOwner

            binding.viewModel = viewModel


            val adapter = SurahAdapter {

                viewModel.onSurahItemClick(it)

            }

            val surahRecyclerView = binding.surahRecyclerView

            surahRecyclerView.setHasFixedSize(true)
            surahRecyclerView.setItemViewCacheSize(10)

            binding.surahRecyclerView.adapter = adapter

            viewModel.selectedItem.observe(viewLifecycleOwner){

                    suraItem ->
                if (null != suraItem){
                    findNavController().navigate(MainFragmentDirections.actionMainFragmentToSurahDetails(suraItem))
                    viewModel.onNavigateToSelectedPropertyCompleted()

                }
            }



         }

//        @RequiresApi(Build.VERSION_CODES.O)
//        override fun onSurahClick(surah: SurahData) {
//            //update the state observer on the view model
//                viewModel.onSurahItemClick(surah)
//
//        }
    }