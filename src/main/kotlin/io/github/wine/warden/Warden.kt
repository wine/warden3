package io.github.wine.warden

import io.github.wine.warden.event.bus.EventBus
import io.github.wine.warden.patch.impl.patches.MinecraftPatch
import io.github.wine.warden.patch.registry.PatchRegistry

/**
 * Warden singleton, holds necessary fields such as the [EventBus] and [PatchRegistry],
 * a Kotlin object will always be the same instance no matter how many times
 * we request it, there can only be one instance of this class.
 */
object Warden {

    val patches = PatchRegistry()
    val bus = EventBus()

    init {
        println("Warden#init")

        patches.put(MinecraftPatch())
    }
}