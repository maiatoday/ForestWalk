package net.maiatoday.forestwalk.lsystem

import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics2D
import kotlin.math.cos
import kotlin.math.sin

typealias Degree = Double
typealias Radian = Double
fun Degree.toRadian(): Radian = this / 180 * Math.PI
fun Radian.toDegree(): Degree = this * 180 / Math.PI
data class State(
    val x: Int,
    val y: Int,
    val length: Int,
    val angle: Double = 0.0,
    val colorIndex: Int = 0,
    val strokeWidth: Float = 1.0f,
)

fun Graphics2D.goTortoise(
    penColors: List<Color> = listOf(Color.GREEN),
    angleChangeDeg:Degree = 20.0,
    startWidth:Float = 10.0f,
    widthChange:Float = 0.95f,
    length:Int = 20,
    startX: Int,
    startY: Int,
    bluePrint: String
) {
    val angleChange = angleChangeDeg.toRadian()
    val stack = ArrayDeque<State>()
    var state = State(
        x = startX,
        y = startY,
        length = length,
        angle = -Math.PI / 2, // upright trees
        strokeWidth = startWidth
    )
    stroke = BasicStroke(state.strokeWidth)
    bluePrint.forEach { c ->
        state = when (c) {
            'F', 'f' -> {
                val newX = state.x + (state.length * cos(state.angle)).toInt()
                val newY = state.y + (state.length * sin(state.angle)).toInt()
                if (c == 'F') {
                    color = penColors[state.colorIndex]
                    drawLine(state.x, state.y, newX, newY)
                }
                state.copy(x = newX, y = newY, strokeWidth = state.strokeWidth*widthChange)
            }

            '+' -> state.copy(angle = state.angle - angleChange)
            '-' -> state.copy(angle = state.angle + angleChange)
            '`' -> state.copy(colorIndex = (state.colorIndex+1)%penColors.size)
            '[' -> {
                stack.addLast(state)
                state
            }

            ']' -> stack.removeLast()

            else ->
                state

        }
        stroke = BasicStroke(state.strokeWidth)
    }
}

fun Graphics2D.goSquirrel(
    penColors: List<Color> = listOf(Color.GREEN),
    angleChangeDeg:Degree = 20.0,
    length:Int = 20,
    startX: Int,
    startY: Int,
    bluePrint: String
) {

}
