package com.example.alquran.di

import com.example.alquran.common.Constants
import com.example.alquran.data.remote.QuranAPI
import com.example.alquran.data.remote.QuranTranslationAPI
import com.example.alquran.data.repo.SurahListTranslationRepoImpl
import com.example.alquran.data.repo.SurahRepoImplementation
import com.example.alquran.domain.repo.SurahListRepo
import com.example.alquran.domain.repo.SurahListTranslationRepo
import com.example.alquran.domain.get_surahListTranslation.GetSurahListTranslationUseCase
import com.example.alquran.domain.use_case.get_surahList.GetSurahUseCase
import com.example.alquran.ui.main.MainViewModel


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//it informs Hilt how to provide instances of certain types.

//to tell Hilt which Android class
// each module will be used or installed in.
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
   fun provideSurahApi():QuranAPI{
       return Retrofit.Builder()
           .baseUrl(Constants.BASE_URL)
           .addConverterFactory(GsonConverterFactory.create())
           .build()
           .create(QuranAPI::class.java)
   }

    @Provides
    @Singleton
    fun provideSurahTranslationApi():QuranTranslationAPI{
         return Retrofit.Builder()
             .baseUrl(Constants.SURA_TRANS_BASE_URL)
             .addConverterFactory(GsonConverterFactory.create())
             .build()
             .create(QuranTranslationAPI::class.java)
    }

    @Provides
    @Singleton
    fun providesSurahRepository(api: QuranAPI):SurahListRepo{
        return SurahRepoImplementation(api)
    }


    @Provides
    @Singleton
    fun providesSurahUseCase(repo: SurahListRepo): GetSurahUseCase {
        return GetSurahUseCase(repo)
    }

    @Provides
    @Singleton
    fun providesSurahViewModel(useCase: GetSurahUseCase): MainViewModel{
        return MainViewModel(useCase)
    }

    @Provides
    @Singleton
    fun providesSurahListTranslationRepository(api: QuranTranslationAPI):SurahListTranslationRepo{
        return SurahListTranslationRepoImpl(api)
    }


    @Provides
    @Singleton
    fun providesSurahListTranslationUseCase(repo: SurahListTranslationRepo): GetSurahListTranslationUseCase {
        return GetSurahListTranslationUseCase(repo)
    }

    /**if u make a vieumodel here */
}