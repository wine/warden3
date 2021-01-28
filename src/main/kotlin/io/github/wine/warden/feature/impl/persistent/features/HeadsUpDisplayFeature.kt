package io.github.wine.warden.feature.impl.persistent.features

import io.github.wine.warden.Warden
import io.github.wine.warden.event.bus.EventSubscriber
import io.github.wine.warden.event.impl.game.events.RenderScreenEvent
import io.github.wine.warden.feature.impl.persistent.PersistentFeature
import io.github.wine.warden.ui.element.impl.hud.elements.FeaturesElement
import io.github.wine.warden.ui.element.impl.hud.elements.LogoElement
import io.github.wine.warden.ui.element.impl.hud.elements.TickRateElement
import io.github.wine.warden.ui.position.Position

class HeadsUpDisplayFeature : PersistentFeature("heads_up_display") {

    private val elements = listOf(
        LogoElement(Position(2, 2)),
        TickRateElement(Position(2, 14)),
        FeaturesElement(Position(2, 26))
    )

    private val renderScreenSubscriber = EventSubscriber<RenderScreenEvent> { event ->
        elements.forEach {
            it.render(event.game.fontRenderer)
        }
    }

    init {
        Warden.bus.subscribe(renderScreenSubscriber)
    }
}