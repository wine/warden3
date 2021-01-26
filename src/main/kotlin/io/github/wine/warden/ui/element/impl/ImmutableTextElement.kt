package io.github.wine.warden.ui.element.impl

import io.github.wine.warden.ui.element.Element
import io.github.wine.warden.ui.position.Position
import net.minecraft.src.FontRenderer
import java.awt.Color

open class ImmutableTextElement(override val position: Position, private val text: String, private val color: Int = Color.WHITE.rgb) :
    Element {

    override fun render(fontRenderer: FontRenderer) {
        fontRenderer.drawStringWithShadow(text, position.x, position.y, color)
    }
}