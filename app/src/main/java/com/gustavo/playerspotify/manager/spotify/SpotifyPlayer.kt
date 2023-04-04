package com.gustavo.playerspotify.manager.spotify

import android.graphics.Bitmap
import android.util.Log
import com.gustavo.playerspotify.extentions.countTimeTrackString
import com.gustavo.playerspotify.extentions.currentTimeTrackString
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.protocol.types.ImageUri
import com.spotify.protocol.types.PlayerState
import com.spotify.protocol.types.Track
import java.util.Timer
import java.util.TimerTask
import kotlin.concurrent.timerTask

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
    private var playerApi = remote.playerApi
    private var isRun = false
    private var timer: Timer = Timer()
    private lateinit var timerTask: TimerTask

    //region # Play

    override fun onPlay(id: String, callback: (Track) -> Unit) {
        this.playerApi.subscribeToPlayerState().setEventCallback { playerState ->
            this.playerApi.play(id)
            callback(playerState.track)
            this@SpotifyPlayer.currentTrack = playerState.track
            this.isRun = true
            this.onRender()
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
        this.remote.playerApi.pause().setResultCallback {
            this.timerTask.cancel()
            this.isRun = false
        }
    }

    override fun onResume() {
        this.playerApi.resume().setResultCallback {
            this.isRun = true
            this.onRender()
            this.timerTask.run()
        }
    }

    override fun onNext() {
        this.isRun = true
        this.playerApi.skipNext()
    }

    override fun onPrev() {
        this.isRun = true
        this.playerApi.skipPrevious()
    }

    override fun onSeek(positionMS: Long) {
        this.isRun = false
        this.playerApi.seekTo(positionMS)
    }

    //endregion


    //region # Render time

    private fun onRender() {
        fun onRenderTime(playerStage: PlayerState) {
            this.timerTask = timerTask {
                Log.e("TIME: ", "${playerStage.currentTimeTrackString()} - ${playerStage.countTimeTrackString()}")
                this@SpotifyPlayer.onRender()
            }
            this.timer.schedule(this.timerTask, 1000)
        }

        this.playerApi.playerState.setResultCallback {
            try {
                onRenderTime(it)
            } catch (e: java.lang.Exception) {}
        }
    }

    //endregion
}