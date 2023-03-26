package com.gustavo.playerspotify.manager.spotify

import android.graphics.Bitmap
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.protocol.types.ImageUri
import com.spotify.protocol.types.Track

interface SpotifyPlayerProtocol {
    fun onPlay(id: String, callback: (Track) -> Unit)
    fun onResume()
    fun onPause()
    fun onNext()
    fun onPrev()
    fun onSeek(positionMS: Long)
    fun getImage(uri: ImageUri, callback: (Bitmap) -> Unit)
}

class SpotifyPlayer(var remote: SpotifyAppRemote): SpotifyPlayerProtocol {

    private var currentTrack: Track? = null

    //region # Play

    override fun onPlay(id: String, callback: (Track) -> Unit) {
        this.remote.playerApi.play(id)
        this.remote.playerApi.subscribeToPlayerState().setEventCallback {
            callback(it.track)
            this@SpotifyPlayer.currentTrack = it.track
        }
    }

    //endregion


    //region # Play

    override fun getImage(uri: ImageUri, callback: (Bitmap) -> Unit) {
        this.remote.imagesApi.getImage(uri).setResultCallback {
            callback(it)
        }
    }

    //endregion

    //region # Controls

    override fun onPause() {
        this.currentTrack?.let {
            this.remote.playerApi.pause()
        }
    }

    override fun onResume() {
        this.currentTrack?.let {
            this.remote.playerApi.resume()
        }
    }

    override fun onNext() {
        this.remote.playerApi.skipNext()
    }

    override fun onPrev() {
        this.remote.playerApi.skipPrevious()
    }

    override fun onSeek(positionMS: Long) {
        this.remote.playerApi.seekTo(positionMS)
    }

    //endregion
}