package com.gustavo.playerspotify.extentions

import com.spotify.protocol.types.PlayerState

fun PlayerState.currentTimeTrackString(): String {
    return String().convertMSToSeconds(this.playbackPosition)
}

fun PlayerState.countTimeTrackString(): String {
    return try {
        "-"+String().convertMSToSeconds(this.track.duration-this.playbackPosition)
    } catch (e: java.lang.Exception) {
        ""
    }
}