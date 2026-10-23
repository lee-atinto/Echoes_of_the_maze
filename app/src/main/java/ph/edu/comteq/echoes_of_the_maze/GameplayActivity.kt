package ph.edu.comteq.echoes_of_the_maze

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ph.edu.comteq.echoes_of_the_maze.ui.theme.Echoes_of_the_mazeTheme

class GameplayActivity : ComponentActivity() {
    companion object {
        const val EXTRA_LEVEL_ID = "level_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val levelId = intent.getIntExtra(EXTRA_LEVEL_ID, 1)

        setContent {
            Echoes_of_the_mazeTheme {
                GameplayScreen(
                    levelId = levelId,
                    onGameFinished = { score, isVictory ->
                        val resultIntent = Intent(this, GameOverActivity::class.java)
                        resultIntent.putExtra(GameOverActivity.EXTRA_SCORE, score)
                        resultIntent.putExtra(GameOverActivity.EXTRA_VICTORY, isVictory)
                        startActivity(resultIntent)
                        finish()
                    }
                )
            }
        }
    }
}
