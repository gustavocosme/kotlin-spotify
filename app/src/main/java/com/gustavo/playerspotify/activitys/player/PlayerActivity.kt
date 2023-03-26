package com.gustavo.playerspotify.activitys.player

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.gustavo.playerspotify.R
import com.gustavo.playerspotify.manager.spotify.SpotifyManager
import com.gustavo.playerspotify.manager.spotify.SpotifyManagerDelegate
import com.gustavo.playerspotify.manager.spotify.SpotifyManagerProtocol

class PlayerActivity : AppCompatActivity(), SpotifyManagerDelegate, PlayerContainerViewDelegate {

    private val spotifyManager: SpotifyManagerProtocol = SpotifyManager.INSTANCE
    lateinit var playerContainerView: PlayerContainerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_player)
        playerContainerView = PlayerContainerView(this, this)
    }


    //region # CYCLE

    override fun onStart() {
        super.onStart()
        this.spotifyManager.onInitialize(this, this)
    }

    override fun onStop() {
        super.onStop()
        this.spotifyManager.onStop()
    }


    override fun onDestroy() {
        super.onDestroy()
        this.spotifyManager.onStop()
    }

    //endregion


    //region # SPOTIFY

    override fun onConnect() {

        this.spotifyManager.player?.let { player ->
            player.onPlay("spotify:artist:5JXhkyafynxCvxtov7C1PV") {
                this@PlayerActivity.playerContainerView.setInfo(it)
            }
        }
    }

    override fun onFailure(error: String) {
        Log.e("ERROR", error)
    }

    //endregion


    override fun onClickNext() {
    }

    override fun onClickPrev() {
    }

    override fun onClickPlay() {
    }

    override fun onChangeSlider(msPosition: Long) {
    }
}