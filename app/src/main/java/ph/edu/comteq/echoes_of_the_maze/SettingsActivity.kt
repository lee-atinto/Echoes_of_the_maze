package ph.edu.comteq.echoes_of_the_maze

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ph.edu.comteq.echoes_of_the_maze.ui.theme.Echoes_of_the_mazeTheme

class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Echoes_of_the_mazeTheme {
                SettingsScreen(
                    onSaveClicked = { finish() },
                    onDeclineClicked = { finish() }
                )
            }
        }
    }
}
