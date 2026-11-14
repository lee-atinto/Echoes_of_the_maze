package ph.edu.comteq.echoes_of_the_maze

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ph.edu.comteq.echoes_of_the_maze.ui.theme.Echoes_of_the_mazeTheme

class GameplayActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val levelId = intent.getIntExtra(EXTRA_LEVEL_ID, 1)

        setContent {
            Echoes_of_the_mazeTheme {
                GameplayScreen(
                    levelId = levelId,
                    onGameFinished = { _, _ -> },
                    onRestartClicked = {
                        val intent = Intent(this, GameplayActivity::class.java).apply {
                            putExtra(EXTRA_LEVEL_ID, levelId)
                        }
                        startActivity(intent)
                        finish()
                    },
                    onNextLevelClicked = {
                        val nextLevel = levelId + 1
                        val intent = Intent(this, GameplayActivity::class.java).apply {
                            putExtra(EXTRA_LEVEL_ID, nextLevel)
                        }
                        startActivity(intent)
                        finish()
                    },
                    onMenuClicked = {
                        val intent = Intent(this, MainActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        }
                        startActivity(intent)
                        finish()
                    }
                )
            }
        }
    }

    companion object {
        const val EXTRA_LEVEL_ID = "extra_level_id"
    }
}