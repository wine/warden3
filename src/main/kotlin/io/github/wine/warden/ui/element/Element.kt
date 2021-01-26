package io.github.wine.warden.ui.element

import io.github.wine.warden.ui.position.Position
import net.minecraft.src.FontRenderer

interface Element {

    val position: Position

    fun render(fontRenderer: FontRenderer)
}