package io.github.wine.warden.ui.element.impl.hud.elements

import io.github.wine.warden.Warden
import io.github.wine.warden.event.bus.EventSubscriber
import io.github.wine.warden.event.impl.game.events.ReceivePacketEvent
import io.github.wine.warden.ui.color.scheme.ColorScheme
import io.github.wine.warden.ui.element.impl.hud.HeadsUpDisplayElement
import io.github.wine.warden.ui.position.Position
import net.minecraft.src.FontRenderer
import net.minecraft.src.Packet4UpdateTime
import kotlin.math.roundToInt


class TickRateElement(override val position: Position) : HeadsUpDisplayElement {

    private var lastRecordedTime = -1L
    private var currentTick = 0

    private val recordedTicks = LongArray(20).apply { fill(1000) }

    private val receivePacketSubscriber = EventSubscriber<ReceivePacketEvent> {
        if (it.packet is Packet4UpdateTime) {
            if (currentTick == 20)
                currentTick = 0

            if (lastRecordedTime != -1L) {
                val timeDifference = System.currentTimeMillis() - lastRecordedTime
                recordedTicks[currentTick] = timeDifference

                currentTick++
            }

            lastRecordedTime = System.currentTimeMillis()
        }
    }

    init {
        Warden.bus.subscribe(receivePacketSubscriber)
    }

    override fun render(fontRenderer: FontRenderer) {
        fontRenderer.drawStringWithShadow("${getTickRate()}%", position.x, position.y, ColorScheme.secondaryText.rgb)
    }

    private fun getTickRate(): Int {
        return (recordedTicks.sum() / (1000f * 20f) * 100f).roundToInt()
    }
}