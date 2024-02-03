package com.example.alquran.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.alquran.R
import com.example.alquran.databinding.FragmentSplashBinding
import com.example.alquran.databinding.SurahDetailsItemBinding

class SplashFragment : Fragment() {

    lateinit var splashBinding: FragmentSplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        splashBinding = FragmentSplashBinding.inflate(layoutInflater,container,false)


        return splashBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        splashBinding.img.apply {
            alpha = 0.5f
            scaleX = 0.5f
            scaleY =0.5f

        }

        splashBinding.img.animate().setDuration(3000).scaleX(1.2f)
            .scaleY(1.2f).alpha(1f).withEndAction{
                findNavController().navigate(R.id.action_splashFragment_to_mainFragment)

            }.start()

    }




}