package ph.edu.comteq.echoes_of_the_maze

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun MainMenuScreen(
    onPlayClicked: () -> Unit,
    onSettingsClicked: () -> Unit
) {
    val context = LocalContext.current

    val backgroundBitmap = rememberAssetImage("background/main_menu_screen_background.png")
    val startBitmap = rememberAssetImage("menu_icon/start.png")
    val settingsBitmap = rememberAssetImage("menu_icon/settings.png")
    val quitBitmap = rememberAssetImage("menu_icon/quit.png")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        backgroundBitmap?.let { bitmap ->
            Image(
                bitmap = bitmap,
                contentDescription = "Background",
                modifier = Modifier
                    .fillMaxSize()
                    .blur(10.dp),
                contentScale = ContentScale.Crop,
                filterQuality = FilterQuality.None
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Echoes of the Maze", color = Color.White)
            Spacer(modifier = Modifier.height(24.dp))

            startBitmap?.let { bitmap ->
                Image(
                    bitmap = bitmap,
                    contentDescription = "Start Button",
                    modifier = Modifier
                        .width(168.dp)
                        .height(33.dp)
                        .clickable { onPlayClicked() },
                    contentScale = ContentScale.Fit,
                    filterQuality = FilterQuality.None
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            settingsBitmap?.let { bitmap ->
                Image(
                    bitmap = bitmap,
                    contentDescription = "Settings Button",
                    modifier = Modifier
                        .width(168.dp)
                        .height(33.dp)
                        .clickable { onSettingsClicked() },
                    contentScale = ContentScale.Fit,
                    filterQuality = FilterQuality.None
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            quitBitmap?.let { bitmap ->
                Image(
                    bitmap = bitmap,
                    contentDescription = "Quit Button",
                    modifier = Modifier
                        .width(168.dp)
                        .height(33.dp)
                        .clickable {
                            (context as? ComponentActivity)?.finish()
                        },
                    contentScale = ContentScale.Fit,
                    filterQuality = FilterQuality.None
                )
            }
        }
    }
}
