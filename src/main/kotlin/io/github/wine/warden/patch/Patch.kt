package io.github.wine.warden.patch

import io.github.wine.warden.patch.impl.ClassPatch

/**
 * An abstract representation of a runtime patch.
 *
 * todo: there's not real reason for having this interface since the only implementation is [ClassPatch]
 */
interface Patch {

    val className: String
}