
package ph.edu.comteq.echoes_of_the_maze

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun GameOverScreen(
    score: Int,
    isVictory: Boolean,
    onRetryClicked: () -> Unit,
    onHomeClicked: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = if (isVictory) "Victory! Score: $score" else "Game Over")

        Button(onClick = onRetryClicked) {
            Text(text = "Retry")
        }

        Button(onClick = onHomeClicked) {
            Text(text = "Main Menu")
        }
    }
}
