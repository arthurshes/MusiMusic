package com.example.damnappgtp

import android.net.Uri

data class
MyLocalAudio(
    val uri:Uri,
    val displayName:String,
    val id:Long,
    val artist:String,
    val data:String,
    val duration:Int,
    val title:String
)
