package com.example.damnappgtp

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import com.afollestad.assent.Permission
import com.afollestad.assent.askForPermissions
import com.example.damnappgtp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
     private lateinit var binding: ActivityMainBinding
     private var adapter:AdapterTest?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val service = MusicServiceLocal()
        adapter = AdapterTest()
        val selection = StringBuilder("is_music != 0 AND title != ''")
val mediaPlayer = MediaPlayer()
        // Display audios in alphabetical order based on their display name.
val deco = DividerItemDecoration(this,DividerItemDecoration.VERTICAL)
        binding?.apply {
            recycler.adapter = adapter
            recycler.addItemDecoration(deco)
        }
        askForPermissions(Permission.READ_EXTERNAL_STORAGE) {
          CoroutineScope(Dispatchers.IO).launch{
              val musics = getAllMusics()
              adapter?.diffResult?.submitList(musics)
          }
            adapter?.onItemClick = {
                if (service.mediaPlayer.isPlaying) {
                    service.mediaPlayer.stop()
                     service.playMusic(it.uri)
                } else {
                    val intent = Intent(this, MusicServiceLocal::class.java)
                    intent.putExtra(ApplicatioMain.CURRENT_TRACk, it.uri.toString())
                    startService(intent)
                }
            }

        }



    }

val musicResolverHelper = MusicResolverHelper(this)

    suspend fun getAllMusics():List<MyLocalAudio> = withContext(Dispatchers.IO){
        musicResolverHelper.getAudioData()
    }


}