package com.example.mediaplayer

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.mediaplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var check = false
    private var mediaPlayer: MediaPlayer? = null
    private var audioManager: AudioManager? = null
    private val songList = Music.list
    private var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        backgroundAnimation()
        val button = binding.buttonIV

        button.setImageResource(R.drawable.start)
        playSound()
        initControls()
    }

    @SuppressLint("SetTextI18n")
    private fun playSound(){
        binding.buttonIV.setOnClickListener {
            if (check) {
                binding.buttonIV.setImageResource(R.drawable.start)
                if (mediaPlayer != null) mediaPlayer?.pause()
                check = false
            } else {
                binding.buttonIV.setImageResource(R.drawable.pause)
                if (mediaPlayer == null) {
                    mediaPlayer = MediaPlayer.create(this, songList[index].music)
                    initializeSeekBar()
                }
                mediaPlayer?.start()
                binding.nameSongTV.text = "Играет ${songList[index].title}"
                check = true
            }
        }

            binding.stopsBT.setOnClickListener{
                if (mediaPlayer != null) {
                    mediaPlayer?.stop()
                    mediaPlayer?.reset()
                    mediaPlayer?.release()
                    mediaPlayer = null
                }
                binding.buttonIV.setImageResource(R.drawable.start)
                check = false
                binding.nameSongTV.text = ""
            }

            binding.nextBT.setOnClickListener {
                if (mediaPlayer?.isPlaying == true) {
                    mediaPlayer?.stop()
                    mediaPlayer?.reset()
                    mediaPlayer?.release()
                    mediaPlayer = null
                }

                if (index < songList.size - 1 && index >= 0){
                    index++
                }else if (index == songList.size - 1){
                    index = 0
                }

                mediaPlayer = MediaPlayer.create(this, songList[index].music)
                mediaPlayer?.start()
                binding.buttonIV.setImageResource(R.drawable.pause)
                binding.nameSongTV.text = "Играет ${songList[index].title}"
                check = true
            }
            binding.previousBT.setOnClickListener {
                if (mediaPlayer?.isPlaying == true) {
                    mediaPlayer?.stop()
                    mediaPlayer?.reset()
                    mediaPlayer?.release()
                    mediaPlayer = null
                }

                if (index > 0 && index <= songList.size - 1){
                    index--
                }else if (index == 0){
                    index = songList.size - 1
                }

                mediaPlayer = MediaPlayer.create(this, songList[index].music)
                mediaPlayer?.start()
                binding.buttonIV.setImageResource(R.drawable.pause)
                binding.nameSongTV.text = "Играет ${songList[index].title}"
                check = true
            }

            binding.seekBar.setOnSeekBarChangeListener(@SuppressLint("AppCompatCustomView")
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser) mediaPlayer?.seekTo(progress)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {

                }

            })

        }

    private fun initControls()
    {
        try {
            binding.seekBarVolume
            audioManager = getSystemService (Context.AUDIO_SERVICE) as AudioManager?
            binding.seekBarVolume.setMax(
                audioManager!!.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
            )
            binding.seekBarVolume.progress = audioManager!!.getStreamVolume(AudioManager.STREAM_MUSIC)

            binding.seekBarVolume.setOnSeekBarChangeListener(@SuppressLint("AppCompatCustomView")
            object : SeekBar.OnSeekBarChangeListener {

                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    audioManager!!.setStreamVolume(
                        AudioManager.STREAM_MUSIC,
                        progress, 0)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {

                }
            })
        } catch (e: Exception ) {
            e.printStackTrace()
        }
    }


    private fun initializeSeekBar() {
        binding.seekBar.max = mediaPlayer!!.duration
        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                try {
                    binding.seekBar.progress = mediaPlayer!!.currentPosition
                    handler.postDelayed(this, 1000)
                }catch (e: Exception){
                    binding.seekBar.progress = 0
                }
            }

        }, 0)
    }

    private fun backgroundAnimation() {
        val animation: AnimationDrawable = binding.main.background as AnimationDrawable
        animation.apply {
            setEnterFadeDuration(1000)
            setExitFadeDuration(3000)
            start()
        }
    }


}


