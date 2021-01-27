package io.github.wine.warden.feature.impl.persistent.features

import io.github.wine.warden.Warden
import io.github.wine.warden.event.bus.EventSubscriber
import io.github.wine.warden.event.impl.game.events.SendPacketEvent
import io.github.wine.warden.feature.impl.execute.printToMinecraft
import io.github.wine.warden.feature.impl.persistent.PersistentFeature
import net.minecraft.src.Packet10Flying
import kotlin.math.abs

class PreventBadPacketsFeature : PersistentFeature("prevent_bad_packets") {

    private val sendPacketSubscriber = EventSubscriber<SendPacketEvent> { event ->
        if (event.packet is Packet10Flying) {
            if (event.packet.moving && event.packet.yPosition == -999.0 && event.packet.stance == -999.0) {
                if (abs(event.packet.xPosition) > 1.0 || abs(event.packet.zPosition) > 1.0) {
                    "Prevented bad packet (invalid position)".printToMinecraft()
                    event.cancel()
                }
            }

            val stanceDifference = event.packet.stance - event.packet.yPosition
            if (event.packet.moving && (stanceDifference > 1.65 || stanceDifference < 0.1)) {
                "Prevented bad packet (illegal stance)".printToMinecraft()
                event.cancel()
            }

            if (abs(event.packet.xPosition) > 3.2E7 || abs(event.packet.zPosition) > 3.2E7) {
                "Prevented bad packet (illegal position)".printToMinecraft()
                event.cancel()
            }
        }
    }

    init {
        Warden.bus.subscribe(sendPacketSubscriber)
    }
}