package ph.edu.comteq.echoes_of_the_maze

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ph.edu.comteq.echoes_of_the_maze.ui.theme.Echoes_of_the_mazeTheme

class GameOverActivity : ComponentActivity() {
    companion object {
        const val EXTRA_SCORE = "score"
        const val EXTRA_VICTORY = "is_victory"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val score = intent.getIntExtra(EXTRA_SCORE, 0)
        val isVictory = intent.getBooleanExtra(EXTRA_VICTORY, false)

        setContent {
            Echoes_of_the_mazeTheme {
                GameOverScreen(
                    score = score,
                    isVictory = isVictory,
                    onRetryClicked = {
                        startActivity(Intent(this, LevelSelectActivity::class.java))
                        finish()
                    },
                    onHomeClicked = {
                        val homeIntent = Intent(this, MainActivity::class.java)
                        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                        startActivity(homeIntent)
                        finish()
                    }
                )
            }
        }
    }
}
