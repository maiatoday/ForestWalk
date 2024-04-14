package net.maiatoday.forestwalk.lsystem
import java.awt.Color

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