package ph.edu.comteq.echoes_of_the_maze

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

@Composable
fun LevelSelectScreen(
    onLevelSelected: (Int) -> Unit,
    onQuitClicked: () -> Unit
) {
    val levelBoxBitmap = rememberAssetImage("menu_icon/level_box.png")
    val levelsTitleBitmap = rememberAssetImage("menu_icon/text_levels.png")
    val levelShadowBitmap = rememberAssetImage("menu_icon/level_shadow.png")
    val levelCloseBitmap = rememberAssetImage("menu_icon/level_close_animation.png")
    val levelOpenBitmap = rememberAssetImage("menu_icon/level_open_animation.png")
    val levelClosedBitmap = rememberAssetImage("menu_icon/level_closed.png")
    val lockedBitmap = rememberAssetImage("menu_icon/locked.png")
    val starUnlitBitmap = rememberAssetImage("menu_icon/unstar.png")
    val nextBackBitmap = rememberAssetImage("menu_icon/next_back.png")
    val quitBitmap = rememberAssetImage("menu_icon/quit.png")

    val openedLevels = remember { mutableStateMapOf<Int, Boolean>() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .width(300.dp)
                .height(360.dp)
        ) {
            levelBoxBitmap?.let { bitmap ->
                Image(
                    bitmap = bitmap,
                    contentDescription = "Level Box",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds,
                    filterQuality = FilterQuality.None
                )
            }

            levelsTitleBitmap?.let { bitmap ->
                Image(
                    bitmap = bitmap,
                    contentDescription = "Levels Title",
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
                    .padding(top = 45.dp, bottom = 55.dp, start = 30.dp, end = 30.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                for (row in 0..2) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        for (col in 0..2) {
                            val levelId = row * 3 + col + 1
                            val isUnlocked = levelId == 1
                            val isOpen = openedLevels[levelId] == true

                            Box(
                                modifier = Modifier
                                    .size(52.dp)
                                    .clickable(enabled = isUnlocked) {
                                        if (isUnlocked) {
                                            if (!isOpen) {
                                                openedLevels[levelId] = true
                                            } else {
                                                onLevelSelected(levelId)
                                            }
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Box(
                                        modifier = Modifier.size(36.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        levelShadowBitmap?.let { shadow ->
                                            Image(
                                                bitmap = shadow,
                                                contentDescription = null,
                                                modifier = Modifier.size(36.dp),
                                                contentScale = ContentScale.Fit,
                                                filterQuality = FilterQuality.None
                                            )
                                        }

                                        val doorBitmap = when {
                                            !isUnlocked -> levelClosedBitmap
                                            isOpen -> levelOpenBitmap
                                            else -> levelCloseBitmap
                                        }

                                        doorBitmap?.let { door ->
                                            Image(
                                                bitmap = door,
                                                contentDescription = "Level $levelId",
                                                modifier = Modifier
                                                    .size(26.dp)
                                                    .offset(y = (-2).dp),
                                                contentScale = ContentScale.Fit,
                                                filterQuality = FilterQuality.None
                                            )
                                        }

                                        if (!isUnlocked) {
                                            lockedBitmap?.let { lock ->
                                                Image(
                                                    bitmap = lock,
                                                    contentDescription = "Locked",
                                                    modifier = Modifier
                                                        .size(12.dp)
                                                        .align(Alignment.BottomCenter)
                                                        .offset(y = 2.dp),
                                                    contentScale = ContentScale.Fit,
                                                    filterQuality = FilterQuality.None
                                                )
                                            }
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(2.dp))

                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        for (i in 0..2) {
                                            starUnlitBitmap?.let { star ->
                                                Image(
                                                    bitmap = star,
                                                    contentDescription = null,
                                                    modifier = Modifier.size(8.dp),
                                                    contentScale = ContentScale.Fit,
                                                    filterQuality = FilterQuality.None
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 12.dp, start = 20.dp, end = 20.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.size(40.dp))

                quitBitmap?.let { bitmap ->
                    Image(
                        bitmap = bitmap,
                        contentDescription = "Quit",
                        modifier = Modifier
                            .width(90.dp)
                            .height(26.dp)
                            .clickable { onQuitClicked() },
                        contentScale = ContentScale.Fit,
                        filterQuality = FilterQuality.None
                    )
                }

                nextBackBitmap?.let { bitmap ->
                    Image(
                        bitmap = bitmap,
                        contentDescription = "Next",
                        modifier = Modifier
                            .size(32.dp)
                            .clickable { },
                        contentScale = ContentScale.Fit,
                        filterQuality = FilterQuality.None
                    )
                }
            }
        }
    }
}
