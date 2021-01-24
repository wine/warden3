package io.github.wine.warden.patch.impl

import io.github.wine.warden.patch.Patch
import io.github.wine.warden.patch.impl.patches.MinecraftPatch

/**
 * Implementation of the [Patch] interface specifically for classes.
 *
 * See [MinecraftPatch] for a basic example.
 */
open class ClassPatch(override val className: String) : Patch