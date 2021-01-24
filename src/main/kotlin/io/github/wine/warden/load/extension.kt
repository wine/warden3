package io.github.wine.warden.load

import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.MethodNode

fun ByteArray.toClassNode(): ClassNode {
    val classNode = ClassNode()
    val reader = ClassReader(this)
    reader.accept(classNode, 0)

    return classNode
}

fun ClassNode.getMethodNode(method: String, descriptor: String): MethodNode {
    return this.methods
        .map { it as MethodNode }
        .filter { it.name == method }
        .first { it.desc == descriptor }
}

fun ClassNode.toByteArray(): ByteArray {
    val writer = ClassWriter(ClassWriter.COMPUTE_MAXS or ClassWriter.COMPUTE_FRAMES)
    this.accept(writer)
    return writer.toByteArray()
}