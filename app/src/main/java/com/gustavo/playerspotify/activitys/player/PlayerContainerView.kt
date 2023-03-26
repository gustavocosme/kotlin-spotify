package com.gustavo.playerspotify.activitys.player

import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatSeekBar
import com.gustavo.playerspotify.R

interface PlayerViewProtocol {

}

interface PlayerViewDelegate {

}

class PlayerContainerView(playerActivity: PlayerActivity) {

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

    init {
        this.initEventsButtons()
    }


    //region # EVENTS BUTTONS

    private fun initEventsButtons() {

        this.nextImageButton.setOnClickListener {

        }

        this.prevImageButton.setOnClickListener {

        }

        this.prevImageButton.setOnClickListener {

        }

        this.playImageButton.setOnClickListener {

        }
    }
    //endregion
}