package io.github.wine.warden.ui.element.impl.hud.elements

import io.github.wine.warden.Warden
import io.github.wine.warden.feature.impl.intermittent.IntermittentFeature
import io.github.wine.warden.ui.color.scheme.ColorScheme
import io.github.wine.warden.ui.element.impl.hud.HeadsUpDisplayElement
import io.github.wine.warden.ui.position.Position
import net.minecraft.src.FontRenderer

class FeaturesElement(override val position: Position) : HeadsUpDisplayElement {

    /**
     * Used to store the colors of [IntermittentFeature] instances.
     */
    private val colors = mutableMapOf<String, Int>()

    private var currentFeaturePositionOffset: Int = position.y
        get() {
            field += OFFSET_PER_FEATURE
            return field - OFFSET_PER_FEATURE
        }

    override fun render(fontRenderer: FontRenderer) {
        // Initial vertical position, after a string has been rendered it
        // should be re-calculated as lastPosition + OFFSET_PER_FEATURE
        currentFeaturePositionOffset = 0

        Warden.features
            .map { it.value }
            .filterIsInstance<IntermittentFeature>()
            .filter { it.state }
            .forEach {
                // Ensure the color for this feature has been created
                if (!colors.contains(it.identifier))
                    colors[it.identifier] = ColorScheme.generate().rgb

                // Get the color and fallback to DEFAULT_COLOR in case
                // it wasn't found (shouldn't ever happen?)
                val color = colors[it.identifier] ?: ColorScheme.text.rgb

                fontRenderer.drawStringWithShadow(it.label, position.x, position.y + currentFeaturePositionOffset, color)
            }
    }

    companion object {
        /**
         * Pixels between each [IntermittentFeature].
         */
        private const val OFFSET_PER_FEATURE = 12
    }
}