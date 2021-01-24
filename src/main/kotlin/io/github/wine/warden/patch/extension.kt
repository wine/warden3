package io.github.wine.warden.patch

import org.objectweb.asm.tree.AbstractInsnNode
import org.objectweb.asm.tree.MethodNode

fun MethodNode.insertInstructionBeforeReturn(instructionNode: AbstractInsnNode) {
    val location = this.instructions.get(this.instructions.size() - 2)
    this.instructions.insertBefore(location, instructionNode)
}