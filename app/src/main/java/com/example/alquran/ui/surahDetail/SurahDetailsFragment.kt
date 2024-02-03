package com.example.alquran.ui.surahDetail

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.alquran.R
import com.example.alquran.data.remote.dto.SurahData
import com.example.alquran.databinding.FragmentSurahDetailsBinding
import com.example.alquran.ui.base.BaseFragment
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class SurahDetailsFragment :

    BaseFragment<FragmentSurahDetailsBinding>(R.layout.fragment_surah_details) {
    private lateinit var suraItem: SurahData

    private val english: String = "english_saheeh"
    private val french: String = "french_montada"
    private val menshawiQari: String = "muhammad_siddeeq_al-minshaawee"
    private val farisAbadQari: String = "fares"
    private var writeQariSharedPref: SharedPreferences? = null
    private var readQariSharedPref: SharedPreferences? = null

    lateinit var suraPlayerService: SuraPlayerService

    private var suraPlayer: ExoPlayer? = null

    var isBound = false
    lateinit var myConnectionService: ServiceConnection


    private companion object {
        private const val SuraDetailsTag = "SuraDetailsTag"
    }

    private val viewModel: SurahDetailViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // Fragment should handle menu actions


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        writeQariSharedPref =
            activity?.getSharedPreferences(getString(R.string.qurisharedpref), Context.MODE_PRIVATE)
        readQariSharedPref =
            activity?.getSharedPreferences(getString(R.string.qurisharedpref), Context.MODE_PRIVATE)


        val qariFromSharedPref =
            readQariSharedPref?.getString(getString(R.string.qarikey), menshawiQari)

        qariFromSharedPref?.let {
            viewModel.addQariSelection(it)
        }


        suraItem = SurahDetailsFragmentArgs.fromBundle(requireArguments()).suraItem
        Log.i("FrgTagTag", "suraItem : ${suraItem.number}")
        viewModel.addSurahNumForPlayer(suraItem.number)


        //Lock the orientation to be portrait
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun doBindService() {
        val bindIntent = Intent(requireContext(), SuraPlayerService::class.java)
        requireContext().bindService(bindIntent, myConnectionService, Context.BIND_AUTO_CREATE)
    }

    private fun initializeSuraPlayer(uri: String) {

        suraPlayer = suraPlayerService.suraPlayer


        suraPlayerService.setMediaSource(uri)
        binding.playerView.player = suraPlayer


//        suraPlayer = ExoPlayer.Builder(requireContext()).build()


//
//        val mediaItem = MediaItem.Builder()
//                .setUri(uri)
//                .build()
//
//            val mediaSource = ProgressiveMediaSource.Factory(
//                DefaultDataSource.Factory(requireContext())
//            ).createMediaSource(mediaItem)
//
//            suraPlayer?.apply {
//                setMediaSource(mediaSource)
//                prepare()
//                binding.playerView.player = this
//            }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val surahNumberPreparedForPlayer = viewModel.prepareSuraNumberForPlayer()

        viewModel.surahQariSelected.observe(viewLifecycleOwner) {
            Log.i("SurFRgTag", "Sura $surahNumberPreparedForPlayer : $it")
            val uri =
                "https://download.quranicaudio.com/quran/$it/$surahNumberPreparedForPlayer.mp3"
            Log.i("SurFRgTag", "Su $uri ")


//            Log.i("SurFRgTag","Suraurl $uri ")

//            initializeSuraPlayer(uri)

            myConnectionService = object : ServiceConnection {
                override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                    val playerService: SuraPlayerService.MyPlayerBoundService =
                        service as SuraPlayerService.MyPlayerBoundService
                    suraPlayerService = playerService.getService()

                    initializeSuraPlayer(uri)

                    isBound = true

                }

                override fun onServiceDisconnected(name: ComponentName?) {
                    isBound = false
                }

            }
            doBindService()


        }

        val toolbar = binding.toolbar
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)

        val actionBar = (activity as? AppCompatActivity)?.supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)

        // Set the title of the ActionBar, if needed
        actionBar?.title = getString(R.string.surah_ayat)


        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }


        val adapter = SurahDetailsAdapter()
        adapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        binding.rvAyat.adapter = adapter
        binding.viewModel = viewModel

        val writelangSharedPreferences =
            activity?.getSharedPreferences(
                getString(R.string.LanguageSharedPref),
                Context.MODE_PRIVATE
            )


        binding.suraItem = suraItem

        val readLanguageSharedPref =
            activity?.getSharedPreferences(
                getString(R.string.LanguageSharedPref),
                Context.MODE_PRIVATE
            )


        val languageFromSharedPref =
            readLanguageSharedPref?.getString(getString(R.string.languagekey), english)


        languageFromSharedPref?.let {
            viewModel.getSurahDetail(it, suraItem.number)
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

            val quraaRadioGroup: RadioGroup =
                btnSheetDialogView.findViewById(R.id.selection_qari_container)


            val currentValue =
                writelangSharedPreferences?.getString(getString(R.string.languagekey), english)
            Log.i(SuraDetailsTag, "current value $currentValue")



            if (!currentValue.isNullOrEmpty()) {
                when (currentValue) {
                    french -> radioGroup.check(R.id.fr_trans)
                    english -> radioGroup.check(R.id.eng_trans)
                }
            }

            val currentQariSelection =
                writeQariSharedPref?.getString(getString(R.string.qarikey), menshawiQari)

            if (!currentQariSelection.isNullOrEmpty()) {
                when (currentQariSelection) {
                    menshawiQari -> quraaRadioGroup.check(R.id.menshawi)
                    farisAbadQari -> quraaRadioGroup.check(R.id.faris)
                }
            }

            saveButton.setOnClickListener {
                val selectedRadioId = radioGroup.checkedRadioButtonId
                val radioButton: RadioButton = btnSheetDialogView.findViewById(selectedRadioId)

                val selectedQariId = quraaRadioGroup.checkedRadioButtonId
                val qariBtn: RadioButton = btnSheetDialogView.findViewById(selectedQariId)


                val qari = when (qariBtn.text.toString()) {
                    getString(R.string.muhammad_sideq_al_minshawe_muratl) -> menshawiQari
                    getString(R.string.faris_abbad) -> farisAbadQari
                    else -> ""
                }

                if (qari.isNotEmpty()) {
                    writeQariSharedPref?.edit()
                        ?.putString(getString(R.string.qarikey), qari)?.commit()
                    viewModel.addQariSelection(qari)

                }

                val language = when (radioButton.text.toString().lowercase().trim()) {
                    "french" -> french
                    "english" -> english
                    else -> ""
                }

                if (language.isNotEmpty()) {
                    writelangSharedPreferences?.edit()
                        ?.putString(getString(R.string.languagekey), language)?.commit()
                    viewModel.addSurahItem(language, suraItem.number)
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

        doUnbindService()

    }

    private fun doUnbindService() {
        if (isBound) {
            context?.unbindService(myConnectionService)
            isBound = false
        }
    }

}



