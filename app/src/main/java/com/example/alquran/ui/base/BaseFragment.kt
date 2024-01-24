package com.example.alquran.ui.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleRegistry


open class BaseFragment<T : ViewDataBinding>(@LayoutRes private val layoutResId : Int) : Fragment(){

    private var _binding : T? = null
    val binding : T get() = _binding!!

    // Make it open, so it can be overridden in child fragments
    open fun T.initialize(){}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        // Calling the extension function
        binding.initialize()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
//        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

    }
}