package com.gustavo.playerspotify.manager.spotify

import android.app.Activity
import com.gustavo.playerspotify.extentions.isPackageInstalled
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote

interface SpotifyManagerDelegate {
    fun onConnect()
    fun onFailure(error: String)
}

interface SpotifyManagerProtocol {
    fun onInitialize(activity: Activity, delegate: SpotifyManagerDelegate)
    fun onStop()
    var  player: SpotifyPlayerProtocol?
}

class SpotifyManager private constructor(): Connector.ConnectionListener, SpotifyManagerProtocol {

    //region # INSTANCE

    companion object {
        val INSTANCE: SpotifyManager by lazy { SpotifyManager() }
    }

    //endregion


    //region # PRIVATE VARs

    private lateinit var remote: SpotifyAppRemote
    private lateinit var delegate: SpotifyManagerDelegate
    override var player: SpotifyPlayerProtocol? = null

    private enum class ERROR(error: String) {
        NO_INSTALL_APP("Please install spotify app."),
        UNKNOWN_AUTHENTICATION("Initial connection error."),
        ON_FAILURE("Unknown error.")
    }

    //endregion


    //region # CONNECT

    override fun onInitialize(activity: Activity, delegate: SpotifyManagerDelegate) {
        fun onInit() {
            this.delegate = delegate
            val connection = ConnectionParams.Builder(SpotifyConfig.ID)
            connection.setRedirectUri(SpotifyConfig.REDIRECT)
            connection.showAuthView(true)
            SpotifyAppRemote.connect(activity, connection.build(), this)
        }

        if (activity.isPackageInstalled("com.spotify.music")) {
            onInit()
        } else {
            delegate.onFailure(ERROR.NO_INSTALL_APP.name)
        }
    }

    //endregion


    //region # SPOTIFY

    override fun onConnected(spotifyAppRemote: SpotifyAppRemote?) {
        spotifyAppRemote?.let {
            this.remote = it
            this.player = SpotifyPlayer(remote)
            this.delegate.onConnect()
        }?: run {
            this.delegate.onFailure(ERROR.UNKNOWN_AUTHENTICATION.name)
        }
    }

    override fun onFailure(error: Throwable?) {
        error?.localizedMessage?.let {
            this.delegate.onFailure(it)
        } ?: run {
            this.delegate.onFailure(ERROR.ON_FAILURE.name)
        }
    }

    //endregion

    //region # CYCLE

    override fun onStop() {
        SpotifyAppRemote.disconnect(this.remote)
    }

    //endregion
}