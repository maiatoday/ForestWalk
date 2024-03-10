package net.maiatoday.forestwalk.lsystem

import java.awt.Color
import java.awt.Graphics2D
import kotlin.math.cos
import kotlin.math.sin

data class State(val x: Int, val y: Int, val length: Int, val angle: Double = 0.0, val colorIndex:Int = 0)

fun Graphics2D.goTortoise(
    penColors: List<Color> = listOf(Color.GREEN),
    angleChange:Double = Math.PI/3,
    length:Int = 20,
    startX: Int,
    startY: Int,
    plant: Plant
) {
    val stack = ArrayDeque<State>()
    var state = State(startX, startY , length, -Math.PI / 2)
    plant.bluePrint.forEach { c ->
        state = when (c) {
            'F', 'f' -> {
                val newX = state.x + (state.length * cos(state.angle)).toInt()
                val newY = state.y + (state.length * sin(state.angle)).toInt()
                if (c == 'F') {
                    color = penColors[state.colorIndex]
                    drawLine(state.x, state.y, newX, newY)
                }
                state.copy(x = newX, y = newY)
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
    }
}
