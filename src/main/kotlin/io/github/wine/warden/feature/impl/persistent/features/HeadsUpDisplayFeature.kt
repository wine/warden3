package io.github.wine.warden.feature.impl.persistent.features

import io.github.wine.warden.Warden
import io.github.wine.warden.event.bus.EventSubscriber
import io.github.wine.warden.event.impl.game.events.RenderScreenEvent
import io.github.wine.warden.feature.impl.persistent.PersistentFeature
import java.awt.Color

class HeadsUpDisplayFeature : PersistentFeature("heads_up_display") {

    private val renderScreenSubscriber = EventSubscriber<RenderScreenEvent> {
        it.game.fontRenderer.drawStringWithShadow("Warden", 2, 2, Color.WHITE.rgb)
    }

    init {
        Warden.bus.subscribe(renderScreenSubscriber)
    }
}