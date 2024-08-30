package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class SongAdapter(private val context: Context, private val songs: List<Song>) : BaseAdapter() {

    override fun getCount(): Int = songs.size

    override fun getItem(position: Int): Any = songs[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.song_item, parent, false)

        val song = songs[position]

        view.findViewById<ImageView>(R.id.albumImage).setImageResource(song.albumImage)
        view.findViewById<TextView>(R.id.songTitle).text = song.title
        view.findViewById<TextView>(R.id.songArtist).text = song.artist

        return view
    }
}