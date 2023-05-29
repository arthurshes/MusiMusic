package com.example.damnappgtp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.damnappgtp.databinding.MusicItemBinding

class AdapterTest:RecyclerView.Adapter<AdapterTest.MyViewHolder>() {

    var onItemClick:((MyLocalAudio)->Unit)?=null

    inner class MyViewHolder(val binding: MusicItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(MusicItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
       return diffResult.currentList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentMusic = diffResult.currentList[position]
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(currentMusic)
        }
        holder.binding.apply {
            textViewArtist.text = currentMusic.artist
            textViewMusic.text = currentMusic.title
            textViewTime.text = currentMusic.duration.toString()
        }
    }

    private val diffCall = object : DiffUtil.ItemCallback<MyLocalAudio>(){
        override fun areItemsTheSame(oldItem: MyLocalAudio, newItem: MyLocalAudio): Boolean {
            return oldItem.uri == newItem.uri
        }

        override fun areContentsTheSame(oldItem: MyLocalAudio, newItem: MyLocalAudio): Boolean {
         return oldItem == newItem
        }

    }

    val diffResult = AsyncListDiffer(this,diffCall)

}