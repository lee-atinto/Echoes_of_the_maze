package ph.edu.comteq.echoes_of_the_maze

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ph.edu.comteq.echoes_of_the_maze.ui.theme.Echoes_of_the_mazeTheme

class MainActivity : ComponentActivity() {
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val sharedPrefs = getSharedPreferences("game_settings", MODE_PRIVATE)
        val musicLevel = sharedPrefs.getInt("music_level", 15)

        try {
            mediaPlayer = MediaPlayer().apply {
                val afd = assets.openFd("music_and_sound/Mr.Cat - Bit Piano Music.mp3")
                setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                afd.close()
                isLooping = true
                setVolume(musicLevel / 15f, musicLevel / 15f)
                prepare()
                start()
            }
        } catch (_: Exception) {
            mediaPlayer = null
        }

        setContent {
            Echoes_of_the_mazeTheme {
                MainMenuScreen(
                    onPlayClicked = {
                        startActivity(Intent(this, LevelSelectActivity::class.java))
                    },
                    onSettingsClicked = {
                        startActivity(Intent(this, SettingsActivity::class.java))
                    }
                )
            }
        }
    }

    override fun onDestroy() {
        mediaPlayer?.let {
            if (it.isPlaying) it.stop()
            it.release()
        }
        mediaPlayer = null
        super.onDestroy()
    }
}
