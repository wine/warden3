package io.github.wine.warden.patch

import org.objectweb.asm.tree.AbstractInsnNode
import org.objectweb.asm.tree.MethodNode
import org.objectweb.asm.tree.LdcInsnNode


fun MethodNode.insertInstructionBeforeReturn(instructionNode: AbstractInsnNode) {
    val location = this.instructions.get(this.instructions.size() - 2)
    this.instructions.insertBefore(location, instructionNode)
}

fun MethodNode.findLdcInstruction(string: String): LdcInsnNode? {
    return this.instructions.toArray()
        .filterIsInstance<LdcInsnNode>()
        .firstOrNull { it.cst.toString() == string }
}