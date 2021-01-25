package io.github.wine.warden.feature.impl.intermittent.features

import io.github.wine.warden.Warden
import io.github.wine.warden.event.bus.EventSubscriber
import io.github.wine.warden.event.impl.game.events.TickEvent
import io.github.wine.warden.feature.impl.intermittent.IntermittentFeature
import net.minecraft.src.EntityClientPlayerMP

class SprintFeature : IntermittentFeature("sprint") {

    /**
     * To be replaced with an event published from the players movement logic method.
     */
    private val tickSubscriber = EventSubscriber<TickEvent> {
        it.game.thePlayer.isSprinting = it.game.thePlayer.canNormallySprint()
    }

    override fun onEnable() {
        Warden.bus.subscribe(tickSubscriber)
    }

    override fun onDisable() {
        Warden.bus.unsubscribe(tickSubscriber)
    }

    /**
     * Whether or not the [EntityClientPlayerMP] can sprint by just
     * holding the sprint modifier key.
     */
    private fun EntityClientPlayerMP.canNormallySprint(): Boolean {
        return (this.moveForward != 0f || this.moveStrafing != 0f) && !this.isCollidedHorizontally
    }
}