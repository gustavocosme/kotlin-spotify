package com.gustavo.playerspotify.manager.spotify

import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.protocol.types.Track

interface SpotifyPlayerProtocol {
    fun onPlay(id: String, callback: (Track) -> Unit)
    fun onResume()
    fun onPause()
    fun onNext()
    fun onPrev()
    fun onSeek(positionMS: Long)
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