package com.gustavo.playerspotify.activitys.player

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.gustavo.playerspotify.R
import com.gustavo.playerspotify.manager.spotify.SpotifyManager
import com.gustavo.playerspotify.manager.spotify.SpotifyManagerDelegate
import com.gustavo.playerspotify.manager.spotify.SpotifyManagerProtocol

class PlayerActivity : AppCompatActivity(), SpotifyManagerDelegate {

    private val spotifyManager: SpotifyManagerProtocol = SpotifyManager.INSTANCE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_player)

        PlayerContainerView(this)
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
        this.spotifyManager.player?.onPlay("spotify:playlist:37i9dQZF1DX2sUQwD7tbmL") {
            Log.e("NAME", it.name)
        }
    }

    override fun onFailure(error: String) {
        Log.e("ERROR", error)
    }

    //endregion
}