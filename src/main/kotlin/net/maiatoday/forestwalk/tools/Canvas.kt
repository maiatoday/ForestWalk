package net.maiatoday.forestwalk.tools
import java.awt.Color
import java.awt.Graphics2D
import java.awt.image.BufferedImage

fun canvas(
    width: Int = 300,
    height: Int = 300,
    backgroundColour: Color = Color.WHITE,
    startImage: BufferedImage? = null,
    block: Graphics2D.(width: Int, height: Int) -> Unit,
): BufferedImage {
    val image = startImage ?: BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
    val graphics = image.createGraphics()
    with(graphics) {
        background = backgroundColour
        clearRect(0, 0, width, height)
        setRenderingHint(
            java.awt.RenderingHints.KEY_ANTIALIASING,
            java.awt.RenderingHints.VALUE_ANTIALIAS_ON
        )
        block(width, height)
    }
    graphics.dispose()
    return image
}