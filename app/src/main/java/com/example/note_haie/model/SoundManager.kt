package com.example.note_haie.model

import android.content.Context
import android.media.MediaPlayer
import com.example.note_haie.R

fun playSong(context: Context) {

    val mediaPlayer = MediaPlayer.create(context, R.raw.task_completion_1)
    mediaPlayer.setOnCompletionListener { mp ->
        mp.release()
    }
    mediaPlayer.start()

}
