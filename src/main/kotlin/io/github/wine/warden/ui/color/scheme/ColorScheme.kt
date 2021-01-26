package io.github.wine.warden.ui.color.scheme

import java.awt.Color
import kotlin.random.Random

data class ColorScheme(
    val text: Color,
    val secondaryText: Color,
    val background: Color,
    val secondaryBackground: Color
) {

    fun generate(from: Color = text): Color {
        val hsb = Color.RGBtoHSB(from.red, from.green, from.blue, null)

        val hue = Random.nextFloat()
        val saturation = hsb[1]
        val luminance = hsb[2]

        return Color.getHSBColor(hue, saturation, luminance)
    }

    companion object {

        private val DEFAULT = ColorScheme(
            Color(0x9BA2FF),
            Color(0x9BA2FF),
            Color(0x2F3061),
            Color(0x363B59)
        )

        var CURRENT = DEFAULT

        fun generate(from: Color = text): Color {
            return CURRENT.generate(from)
        }

        val text: Color
        get() {
            return CURRENT.text
        }

        val secondaryText: Color
        get() {
            return CURRENT.secondaryText
        }

        val background: Color
        get() {
            return CURRENT.background
        }

        val secondaryBackground: Color
        get() {
            return CURRENT.secondaryBackground
        }
    }
}