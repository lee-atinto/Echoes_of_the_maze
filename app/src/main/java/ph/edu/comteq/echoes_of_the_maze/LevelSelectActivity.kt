package ph.edu.comteq.echoes_of_the_maze

import android.content.Context
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

        val sharedPreferences = getSharedPreferences("game_progress", Context.MODE_PRIVATE)
        val levelCompleted = intent.getIntExtra("COMPLETED_LEVEL", -1)
        if (levelCompleted != -1) {
            val nextLevel = levelCompleted + 1
            sharedPreferences.edit().putBoolean("level_${nextLevel}_unlocked", true).apply()
        }

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