package io.github.wine.warden

import io.github.wine.warden.event.bus.EventBus
import io.github.wine.warden.feature.impl.execute.features.ToggleFeature
import io.github.wine.warden.feature.impl.intermittent.IntermittentFeature
import io.github.wine.warden.feature.impl.intermittent.features.SprintFeature
import io.github.wine.warden.feature.impl.persistent.features.HeadsUpDisplayFeature
import io.github.wine.warden.feature.registry.FeatureRegistry
import io.github.wine.warden.patch.impl.patches.EntityClientPlayerMPPatch
import io.github.wine.warden.patch.impl.patches.GuiIngamePatch
import io.github.wine.warden.patch.impl.patches.MinecraftPatch
import io.github.wine.warden.patch.registry.PatchRegistry

/**
 * Warden singleton, holds necessary fields such as the [EventBus] and [PatchRegistry],
 * a Kotlin object will always be the same instance no matter how many times
 * we request it, there can only be one instance of this class.
 */
object Warden {

    val bus = EventBus()

    val patches = PatchRegistry()
    val features = FeatureRegistry()

    init {
        patches.put(MinecraftPatch())
        patches.put(GuiIngamePatch())
        patches.put(EntityClientPlayerMPPatch())

        features.put(ToggleFeature())

        features.put(HeadsUpDisplayFeature())

        features.put(SprintFeature())
    }
}