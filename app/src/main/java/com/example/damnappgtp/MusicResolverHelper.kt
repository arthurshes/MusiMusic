package com.example.damnappgtp

import android.app.Application
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import androidx.annotation.WorkerThread


class MusicResolverHelper (private val context:Context) {
    private var mCursor:Cursor?=null

    private val projection:Array<String> = arrayOf(
        MediaStore.Audio.AudioColumns.DISPLAY_NAME,
        MediaStore.Audio.AudioColumns._ID,
        MediaStore.Audio.AudioColumns.TITLE,
        MediaStore.Audio.AudioColumns.DATA,
        MediaStore.Audio.AudioColumns.DURATION,
        MediaStore.Audio.AudioColumns.ARTIST
    )

    private var selectionClause:String? = "${MediaStore.Audio.AudioColumns.IS_MUSIC} = ?"
    private var selectiomArg = arrayOf("1")
    private val sortOrder = "${MediaStore.Audio.AudioColumns.DISPLAY_NAME} ASC"

    @WorkerThread
    fun getAudioData():List<MyLocalAudio>{
        return getCursorData()
    }

    private fun getCursorData():MutableList<MyLocalAudio>{
        val audioList = mutableListOf<MyLocalAudio>()
        mCursor = context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selectionClause,
            selectiomArg,
            sortOrder
        )
        mCursor?.use { cursor->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns._ID)
            val DisplayNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DISPLAY_NAME)
            val ArtistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ARTIST)
            val DataColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATA)
            val DurationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DURATION)
            val TitileColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.TITLE)
         cursor.apply {
             if(count==0){

             }else{
                 while (cursor.moveToNext()){
                     val displayName = getString(DisplayNameColumn)
                     val id = getLong(idColumn)
                     val artist = getString(ArtistColumn)
                     val title = getString(TitileColumn)
                     val data = getString(DataColumn)
                     val duration = getInt(DurationColumn)
                     val uri = ContentUris.withAppendedId(
                         MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                         id
                     )
                     audioList += MyLocalAudio(
                         uri, displayName, id, artist, data, duration, title
                     )
                 }
             }
         }

        }
        return audioList
    }
}