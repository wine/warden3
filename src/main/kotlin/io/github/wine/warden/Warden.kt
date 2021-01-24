package io.github.wine.warden

import io.github.wine.warden.event.bus.EventBus
import io.github.wine.warden.patch.impl.patches.MinecraftPatch
import io.github.wine.warden.patch.registry.PatchRegistry

object Warden {

    val patches = PatchRegistry()
    val bus = EventBus()

    init {
        println("Warden#init")

        patches.put(MinecraftPatch())
    }
}