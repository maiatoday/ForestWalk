package net.maiatoday.forestwalk.lsystem

import net.maiatoday.forestwalk.tools.shrubShades
import net.maiatoday.forestwalk.tools.treeShades
import java.awt.Color
import kotlin.random.Random

enum class Ground { Fore, Middle, Back }
data class Seedling(
    val colors: List<Color>,
    val angleChangeDeg: Degree = 20.0,
    val startStroke: Float = 1.0f,
    val strokeChange: Float = 1.0f,
    val length: Int = 20,
    val startX: Int,
    val startY: Int,
    val bluePrint: String
)

fun colorSeedling(isTree: Boolean, ground: Ground): List<Color> =
    if (isTree) {
        when (ground) {
            Ground.Fore -> treeShades.shuffled().take(1)
            Ground.Middle -> listOf(Color.gray)
            Ground.Back -> listOf(Color.lightGray)
        }
    } else {
        shrubShades.shuffled().take(1)
    }

fun xSeedling(isTree: Boolean, ground: Ground, width: Int): Int = Random.nextInt(0, width)

fun ySeedling(isTree: Boolean, ground: Ground, height: Int): Int = if (isTree) {
    when (ground) {
        Ground.Fore -> Random.nextInt(height - 50, height)
        Ground.Middle -> Random.nextInt(height - 100, height - 50)
        Ground.Back -> Random.nextInt(height - 200, height - 150)
    }
} else {
    height
}

fun lengthSeedling(isTree: Boolean, ground: Ground): Int = if (isTree) {
    when (ground) {
        Ground.Fore -> 5
        Ground.Middle -> 4
        Ground.Back -> 3
    }
} else {
    6
}

fun startStrokeSeedling(isTree: Boolean, ground: Ground): Float = if (isTree) {
    when (ground) {
        Ground.Fore -> 16.0f
        Ground.Middle -> 10.0f
        Ground.Back -> 8.0f
    }
} else {
    8.0f
}

fun strokeChangeSeedling(isTree: Boolean, ground: Ground): Float = if (isTree) {
    when (ground) {
        Ground.Fore -> 0.98f
        Ground.Middle -> 0.98f
        Ground.Back -> 0.98f
    }
} else {
    0.9f
}
