package io.github.wine.warden.feature.impl.persistent.features

import io.github.wine.warden.Warden
import io.github.wine.warden.event.bus.EventSubscriber
import io.github.wine.warden.event.impl.game.events.RenderScreenEvent
import io.github.wine.warden.feature.impl.intermittent.IntermittentFeature
import io.github.wine.warden.feature.impl.persistent.PersistentFeature
import java.awt.Color
import kotlin.random.Random

class HeadsUpDisplayFeature : PersistentFeature("heads_up_display") {

    /**
     * Used to store the colors of [IntermittentFeature] instances,
     * colors are created by the [getRandomColor] function "dynamically"
     * when requested (see [renderScreenSubscriber]).
     */
    private val colors = mutableMapOf<String, Int>()

    private val renderScreenSubscriber = EventSubscriber<RenderScreenEvent> { event ->
        // Initial vertical position, after text has been rendered it
        // should be re-calculated as lastPosition + OFFSET_PER_FEATURE
        var y = 2

        event.game.fontRenderer.drawStringWithShadow("Warden", 2, y, DEFAULT_COLOR)
        y += OFFSET_PER_FEATURE

        Warden.features
            .map { it.value }
            .filterIsInstance<IntermittentFeature>()
            .filter { it.state }
            .forEach {
                // Ensure the color for this feature has been created
                if (!colors.contains(it.identifier))
                    colors[it.identifier] = getRandomColor().rgb

                // Get the color and fallback to DEFAULT_COLOR in case
                // it wasn't found (shouldn't ever happen?)
                val color = colors[it.identifier] ?: DEFAULT_COLOR

                event.game.fontRenderer.drawStringWithShadow(it.label, 2, y, color)
                y += OFFSET_PER_FEATURE
            }
    }

    init {
        Warden.bus.subscribe(renderScreenSubscriber)
    }

    companion object {
        /**
         * Pixels between each [IntermittentFeature] rendered in [renderScreenSubscriber].
         */
        private const val OFFSET_PER_FEATURE = 12

        /**
         * If a [IntermittentFeature] for some reason doesn't have a color in the [colors] map
         * this will be used as a fallback.
         */
        private val DEFAULT_COLOR = Color.WHITE.rgb

        /**
         * Generate a [Color] with a random hue.
         */
        private fun getRandomColor(saturation: Float = 0.5f, luminance: Float = 1f): Color {
            return Color.getHSBColor(Random.nextFloat(), saturation, luminance)
        }
    }
}