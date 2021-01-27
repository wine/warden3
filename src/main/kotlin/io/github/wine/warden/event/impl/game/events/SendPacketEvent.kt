package io.github.wine.warden.event.impl.game.events

import io.github.wine.warden.event.Cancellable
import io.github.wine.warden.event.impl.game.GameEvent
import net.minecraft.src.Packet

class SendPacketEvent(val packet: Packet, override var cancelled: Boolean = false) : GameEvent(), Cancellable