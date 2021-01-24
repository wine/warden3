package io.github.wine.warden.patch.registry

import io.github.wine.warden.patch.Patch

/**
 * [MutableMap] with the [Patch.className] as key and the corresponding
 * [Patch] instance as value.
 */
class PatchRegistry : MutableMap<String, Patch> by mutableMapOf() {

    /**
     * Associates the specified [Patch.className] with the specified [Patch] in the registry.
     */
    fun put(patch: Patch) {
        this[patch.className] = patch
    }
}