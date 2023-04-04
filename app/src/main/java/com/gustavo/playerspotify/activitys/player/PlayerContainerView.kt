package com.gustavo.playerspotify.activitys.player

import android.widget.*
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.widget.AppCompatSeekBar
import com.gustavo.playerspotify.R
import com.gustavo.playerspotify.extentions.loadCoverSpotify
import com.spotify.protocol.types.Track


interface PlayerContainerViewProtocol {
    fun setInfo(track: Track)
}

interface PlayerContainerViewDelegate {
    fun onClickNext()
    fun onClickPrev()
    fun onClickPlay()
    fun onClickPause()
    fun onChangeSlider(msPosition: Long)
}

class PlayerContainerView(
    private val playerActivity: PlayerActivity,
    private val delegate: PlayerContainerViewDelegate)
    : PlayerContainerViewProtocol {

    private var nextImageButton: ImageButton = playerActivity.findViewById(R.id.next)
    private var prevImageButton: ImageButton = playerActivity.findViewById(R.id.previous)
    private var playImageButton: ImageButton = playerActivity.findViewById(R.id.play)
    private var photoCardImageView: ImageView = playerActivity.findViewById(R.id.image)
    private var loadProgressBar: ProgressBar = playerActivity.findViewById(R.id.load)
    private var categoryTextView: TextView = playerActivity.findViewById(R.id.category)
    private var titleTextView: TextView = playerActivity.findViewById(R.id.title)
    private var timeTextView: TextView = playerActivity.findViewById(R.id.time)
    private var countTextView: TextView = playerActivity.findViewById(R.id.count)
    private var descriptionTextView: TextView = playerActivity.findViewById(R.id.description)
    private var sliderSeekBar: AppCompatSeekBar = playerActivity.findViewById(R.id.slider)
    private var isPlay = true

    override fun setInfo(track: Track) {
        this.initEventsButtons()
        this.initEventSlider()

        this.categoryTextView.text = track.album.name
        this.titleTextView.text = track.name
        this.descriptionTextView.text = track.artist.name
        this.photoCardImageView.loadCoverSpotify(track.imageUri)
    }

    private fun initEventSlider() {
        this.sliderSeekBar.max = 1000
        this.sliderSeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {

            }
        })
    }

    //region # EVENTS BUTTONS

    private fun initEventsButtons() {

        this.nextImageButton.setOnClickListener {
            this.delegate.onClickNext()
        }

        this.prevImageButton.setOnClickListener {
            this.delegate.onClickPrev()
        }

        this.playImageButton.setOnClickListener {
            if(this.isPlay) {
                this.isPlay = false
                this.delegate.onClickPause()
                this.playImageButton.setImageResource(R.drawable.baseline_pause_circle_filled)
            } else {
                this.isPlay = true
                this.delegate.onClickPlay()
                this.playImageButton.setImageResource(R.drawable.round_play_circle)
            }
        }
    }

    //endregion
}