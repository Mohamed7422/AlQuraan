package com.example.alquran.ui.surahDetail

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.alquran.R
import com.example.alquran.databinding.FragmentSurahDetailsBinding
import com.example.alquran.ui.base.BaseFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class SurahDetailsFragment :
    BaseFragment<FragmentSurahDetailsBinding>(R.layout.fragment_surah_details) {

   private val english: String = "english_saheeh"
   private val french: String = "french_montada"

    private companion object {
        private const val SuraDetailsTag = "SuraDetailsTag"
    }

    private val viewModel: SurahDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // Fragment should handle menu actions
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        //Lock the orientation to be portrait
//        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        return super.onCreateView(inflater, container, savedInstanceState)
    }






    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val toolbar = binding.toolbar
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)

        val actionBar = (activity as? AppCompatActivity)?.supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)

        // Set the title of the ActionBar, if needed
        actionBar?.title = "Surah Ayat"

        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }


        val adapter = SurahDetailsAdapter()
        adapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        binding.rvAyat.adapter = adapter
        binding.viewModel = viewModel

        val langSharedPreferences =
            activity?.getSharedPreferences(
                getString(R.string.LanguageSharedPref),
                Context.MODE_PRIVATE
            )

        val surahItem = SurahDetailsFragmentArgs.fromBundle(requireArguments()).suraItem

        binding.suraItem = surahItem

        val readLanguageSharedPref =
            activity?.getSharedPreferences(
                getString(R.string.LanguageSharedPref),
                Context.MODE_PRIVATE
            )

        val languageFromSharedPref =
            readLanguageSharedPref?.getString(getString(R.string.languagekey), english)


        languageFromSharedPref?.let {
            viewModel.getSurahDetail(it, surahItem.number)
        }

        binding.settingBtn.setOnClickListener {


            val bottomSheetDialog =
                BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
            val inflater = LayoutInflater.from(requireContext())
            val btnSheetDialogView = inflater.inflate(
                R.layout.button_sheet_dialouge, binding.root.findViewById(R.id.sheet_container)
            )

            val radioGroup: RadioGroup = btnSheetDialogView.findViewById(R.id.selection_container)
            val saveButton: View = btnSheetDialogView.findViewById(R.id.save)


            val currentValue =
                langSharedPreferences?.getString(getString(R.string.languagekey), english)
            Log.i(SuraDetailsTag, "current value $currentValue")

            if (!currentValue.isNullOrEmpty()) {
                when (currentValue) {
                    french -> radioGroup.check(R.id.fr_trans)
                    english -> radioGroup.check(R.id.eng_trans)
                }
            }

            saveButton.setOnClickListener {
                val selectedRadioId = radioGroup.checkedRadioButtonId
                val radioButton: RadioButton = btnSheetDialogView.findViewById(selectedRadioId)

                val language = when (radioButton.text.toString().lowercase().trim()) {
                    "french" -> french
                    "english" -> english
                    else -> ""
                }

                if (language.isNotEmpty()) {
                    langSharedPreferences?.edit()
                        ?.putString(getString(R.string.languagekey), language)?.commit()
                    viewModel.addSurahItem(language, surahItem.number)
                }
                bottomSheetDialog.dismiss()
            }

            bottomSheetDialog.setContentView(btnSheetDialogView)
            bottomSheetDialog.show()

        }

        val color = ContextCompat.getColor(requireContext(), R.color.Progress_Bar_Color)
        binding.pbLoading.indeterminateDrawable?.colorFilter =
            PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
    }


//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.toolbar_menu,menu)
//        super.onCreateOptionsMenu(menu, inflater)
//
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Navigate back
                findNavController().navigateUp() // or navigate(R.id.home_fragment) if you want to navigate to a specific fragment
                true
            }

//            R.id.action_search -> {
//                Toast.makeText(this.requireContext(),"OpenSearch",Toast.LENGTH_SHORT).show()
//                true
//            }
//            R.id.action_settings -> {
//                Toast.makeText(this.requireContext(),"OpenSettings",Toast.LENGTH_SHORT).show()
//                true
//            }

            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onStart() {

        super.onStart()


//        binding.filterSearch.addTextChangedListener {
//
//            //make a fun takes the string and pass it to
//            //vieumodel filterlist function
//            //then make vieumodel to filter the list that'll passed to RV via
//            //the layout
//            filterList(it.toString())
//            Log.i("SEARCH",it.toString())
//        }

    }


//    fun filterList(filterText : String){
//        viewModel.filterList(filterText)
//    }


    override fun onDestroyView() {
        super.onDestroyView()
        // Reset the action bar
        val actionBar = (activity as? AppCompatActivity)?.supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(false)
        actionBar?.setDisplayShowHomeEnabled(false)
        // Optionally, reset the title

    }

}



