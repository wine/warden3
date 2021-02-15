package io.github.wine.warden.feature.impl.intermittent.features

import io.github.wine.warden.Warden
import io.github.wine.warden.event.bus.EventSubscriber
import io.github.wine.warden.event.impl.game.events.UpdateEvent
import io.github.wine.warden.feature.impl.intermittent.IntermittentFeature
import net.minecraft.src.EntityClientPlayerMP

class SprintFeature : IntermittentFeature("sprint") {

    private val tickSubscriber = EventSubscriber<UpdateEvent> { event ->
        event.game.thePlayer.isSprinting = event.game.thePlayer.canNormallySprint()
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