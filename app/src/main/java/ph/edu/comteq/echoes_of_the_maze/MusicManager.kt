package ph.edu.comteq.echoes_of_the_maze

import android.content.Context
import android.media.MediaPlayer

object MusicManager {
    private var mediaPlayer: MediaPlayer? = null

    fun start(context: Context) {
        if (mediaPlayer != null) return

        val appContext = context.applicationContext
        val savedLevel = appContext
            .getSharedPreferences("game_settings", Context.MODE_PRIVATE)
            .getInt("music_level", 15)

        try {
            val player = MediaPlayer()
            val afd = appContext.assets.openFd(
                "music_and_sound/Mr.Cat - Bit Piano Music.mp3"
            )

            player.setDataSource(
                afd.fileDescriptor,
                afd.startOffset,
                afd.length
            )
            afd.close()

            player.isLooping = true
            player.setVolume(savedLevel / 15f, savedLevel / 15f)
            player.prepare()
            player.start()

            mediaPlayer = player
        } catch (e: Exception) {
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    fun setVolume(level: Int) {
        val volume = level.coerceIn(1, 15) / 15f
        mediaPlayer?.setVolume(volume, volume)
    }

    fun stop() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
