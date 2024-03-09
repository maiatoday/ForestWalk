package net.maiatoday.forestwalk.lsystem

import java.awt.Color
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.image.BufferedImage

data class Segment(var x: Double, var y: Double, var angle: Double)

fun tortoiseDraw(lsystem: String, iterations: Int): BufferedImage {
    val width = 1000
    val height = 1000
    val angleChange = Math.PI / 3  // 60 degrees
    val initialLength = height / (2.0 * iterations)
    val lengthDecreaseFactor = 0.6
    val stack = mutableListOf<Segment>()
    var segment = Segment(width / 2.0, 0.9 * height, -Math.PI / 2)
    var length = initialLength
    val bufferedImage = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
    val g2d = bufferedImage.createGraphics()
    with(g2d) {
        setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        color = Color.BLACK
        fillRect(0, 0, width, height)
        color = Color.GREEN
        lsystem.forEach { c -> segment = drawSegment(c, segment, stack, angleChange, lengthDecreaseFactor, length) }
        dispose()
    }
    return bufferedImage
}

private fun Graphics2D.drawSegment(
    c: Char, segment: Segment, stack: MutableList<Segment>, angleChange: Double,
    lengthDecreaseFactor: Double, length: Double
): Segment {
    var newLength = length
    var newSegment = segment
    when (c) {
        'F' -> {
            val newX = segment.x + length * Math.cos(segment.angle)
            val newY = segment.y + length * Math.sin(segment.angle)
            drawLine(segment.x.toInt(), segment.y.toInt(), newX.toInt(), newY.toInt())
            newSegment = Segment(newX, newY, segment.angle)
        }

        '+' -> newSegment = Segment(segment.x, segment.y, segment.angle - angleChange)
        '-' -> newSegment = Segment(segment.x, segment.y, segment.angle + angleChange)
        '[' -> {
            stack.add(Segment(segment.x, segment.y, segment.angle))
            newLength *= lengthDecreaseFactor
        }

        ']' -> {
            newSegment = stack.removeAt(stack.size - 1)
            newLength /= lengthDecreaseFactor
        }

        else -> {
        }
    }
    return newSegment
}