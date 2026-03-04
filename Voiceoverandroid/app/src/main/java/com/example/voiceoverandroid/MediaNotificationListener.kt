package com.example.voiceoverandroid

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.media.MediaMetadata
import android.media.session.MediaController
import android.media.session.MediaSessionManager
import android.media.session.PlaybackState
import android.service.notification.NotificationListenerService
import android.util.Log

class MediaNotificationListener : NotificationListenerService() {

    // Live state cache (accessible from other components)
    companion object {
        var currentTitle: String? = null
        var currentArtist: String? = null
        var currentAlbum: String? = null
    }

    private var currentController: MediaController? = null
    private var controllerCallback: MediaController.Callback? = null

    private var lastTitle: String? = null
    private var lastArtist: String? = null

    private lateinit var sessionManager: MediaSessionManager

    override fun onListenerConnected() {
        super.onListenerConnected()

        Log.d("MusicDeets", "Notification Listener Connected")

        // Ensure foreground service is running
        val serviceIntent = Intent(this, MediaService::class.java)
        startForegroundService(serviceIntent)

        sessionManager =
            getSystemService(Context.MEDIA_SESSION_SERVICE) as MediaSessionManager

        registerActiveSessions()

        sessionManager.addOnActiveSessionsChangedListener(
            { controllers ->
                controllers?.forEach { observeController(it) }
            },
            ComponentName(this, MediaNotificationListener::class.java)
        )
    }

    private fun registerActiveSessions() {
        val sessions = sessionManager.getActiveSessions(
            ComponentName(this, MediaNotificationListener::class.java)
        )

        sessions.forEach { observeController(it) }
    }

    private fun observeController(controller: MediaController) {

        // Ignore if same session
        if (currentController?.sessionToken == controller.sessionToken) return

        // Clean up old callback
        controllerCallback?.let {
            currentController?.unregisterCallback(it)
        }

        currentController = controller

        controllerCallback = object : MediaController.Callback() {

            override fun onMetadataChanged(metadata: MediaMetadata?) {

                val title = metadata?.description?.title?.toString()
                val artist = metadata?.description?.subtitle?.toString()
                val album = metadata?.description?.description?.toString()

                // Prevent duplicate spam
                if (title == lastTitle && artist == lastArtist) return

                lastTitle = title
                lastArtist = artist

                // Update global cache
                currentTitle = title
                currentArtist = artist
                currentAlbum = album

                Log.d("MusicDeets", "Now playing: $title - $artist")
            }

            override fun onPlaybackStateChanged(state: PlaybackState?) {
                Log.d("MusicDeets", "Playback state changed: ${state?.state}")
            }
        }

        controller.registerCallback(controllerCallback!!)
    }

    override fun onListenerDisconnected() {
        Log.d("MusicDeets", "Listener disconnected")
        super.onListenerDisconnected()
    }

    override fun onDestroy() {
        Log.d("MusicDeets", "Listener destroyed")
        super.onDestroy()
    }
}