package ph.edu.comteq.echoes_of_the_maze

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(
    onSaveClicked: () -> Unit,
    onDeclineClicked: () -> Unit
) {
    val context = LocalContext.current
    val sharedPrefs = remember {
        context.getSharedPreferences("game_settings", Context.MODE_PRIVATE)
    }

    val backgroundBitmap = rememberAssetImage("background/main_menu_screen_background.png")
    val boxSettingsBitmap = rememberAssetImage("menu_icon/box_settings.png")
    val textSettingsBitmap = rememberAssetImage("menu_icon/text_settings.png")
    val soundBitmap = rememberAssetImage("menu_icon/sound.png")
    val musicBitmap = rememberAssetImage("menu_icon/music.png")
    val volumeUpBitmap = rememberAssetImage("menu_icon/volume_up.png")
    val volumeDownBitmap = rememberAssetImage("menu_icon/volume_down.png")
    val saveBitmap = rememberAssetImage("menu_icon/save.png")
    val declineBitmap = rememberAssetImage("menu_icon/decline.png")

    var soundLevel by remember {
        mutableIntStateOf(sharedPrefs.getInt("sound_level", 15))
    }
    var musicLevel by remember {
        mutableIntStateOf(sharedPrefs.getInt("music_level", 15))
    }

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

        Box(
            modifier = Modifier
                .width(300.dp)
                .height(360.dp)
        ) {
            boxSettingsBitmap?.let { bitmap ->
                Image(
                    bitmap = bitmap,
                    contentDescription = "Settings Box",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds,
                    filterQuality = FilterQuality.None
                )
            }

            textSettingsBitmap?.let { bitmap ->
                Image(
                    bitmap = bitmap,
                    contentDescription = "Settings Title",
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 10.dp)
                        .width(96.dp)
                        .height(22.dp),
                    contentScale = ContentScale.Fit,
                    filterQuality = FilterQuality.None
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .width(260.dp)
                        .pointerInput(Unit) {
                            detectTapGestures { offset ->
                                val clickedIndex = ((offset.x / size.width) * 15).toInt().coerceIn(0, 14)
                                soundLevel = clickedIndex + 1
                            }
                        }
                        .pointerInput(Unit) {
                            detectDragGestures { change, _ ->
                                val draggedIndex = ((change.position.x / size.width) * 15).toInt().coerceIn(0, 14)
                                soundLevel = draggedIndex + 1
                            }
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    soundBitmap?.let { bitmap ->
                        Image(
                            bitmap = bitmap,
                            contentDescription = "Sound",
                            modifier = Modifier.size(30.dp),
                            contentScale = ContentScale.Fit,
                            filterQuality = FilterQuality.None
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        for (i in 1..15) {
                            val active = i <= soundLevel
                            val barBitmap = if (active) volumeUpBitmap else volumeDownBitmap
                            barBitmap?.let { bitmap ->
                                Image(
                                    bitmap = bitmap,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(width = 10.dp, height = if (active) 18.dp else 10.dp),
                                    contentScale = ContentScale.FillBounds,
                                    filterQuality = FilterQuality.None
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .width(260.dp)
                        .pointerInput(Unit) {
                            detectTapGestures { offset ->
                                val clickedIndex = ((offset.x / size.width) * 15).toInt().coerceIn(0, 14)
                                musicLevel = clickedIndex + 1
                                sharedPrefs.edit().putInt("music_level", musicLevel).apply()
                            }
                        }
                        .pointerInput(Unit) {
                            detectDragGestures { change, _ ->
                                val draggedIndex = ((change.position.x / size.width) * 15).toInt().coerceIn(0, 14)
                                musicLevel = draggedIndex + 1
                                sharedPrefs.edit().putInt("music_level", musicLevel).apply()
                            }
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    musicBitmap?.let { bitmap ->
                        Image(
                            bitmap = bitmap,
                            contentDescription = "Music",
                            modifier = Modifier.size(30.dp),
                            contentScale = ContentScale.Fit,
                            filterQuality = FilterQuality.None
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        for (i in 1..15) {
                            val active = i <= musicLevel
                            val barBitmap = if (active) volumeUpBitmap else volumeDownBitmap
                            barBitmap?.let { bitmap ->
                                Image(
                                    bitmap = bitmap,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(width = 10.dp, height = if (active) 18.dp else 10.dp),
                                    contentScale = ContentScale.FillBounds,
                                    filterQuality = FilterQuality.None
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    saveBitmap?.let { bitmap ->
                        Image(
                            bitmap = bitmap,
                            contentDescription = "Save",
                            modifier = Modifier
                                .width(130.dp)
                                .height(30.dp)
                                .clickable {
                                    sharedPrefs.edit().apply {
                                        putInt("sound_level", soundLevel)
                                        putInt("music_level", musicLevel)
                                        apply()
                                    }
                                    onSaveClicked()
                                },
                            contentScale = ContentScale.Fit,
                            filterQuality = FilterQuality.None
                        )
                    }
                    declineBitmap?.let { bitmap ->
                        Image(
                            bitmap = bitmap,
                            contentDescription = "Decline",
                            modifier = Modifier
                                .width(130.dp)
                                .height(30.dp)
                                .clickable {
                                    onDeclineClicked()
                                },
                            contentScale = ContentScale.Fit,
                            filterQuality = FilterQuality.None
                        )
                    }
                }
            }
        }
    }
}
