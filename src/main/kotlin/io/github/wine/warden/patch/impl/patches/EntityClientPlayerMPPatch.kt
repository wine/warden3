package io.github.wine.warden.patch.impl.patches

import io.github.wine.warden.Warden
import io.github.wine.warden.event.impl.client.ExecuteCommandEvent
import io.github.wine.warden.event.impl.game.events.UpdateEvent
import io.github.wine.warden.patch.annotate.Inject
import io.github.wine.warden.patch.impl.ClassPatch
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.Type
import org.objectweb.asm.tree.*

class EntityClientPlayerMPPatch : ClassPatch("net/minecraft/src/EntityClientPlayerMP") {

    @Inject("sendChatMessage")
    fun patchSendChatMessage(methodNode: MethodNode) {
        val instructions = InsnList()

        // Load message reference onto the stack
        instructions.add(VarInsnNode(ALOAD, 1))

        instructions.add(
            MethodInsnNode(
                INVOKESTATIC,
                Type.getInternalName(this.javaClass),
                "sendChatMessage",
                "(Ljava/lang/String;)Z",
                false
            )
        )

        val jump = LabelNode()

        // if return of sendChatMessage is true
        // we add a return
        instructions.add(JumpInsnNode(IFEQ, jump))
        instructions.add(InsnNode(RETURN))
        instructions.add(jump)

        methodNode.instructions.insert(instructions)
    }

    @Inject("onUpdate")
    fun patchOnUpdate(methodNode: MethodNode) {
        methodNode.instructions.insert(
            MethodInsnNode(
                INVOKESTATIC,
                Type.getInternalName(this.javaClass),
                "onUpdate",
                "()V",
                false
            )
        )
    }

    companion object {
        @JvmStatic
        fun sendChatMessage(string: String): Boolean {
            if (string.startsWith(".")) {
                val arguments = string.removePrefix(".").split(" ").toMutableList()
                val identifier = arguments.removeAt(0)

                Warden.bus.publish(ExecuteCommandEvent(identifier, arguments.toTypedArray()))

                return true
            }

            return false
        }

        @JvmStatic
        fun onUpdate() {
            Warden.bus.publish(UpdateEvent())
        }
    }
}