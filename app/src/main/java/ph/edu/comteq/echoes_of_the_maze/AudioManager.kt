package ph.edu.comteq.echoes_of_the_maze

import android.content.Context

object AudioManager {
    private var soundVolume: Float = 1.0f

    fun playBackgroundMusic(context: Context, assetPath: String) {
        MusicManager.start(context)
    }

    fun setMusicVolume(volume: Float) {
        val clampedVolume = volume.coerceIn(0f, 1f)
        val level = (clampedVolume * 15).toInt().coerceIn(1, 15)
        MusicManager.setVolume(level)
    }

    fun setSoundVolume(volume: Float) {
        soundVolume = volume.coerceIn(0f, 1f)
    }

    fun stopMusic() {
        MusicManager.stop()
    }
}