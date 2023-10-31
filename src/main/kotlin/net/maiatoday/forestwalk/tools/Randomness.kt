package net.maiatoday.forestwalk.tools

import java.awt.Color
import kotlin.random.Random
import kotlin.math.*

fun randomColor(): Color {
    val colorRange = 0..255
    val r = colorRange.random()
    val g = colorRange.random()
    val b = colorRange.random()
    return Color(r, g, b)
}

fun randomGrey(): Color {
    val colorRange = 0..255
    val c = colorRange.random()
    return Color(c, c, c)
}

data class Point(val x: Int, val y: Int)

fun randomOffset(width: Int, height: Int) =
    Point(x = Random.nextInt(0, width), y = Random.nextInt(0, height))

fun randomRadius(min: Int = 5, max: Int = 100) = Random.nextInt(min, max)

class PoissonDisc(
    private val width: Float,
    private val height: Float,
    private val radius: Float,
    private val k: Int = 30
) {
    private val cellSize = radius / sqrt(2f)
    private val gridWidth = ceil(width / cellSize).toInt()
    private val gridHeight = ceil(height / cellSize).toInt()
    private val grid = Array(gridWidth * gridHeight) { -1 }
    private val points = mutableListOf<Pair<Float, Float>>()
    private val activePoints = mutableListOf<Pair<Float, Float>>()

    fun generatePoints(): List<Pair<Float, Float>> {
        val initialPoint = Pair(
            (random() * width).toFloat(),
            (random() * height).toFloat()
        )

        points.add(initialPoint)
        activePoints.add(initialPoint)

        while (activePoints.isNotEmpty()) {
            val activeIndex = (random() * activePoints.size).toInt()
            val activePoint = activePoints[activeIndex]

            var found = false
            for (i in 0 until k) {
                val newPoint = generateRandomPointAround(activePoint)

                if (newPoint.first < 0 || newPoint.first >= width ||
                    newPoint.second < 0 || newPoint.second >= height
                ) {
                    continue
                }

                val cellX = (newPoint.first / cellSize).toInt()
                val cellY = (newPoint.second / cellSize).toInt()

                var isValid = true

                for (dx in -1..1) {
                    for (dy in -1..1) {
                        val neighbourX = cellX + dx
                        val neighbourY = cellY + dy

                        if (neighbourX < 0 || neighbourX >= gridWidth ||
                            neighbourY < 0 || neighbourY >= gridHeight
                        ) {
                            continue
                        }

                        val neighbourIndex = neighbourY * gridWidth + neighbourX
                        val neighbour = grid[neighbourIndex]

                        if (neighbour != -1 && isTooClose(points[neighbour], newPoint)) {
                            isValid = false
                            break
                        }
                    }

                    if (!isValid) break
                }

                if (isValid) {
                    points.add(newPoint)
                    activePoints.add(newPoint)
                    grid[cellY * gridWidth + cellX] = points.lastIndex
                    found = true
                    break
                }
            }

            if (!found) {
                activePoints.removeAt(activeIndex)
            }
        }

        return points
    }

    private fun generateRandomPointAround(point: Pair<Float, Float>): Pair<Float, Float> {
        val angle = random() * 2 * PI.toFloat()
        val distance = radius + random() * radius

        val x = point.first + cos(angle) * distance
        val y = point.second + sin(angle) * distance

        return x.toFloat() to y.toFloat()
    }

    private fun isTooClose(pointA: Pair<Float, Float>, pointB: Pair<Float, Float>): Boolean {
        val dx = pointA.first - pointB.first
        val dy = pointA.second - pointB.second
        val distance = sqrt(dx * dx + dy * dy)

        return distance < radius
    }

    private fun random(): Double {
        return Random.nextDouble()
    }
}

fun buildPoissonDiscPoints(width: Int, height: Int, radius: Float = 20f): List<Point> =
    PoissonDisc(width.toFloat(), height.toFloat(), radius)
        .generatePoints()
        .map { Point(it.first.toInt(), it.second.toInt()) }