package io.github.wine.warden.patch.registry

import io.github.wine.warden.patch.Patch

class PatchRegistry : MutableMap<String, Patch> by mutableMapOf() {

    fun put(patch: Patch) {
        this[patch.className] = patch
    }
}