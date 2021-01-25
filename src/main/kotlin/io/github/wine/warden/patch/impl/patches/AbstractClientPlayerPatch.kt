package io.github.wine.warden.patch.impl.patches

import io.github.wine.warden.patch.annotate.Inject
import io.github.wine.warden.patch.impl.ClassPatch
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.Type
import org.objectweb.asm.tree.*

class AbstractClientPlayerPatch : ClassPatch("net/minecraft/src/AbstractClientPlayer") {

    @Inject("getSkinUrl", "(Ljava/lang/String;)Ljava/lang/String;")
    fun patchGetSkinUrl(methodNode: MethodNode) {
        val instructions = InsnList()

        // Load name reference onto the stack
        instructions.add(VarInsnNode(ALOAD, 0))

        instructions.add(
            MethodInsnNode(
                INVOKESTATIC,
                Type.getInternalName(this.javaClass),
                "getSkinUrl",
                "(Ljava/lang/String;)Ljava/lang/String;",
                false
            )
        )

        // Return the String from getSkinUrl
        instructions.add(InsnNode(ARETURN))

        // Remove previous instructions since
        // they will never be called anyways
        methodNode.instructions.clear()

        methodNode.instructions.insert(instructions)
    }

    companion object {
        private const val API_URL = "https://minotar.net"

        @JvmStatic
        fun getSkinUrl(name: String): String {
            println(name)
            return "$API_URL/skin/$name"
        }
    }
}