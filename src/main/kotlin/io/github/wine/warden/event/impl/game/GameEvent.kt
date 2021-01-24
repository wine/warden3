package io.github.wine.warden.event.impl.game

import io.github.wine.warden.event.Event
import net.minecraft.src.Minecraft

open class GameEvent(val game: Minecraft = Minecraft.getMinecraft()) : Event