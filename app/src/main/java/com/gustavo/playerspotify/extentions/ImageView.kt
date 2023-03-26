package com.gustavo.playerspotify.extentions

import android.widget.ImageView
import com.gustavo.playerspotify.manager.spotify.SpotifyManager
import com.spotify.protocol.types.ImageUri

fun ImageView.loadCoverSpotify(uri: ImageUri) {
    SpotifyManager.INSTANCE.player?.let { player ->
        player.getImage(uri) {
            this.setImageBitmap(it)
        }
    }
}