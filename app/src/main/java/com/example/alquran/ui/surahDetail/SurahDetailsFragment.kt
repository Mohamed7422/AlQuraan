package com.example.alquran.ui.surahDetail

import android.content.pm.ActivityInfo
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.GradientDrawable.Orientation
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.ButtonBarLayout
import androidx.compose.ui.text.toLowerCase
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.whenStarted
import com.example.alquran.R
import com.example.alquran.databinding.FragmentSurahDetailsBinding
import com.example.alquran.ui.base.BaseFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [SurahDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class SurahDetailsFragment : BaseFragment<FragmentSurahDetailsBinding>(R.layout.fragment_surah_details) {


    private lateinit var lan:String
    var english: String = "english_saheeh"
    var french: String = "french_montada"

    private val viewModel : SurahDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        //Lock the orientation to be portrait
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        return  super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onStart() {

        super.onStart()

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        val adapter = SurahDetailsAdapter()
        binding.rvAyat.adapter = adapter

        val surahItem =  SurahDetailsFragmentArgs.fromBundle(requireArguments()).suraItem
        binding.suraItem = surahItem
        lan = english
//        viewModel.addSurahItem(lan,surahItem)


        binding.filterSearch.addTextChangedListener {

            //make a fun takes the string and pass it to
            //vieumodel filterlist function
            //then make vieumodel to filter the list that'll passed to RV via
            //the layout
            filterList(it.toString())
            Log.i("SEARCH",it.toString())
        }

        //Change the Progress bar color
        val color = ContextCompat.getColor(requireContext(), R.color.Progress_Bar_Color)
        binding.pbLoading.indeterminateDrawable?.colorFilter = PorterDuffColorFilter(color,PorterDuff.Mode.SRC_IN)


        //observe the setting btn click
        binding.settingBtn.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(requireContext(),R.style.BottomSheetDialogTheme)
            val inflater = LayoutInflater.from(requireContext())
            val view = inflater.inflate(R.layout.button_sheet_dialouge
                ,binding.root.findViewById(R.id.sheet_container))

            view.findViewById<View>(R.id.save).setOnClickListener{
                val radioGroup: RadioGroup = view.findViewById(R.id.selection_container)
                val selectedId = radioGroup.checkedRadioButtonId
                val radioButton: RadioButton = view.findViewById(selectedId)

                if (selectedId==-1){
                    Toast.makeText(requireContext(),"nothing selected",Toast.LENGTH_LONG).show()
                }else {

                    Toast.makeText(requireContext()," selected",Toast.LENGTH_LONG).show()

                }
                /**
                 *The prop here the call did not make untill click on slection
                 * and if selection is not work
                 **/

                if (radioButton.text.toString().lowercase().trim() == "french")
                {
                    lan = french
                    radioButton.isSelected.let { true }
                }else if (radioButton.text.toString().lowercase().trim() == "english")
                {
                    lan = english

                }

                viewModel.addSurahItem(lan,surahItem)

                bottomSheetDialog.dismiss()
            }


            bottomSheetDialog.setContentView(view)
            bottomSheetDialog.show()

        }
        viewModel.addSurahItem(lan,surahItem)



    }



    fun filterList(filterText : String){
        viewModel.filterList(filterText)
    }


}



