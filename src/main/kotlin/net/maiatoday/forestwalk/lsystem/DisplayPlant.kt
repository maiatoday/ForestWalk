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
    if (isTree) treeShades.shuffled().take(1)
    else shrubShades.shuffled().take(1)

fun xSeedling(isTree: Boolean, ground: Ground, width: Int): Int = Random.nextInt(0, width)
fun ySeedling(isTree: Boolean, ground: Ground, height: Int): Int = Random.nextInt(height - 150, height)
fun lengthSeedling(isTree: Boolean, ground: Ground): Int = if (isTree) 5 else 6
fun startStrokeSeedling(isTree: Boolean, ground: Ground): Float = if (isTree) 15.0f else 8.0f
fun strokeChangeSeedling(isTree: Boolean, ground: Ground): Float = if (isTree) 0.98f else 0.9f