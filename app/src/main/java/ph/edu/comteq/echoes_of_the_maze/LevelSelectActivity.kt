package ph.edu.comteq.echoes_of_the_maze

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ph.edu.comteq.echoes_of_the_maze.ui.theme.Echoes_of_the_mazeTheme

class LevelSelectActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Echoes_of_the_mazeTheme {
                LevelSelectScreen(
                    onLevelSelected = { levelId ->
                        val intent = Intent(this, GameplayActivity::class.java)
                        intent.putExtra(GameplayActivity.EXTRA_LEVEL_ID, levelId)
                        startActivity(intent)
                    },
                    onQuitClicked = { finish() }
                )
            }
        }
    }
}
