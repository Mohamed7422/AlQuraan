package com.example.alquran.ui.main

import android.os.Build
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController

import com.example.alquran.R
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
        @RequiresApi(Build.VERSION_CODES.O)
        override fun onStart() {
            super.onStart()
            binding.lifecycleOwner = viewLifecycleOwner
            binding.viewModel = viewModel
            val adapter = SurahAdapter {

                viewModel.onSurahItemClick(it)

            }
            binding.surahRecyclerView.adapter = adapter

            viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner){
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