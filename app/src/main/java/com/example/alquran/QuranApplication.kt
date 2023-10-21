package com.example.alquran

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


/**
 * HiltAndroid App annotation
triggers Hilt's code generation,
including a base class for your application that serves
as the application-level dependency container.
 */
@HiltAndroidApp
class QuranApplication :Application(){
}