package ph.edu.comteq.echoes_of_the_maze

object GameLevels {
    private val levels = mapOf(
        1 to arrayOf(
            intArrayOf(1, 1, 1, 1, 1, 1, 1),
            intArrayOf(1, 0, 0, 0, 1, 0, 1),
            intArrayOf(1, 0, 1, 0, 1, 0, 1),
            intArrayOf(1, 0, 1, 0, 0, 0, 1),
            intArrayOf(1, 1, 1, 1, 1, 2, 1)
        ),
        2 to arrayOf(
            intArrayOf(1, 1, 1, 1, 1, 1, 1, 1),
            intArrayOf(1, 0, 1, 0, 0, 0, 0, 1),
            intArrayOf(1, 0, 1, 1, 1, 1, 0, 1),
            intArrayOf(1, 0, 0, 0, 0, 1, 0, 1),
            intArrayOf(1, 1, 1, 1, 1, 1, 2, 1)
        )
    )

    fun getLevelGrid(levelId: Int): Array<IntArray> {
        return levels[levelId] ?: levels[1]!!
    }
}
