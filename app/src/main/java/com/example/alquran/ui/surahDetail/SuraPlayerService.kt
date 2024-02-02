package com.example.alquran.ui.surahDetail

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Picture
import android.graphics.drawable.BitmapDrawable
import android.os.Binder
import android.os.IBinder
import android.widget.ImageView
import androidx.compose.ui.graphics.Canvas
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_MAX
import androidx.core.app.NotificationManagerCompat.IMPORTANCE_HIGH
import androidx.core.app.TaskStackBuilder
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLinkBuilder
import com.example.alquran.MainActivity
import com.example.alquran.R
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.google.android.exoplayer2.ui.PlayerNotificationManager.MediaDescriptionAdapter
import com.google.android.exoplayer2.ui.PlayerNotificationManager.Visibility
import com.google.android.exoplayer2.upstream.DefaultDataSource

//step1
class SuraPlayerService : Service() {

    //step3
    private val myPlayerBoundService:IBinder = MyPlayerBoundService()
    //Player
    var suraPlayer:ExoPlayer?=null
    private var playerNotificationManager:PlayerNotificationManager?=null



    override fun onBind(intent: Intent): IBinder {

        return myPlayerBoundService
    }

    override fun onCreate() {
        super.onCreate()
        //assign variables
        suraPlayer = ExoPlayer.Builder(applicationContext).build()


        //Setup Audio Attributes
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(C.USAGE_MEDIA)
            .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
            .build()

        suraPlayer?.setAudioAttributes(audioAttributes,true)


        //player Notification Manager
        val notificationId = 11111
        val playerChannelId = resources.getString(R.string.app_name)+" Surah Player Channel"
        playerNotificationManager = PlayerNotificationManager.Builder(this,notificationId,playerChannelId)
            .setNotificationListener(notificationListener)
            .setChannelImportance(IMPORTANCE_HIGH)
            .setMediaDescriptionAdapter(descriptionAdapter)
            .setSmallIconResourceId(R.drawable.quran)
            .setChannelDescriptionResourceId(R.string.app_name)
            .setNextActionIconResourceId(R.drawable.ic_skip_next)
            .setPreviousActionIconResourceId(R.drawable.ic_skip_previous)
            .setPauseActionIconResourceId(R.drawable.ic_pause)
            .setPlayActionIconResourceId(R.drawable.ic_play)
            .setChannelNameResourceId(R.string.app_name)
            .build()


        playerNotificationManager?.setPlayer(suraPlayer)
        playerNotificationManager?.setPriority(PRIORITY_MAX)
        playerNotificationManager?.setUseRewindAction(false)
        playerNotificationManager?.setUseFastForwardAction(false)

   }

    //Setup notification Adapter
    private val descriptionAdapter = object : MediaDescriptionAdapter {
        override fun getCurrentContentTitle(player: Player): CharSequence {
           return player.mediaMetadata.title.toString()
        }

        override fun createCurrentContentIntent(player: Player): PendingIntent? {
            val intent = Intent(applicationContext, MainActivity::class.java)
//            intent.action = Intent.ACTION_MAIN
//            intent.addCategory(Intent.CATEGORY_LAUNCHER)
//
//            val taskStackBuilder = TaskStackBuilder.create(applicationContext)
//            taskStackBuilder.addNextIntentWithParentStack(intent)
//
//            val detailsFragmentIntent = Intent(applicationContext, SurahDetailsFragment::class.java)
//            taskStackBuilder.addNextIntent(detailsFragmentIntent)
//            taskStackBuilder.getPendingIntent(0,PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
//
//            val pendigIntent = NavDeepLinkBuilder(applicationContext)
//                .setGraph(R.navigation.main_nav_graph)
//                .setDestination(R.id.surahDetails)
//                .createPendingIntent()
            return  null
        }

        override fun getCurrentContentText(player: Player): CharSequence? {
             return player.mediaMetadata.albumTitle
        }

        override fun getCurrentLargeIcon(
            player: Player,
            callback: PlayerNotificationManager.BitmapCallback
        ): Bitmap? {

            val koraanDrawable = ContextCompat.getDrawable(applicationContext, R.drawable.ic_details_bg)
            val koraanBitmap = koraanDrawable?.toBitmap()
            return koraanBitmap
        }



    }

    //Setup Notification Listener
    val notificationListener = object : PlayerNotificationManager.NotificationListener {
        override fun onNotificationCancelled(notificationId: Int, dismissedByUser: Boolean) {
            super.onNotificationCancelled(notificationId, dismissedByUser)

            stopForeground(true)
            if(suraPlayer?.isPlaying == true){
                suraPlayer?.pause()
            }

        }

        override fun onNotificationPosted(
            notificationId: Int,
            notification: Notification,
            ongoing: Boolean
        ) {
            super.onNotificationPosted(notificationId, notification, ongoing)
            startForeground(notificationId,notification)
        }
    }


    //step2
    //class binder for clients
    inner class MyPlayerBoundService():Binder(){

        fun getService():SuraPlayerService{
            return this@SuraPlayerService
        }

    }

    override fun onDestroy() {

        if (suraPlayer?.isPlaying == true){
            suraPlayer?.stop()
        }
        playerNotificationManager?.setPlayer(null)
        suraPlayer?.release()
        suraPlayer = null
        stopForeground(true)
        stopSelf()
        super.onDestroy()
    }

    fun setMediaSource(uri: String) {


          val mediaItem = MediaItem.Builder()
              .setUri(uri).build()

        val mediaSource = ProgressiveMediaSource.Factory(
            DefaultDataSource.Factory( applicationContext)
        ).createMediaSource(mediaItem)

        suraPlayer?.apply {

            setMediaSource(mediaSource)
            prepare()
        }
    }

}