package ph.edu.comteq.echoes_of_the_maze

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ph.edu.comteq.echoes_of_the_maze.ui.theme.Echoes_of_the_mazeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Start background music via MusicManager
        MusicManager.start(this)

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
        // Keep music playing across activities
        super.onDestroy()
    }
}