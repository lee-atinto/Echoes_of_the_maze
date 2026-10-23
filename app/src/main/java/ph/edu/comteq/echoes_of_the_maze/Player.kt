package ph.edu.comteq.echoes_of_the_maze

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize

class Player(
    initialX: Int,
    initialY: Int,
    private val context: Context
) {
    var x by mutableIntStateOf(initialX)
    var y by mutableIntStateOf(initialY)
    var currentDirection by mutableStateOf("down")
    var isMoving by mutableStateOf(false)

    private val walkSprites = mapOf(
        "down" to loadAssetFrames("player_level_1/walking", "walk_down", 6),
        "up" to loadAssetFrames("player_level_1/walking", "walk_up", 6),
        "left" to loadAssetFrames("player_level_1/walking", "walk_left", 6),
        "right" to loadAssetFrames("player_level_1/walking", "walk_right", 6)
    )

    private val idleSprites = mapOf(
        "down" to loadAssetFrames("player_level_1/idle", "idle_down", 12),
        "up" to loadAssetFrames("player_level_1/idle", "idle_up", 4),
        "left" to loadAssetFrames("player_level_1/idle", "idle_left", 12),
        "right" to loadAssetFrames("player_level_1/idle", "idle_right", 12)
    )

    private fun loadAssetFrames(folderPath: String, prefix: String, count: Int): List<ImageBitmap> {
        return (1..count).map { index ->
            val filePath = "$folderPath/${prefix}_$index.png"
            try {
                val inputStream = context.assets.open(filePath)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                inputStream.close()
                bitmap.asImageBitmap()
            } catch (e: Exception) {
                val fallbackBitmap = Bitmap.createBitmap(32, 32, Bitmap.Config.ARGB_8888)
                fallbackBitmap.asImageBitmap()
            }
        }
    }

    fun move(dx: Int, dy: Int, grid: Array<IntArray>): Boolean {
        if (dx > 0) currentDirection = "right"
        else if (dx < 0) currentDirection = "left"
        else if (dy > 0) currentDirection = "down"
        else if (dy < 0) currentDirection = "up"

        if (dx == 0 && dy == 0) {
            isMoving = false
            return false
        }

        val newX = x + dx
        val newY = y + dy

        if (newY in grid.indices && newX in grid[0].indices) {
            val cell = grid[newY][newX]
            if (cell == 0) {
                x = newX
                y = newY
                isMoving = true
                return false
            } else if (cell == 2) {
                isMoving = true
                return true
            }
        }

        isMoving = false
        return false
    }

    fun stopMoving() {
        isMoving = false
    }

    fun draw(drawScope: DrawScope, tileWidth: Float, tileHeight: Float, currentFrame: Int) {
        val spriteMap = if (isMoving) walkSprites else idleSprites
        val frameList = spriteMap[currentDirection] ?: spriteMap["down"]!!

        val activeBitmap = frameList[currentFrame % frameList.size]

        val dstOffset = IntOffset((x * tileWidth).toInt(), (y * tileHeight).toInt())
        val dstSize = IntSize(tileWidth.toInt(), tileHeight.toInt())

        drawScope.drawImage(
            image = activeBitmap,
            dstOffset = dstOffset,
            dstSize = dstSize,
            filterQuality = FilterQuality.None
        )
    }
}