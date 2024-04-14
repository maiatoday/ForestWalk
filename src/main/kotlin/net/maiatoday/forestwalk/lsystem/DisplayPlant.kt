package net.maiatoday.forestwalk.lsystem

import net.maiatoday.forestwalk.tools.shrubShades
import net.maiatoday.forestwalk.tools.treeShades

enum class Ground { Fore, Middle, Back }
data class Seedling(
    val bluePrint: String,
    val startX: Int,
    val startY: Int,
    val isTree: Boolean,
    val ground: Ground
) {
    val colors = if (isTree) treeShades else shrubShades
    val length = if (isTree) 5 else 6
    val startStroke = if (isTree) 15.0f else 8.0f
    val strokeChange = if (isTree) 0.98f else 0.9f
}