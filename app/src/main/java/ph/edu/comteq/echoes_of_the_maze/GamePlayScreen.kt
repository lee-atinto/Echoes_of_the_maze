package ph.edu.comteq.echoes_of_the_maze

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.abs

@Composable
fun GameplayScreen(
    levelId: Int,
    onGameFinished: (Int, Boolean) -> Unit
) {
    val context = LocalContext.current
    val mapBitmap = rememberAssetImage("map_level/map_level_1.png")
    val keyBitmap = rememberAssetImage("objects/key_door.png")

    val doorBitmaps = listOf(
        rememberAssetImage("objects/door_animation_open1.png"),
        rememberAssetImage("objects/door_animation_open2.png"),
        rememberAssetImage("objects/door_animation_open3.png"),
        rememberAssetImage("objects/door_animation_open4.png"),
        rememberAssetImage("objects/door_animation_open5.png"),
        rememberAssetImage("objects/door_animation_open6.png")
    )

    val player = remember { Player(initialX = 1, initialY = 4, context = context) }

    var direction by remember { mutableStateOf("down") }
    var isMoving by remember { mutableStateOf(false) }
    var currentFrame by remember { mutableIntStateOf(0) }
    var hasKey by remember { mutableStateOf(false) }

    var doorState by remember { mutableIntStateOf(0) }
    var isOpeningDoor by remember { mutableStateOf(false) }

    val level1Grid = remember {
        arrayOf(
            intArrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
            intArrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
            intArrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
            intArrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
            intArrayOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1),
            intArrayOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1),
            intArrayOf(1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1),
            intArrayOf(1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1),
            intArrayOf(1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1),
            intArrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
            intArrayOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
        )
    }

    var keyPos by remember { mutableStateOf(Pair(13, 4)) }
    var doorPos by remember { mutableStateOf(Pair(7, 9)) }

    val distanceToKey = abs(player.x - keyPos.first) + abs(player.y - keyPos.second)
    val isNearKey = !hasKey && keyPos.first >= 0 && keyPos.second >= 0 && distanceToKey <= 1

    val distanceToDoor = abs(player.x - doorPos.first) + abs(player.y - doorPos.second)
    val isNearDoor = doorState == 0 && doorPos.first >= 0 && doorPos.second >= 0 && distanceToDoor <= 1

    LaunchedEffect(isOpeningDoor) {
        if (isOpeningDoor) {
            for (i in 1..6) {
                doorState = i
                delay(100L)
            }
            doorState = 7
            level1Grid[doorPos.second][doorPos.first] = 0
            onGameFinished(100, true)
        }
    }

    LaunchedEffect(isMoving, direction) {
        if (isMoving) {
            while (isMoving) {
                var dx = 0
                var dy = 0
                when (direction) {
                    "up" -> dy = -1
                    "down" -> dy = 1
                    "left" -> dx = -1
                    "right" -> dx = 1
                }

                player.move(dx, dy, level1Grid)
                currentFrame++
                delay(75L)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.TopStart
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .drawBehind {
                    mapBitmap?.let { bitmap ->
                        drawImage(
                            image = bitmap,
                            dstSize = IntSize(size.width.toInt(), size.height.toInt()),
                            filterQuality = FilterQuality.None
                        )
                    }

                    val tileWidth = size.width / level1Grid[0].size
                    val tileHeight = size.height / level1Grid.size

                    for (row in level1Grid.indices) {
                        for (col in level1Grid[row].indices) {
                            val color = if (level1Grid[row][col] == 1) Color.Red.copy(alpha = 0.3f) else Color.Green.copy(alpha = 0.1f)
                            drawRect(
                                color = color,
                                topLeft = Offset(col * tileWidth, row * tileHeight),
                                size = Size(tileWidth, tileHeight)
                            )
                            drawRect(
                                color = Color.White.copy(alpha = 0.2f),
                                topLeft = Offset(col * tileWidth, row * tileHeight),
                                size = Size(tileWidth, tileHeight),
                                style = Stroke(width = 1f)
                            )
                        }
                    }

                    if (!hasKey && keyPos.first >= 0 && keyPos.second >= 0) {
                        val keyW = tileWidth * 0.5f
                        val keyH = tileHeight * 0.5f
                        val keyX = (keyPos.first * tileWidth) + (tileWidth - keyW) / 2f
                        val keyY = (keyPos.second * tileHeight) + (tileHeight - keyH) / 2f

                        keyBitmap?.let { bitmap ->
                            drawImage(
                                image = bitmap,
                                dstOffset = IntOffset(keyX.toInt(), keyY.toInt()),
                                dstSize = IntSize(keyW.toInt(), keyH.toInt()),
                                filterQuality = FilterQuality.None
                            )
                        }
                    }

                    if (doorState in 1..6) {
                        val frameIndex = doorState - 1
                        val doorW = tileWidth
                        val doorH = tileHeight
                        val doorX = doorPos.first * tileWidth
                        val doorY = doorPos.second * tileHeight

                        doorBitmaps.getOrNull(frameIndex)?.let { bitmap ->
                            drawImage(
                                image = bitmap,
                                dstOffset = IntOffset(doorX.toInt(), doorY.toInt()),
                                dstSize = IntSize(doorW.toInt(), doorH.toInt()),
                                filterQuality = FilterQuality.None
                            )
                        }
                    } else if (doorState == 0 && doorPos.first >= 0 && doorPos.second >= 0) {
                        val doorW = tileWidth
                        val doorH = tileHeight
                        val doorX = doorPos.first * tileWidth
                        val doorY = doorPos.second * tileHeight

                        doorBitmaps.firstOrNull()?.let { bitmap ->
                            drawImage(
                                image = bitmap,
                                dstOffset = IntOffset(doorX.toInt(), doorY.toInt()),
                                dstSize = IntSize(doorW.toInt(), doorH.toInt()),
                                filterQuality = FilterQuality.None
                            )
                        }
                    }

                    player.draw(this, tileWidth, tileHeight, currentFrame)
                }
        )

        if (isNearKey) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 100.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Press Pick to get the key",
                    color = Color.Yellow
                )
            }
        } else if (isNearDoor && doorState == 0) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 100.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (hasKey) "Press Open to unlock the door" else "You need a key to open this door",
                    color = Color.Yellow
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.size(150.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color.Blue)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onPress = {
                                    direction = "up"
                                    isMoving = true
                                    tryAwaitRelease()
                                    isMoving = false
                                    player.stopMoving()
                                }
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text("▲", color = Color.White)
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(Color.Blue)
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onPress = {
                                        direction = "left"
                                        isMoving = true
                                        tryAwaitRelease()
                                        isMoving = false
                                        player.stopMoving()
                                    }
                                )
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("◀", color = Color.White)
                    }

                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(Color.Blue)
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onPress = {
                                        direction = "right"
                                        isMoving = true
                                        tryAwaitRelease()
                                        isMoving = false
                                        player.stopMoving()
                                    }
                                )
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("▶", color = Color.White)
                    }
                }

                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color.Blue)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onPress = {
                                    direction = "down"
                                    isMoving = true
                                    tryAwaitRelease()
                                    isMoving = false
                                    player.stopMoving()
                                }
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text("▼", color = Color.White)
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                if (isNearKey) {
                    Button(
                        onClick = {
                            hasKey = true
                            keyPos = Pair(-1, -1)
                        }
                    ) {
                        Text(text = "Pick")
                    }
                }

                if (isNearDoor && doorState == 0) {
                    Button(
                        onClick = {
                            if (hasKey) {
                                isOpeningDoor = true
                            }
                        }
                    ) {
                        Text(text = "Open")
                    }
                }
            }
        }
    }
}
