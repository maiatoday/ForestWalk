package net.maiatoday.forestwalk.lsystem

import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics2D
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

//region tortoise commands
// anything else is ignored
// `    use the next pen colour
// ]    take the most recent state from the stack and use it
// [    remember the state (position, angle and more) on the stack
// -    rotate by the angle anti-clockwise
// +    rotate by the angle clockwise
// f    move forward
// F    pen down move forward
//endregion

//region the tortoise lives here
data class State(
    val x: Int,
    val y: Int,
    val length: Int,
    val angle: Double = 0.0,
    val colorIndex: Int = 0,
    val strokeWidth: Float = 1.0f,
)

fun Graphics2D.goTortoise(
    penColors: List<Color> = listOf(Color.BLACK),
    angleChangeDeg: Degree = 20.0,
    length: Int = 20,
    startX: Int,
    startY: Int,
    bluePrint: String
) {
    val stack = ArrayDeque<State>()
    //region start state
    val angleChange = angleChangeDeg.toRadian()
    var state = State(
        x = startX,
        y = startY,
        length = length,
        angle = -Math.PI / 2, // upright trees
    )
    //endregion
    bluePrint.forEach { c ->
        state = when (c) {
            'F', 'f' -> {
                val newX = state.x + (state.length * cos(state.angle)).toInt()
                val newY = state.y + (state.length * sin(state.angle)).toInt()
                // The side effect to draw
                if (c == 'F') {
                    color = penColors[state.colorIndex]
                    drawLine(state.x, state.y, newX, newY)
                }
                state.copy(x = newX, y = newY)
            }

            '+' -> state.copy(angle = state.angle - angleChange)
            '-' -> state.copy(angle = state.angle + angleChange)
            '`' -> state.copy(colorIndex = (state.colorIndex + 1) % penColors.size)
            '[' -> {
                stack.addLast(state)
                state
            }

            ']' -> stack.removeLast()

            else ->
                state

        }
    }
}

//endregion


//region the squirrels live here
fun Graphics2D.goSquirrel(
    penColors: List<Color> = listOf(Color.GREEN, Color.GREEN),
    angleChangeDeg: Degree = 20.0,
    startStroke: Float = 1.0f,
    strokeChange: Float = 1.0f,
    length: Int = 20,
    startX: Int,
    startY: Int,
    bluePrint: String
) {

    val stack = ArrayDeque<State>()
    //region start state
    val angleChange = angleChangeDeg.toRadian()
    var state = State(
        x = startX,
        y = startY,
        length = length,
        angle = -Math.PI / 2, // upright trees
        strokeWidth = startStroke,
        colorIndex = if (penColors.size > 1) Random.nextInt(0, penColors.size-1) else 0
    )
    stroke = BasicStroke(state.strokeWidth)
    //endregion

    bluePrint.forEach { c ->
        val jitter = Random.nextDouble((-5.0).toRadian(), 5.0.toRadian())
        state = when (c) {
            'F', 'f' -> {
                val newX = state.x + (state.length * cos(state.angle)).toInt()
                val newY = state.y + (state.length * sin(state.angle)).toInt()
                // The side effect to draw
                if (c == 'F') {
                    color = penColors[state.colorIndex]
                    drawLine(state.x, state.y, newX, newY)
                }
                state.copy(
                    x = newX,
                    y = newY,
                    strokeWidth = state.strokeWidth * strokeChange,
                    colorIndex = if (penColors.size > 1) (state.colorIndex + 1) % penColors.size else 0
                )
            }

            '+' -> state.copy(angle = state.angle - (angleChange + jitter))
            '-' -> state.copy(angle = state.angle + (angleChange + jitter))
            '`' -> state.copy(colorIndex = (state.colorIndex + 1) % penColors.size)
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

fun Graphics2D.goSquirrel(seedling:Seedling) {
    goSquirrel(
        startX = seedling.startX,
        startY = seedling.startY,
        angleChangeDeg = seedling.angleChangeDeg,
        startStroke = seedling.startStroke,
        strokeChange = seedling.strokeChange,
        length = seedling.length,
        bluePrint = seedling.bluePrint,
        penColors = seedling.colors)
}

//endregion



//region degree radian tools
typealias Degree = Double
typealias Radian = Double

fun Degree.toRadian(): Radian = this / 180 * Math.PI
fun Radian.toDegree(): Degree = this * 180 / Math.PI
//endregion