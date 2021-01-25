package io.github.wine.warden.patch.annotate

import io.github.wine.warden.load.Transformer
import io.github.wine.warden.patch.impl.ClassPatch
import org.objectweb.asm.tree.MethodNode

/**
 * Used to show the [Transformer] the methods in a [ClassPatch] that
 * patch a game method, the patcher method requires exactly 1 parameter,
 * the [MethodNode] that matches the [method] and [descriptor].
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class Inject(
    val method: String,
    val descriptor: String = ""
)