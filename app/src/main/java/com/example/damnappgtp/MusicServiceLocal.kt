package com.example.damnappgtp

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.IBinder
import androidx.core.net.toUri
import com.example.damnappgtp.ApplicatioMain.Companion.CURRENT_TRACk
import java.io.IOException

class MusicServiceLocal:Service(){
    val mediaPlayer = MediaPlayer()

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.getStringExtra(CURRENT_TRACk)?.toUri()?.let { playMusic(it) }
        return super.onStartCommand(intent, flags, startId)
    }

    fun playMusic(uri:Uri){
        try {
                 mediaPlayer.setDataSource(this, uri)
                    mediaPlayer.prepare()
        }catch (e:IOException){
            e.printStackTrace()
        }
        mediaPlayer.start()
    }

}