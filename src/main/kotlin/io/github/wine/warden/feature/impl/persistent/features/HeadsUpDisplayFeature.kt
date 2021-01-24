package io.github.wine.warden.feature.impl.persistent.features

import io.github.wine.warden.Warden
import io.github.wine.warden.event.bus.EventSubscriber
import io.github.wine.warden.event.impl.game.events.RenderScreenEvent
import io.github.wine.warden.feature.impl.intermittent.IntermittentFeature
import io.github.wine.warden.feature.impl.persistent.PersistentFeature
import java.awt.Color

class HeadsUpDisplayFeature : PersistentFeature("heads_up_display") {

    private val renderScreenSubscriber = EventSubscriber<RenderScreenEvent> { event ->
        event.game.fontRenderer.drawStringWithShadow("Warden", 2, 2, Color.WHITE.rgb)

        var y = 2 + OFFSET_PER_FEATURE
        Warden.features
            .map { it.value }
            .filterIsInstance<IntermittentFeature>()
            .filter { it.state }
            .forEach {
                event.game.fontRenderer.drawStringWithShadow(it.label, 2, y, Color.WHITE.rgb)

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
    }
}