package io.github.wine.warden.patch.impl.patches

import io.github.wine.warden.Warden
import io.github.wine.warden.event.impl.game.events.SendPacketEvent
import io.github.wine.warden.patch.annotate.Inject
import io.github.wine.warden.patch.impl.ClassPatch
import net.minecraft.src.Packet
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import org.objectweb.asm.tree.*

class TcpConnectionPatch : ClassPatch("net/minecraft/src/TcpConnection") {

    @Inject("addToSendQueue", "(Lnet/minecraft/src/Packet;)V")
    fun patchAddToSendQueue(methodNode: MethodNode) {
        val instructions = InsnList()

        // Load packet reference onto the stack
        instructions.add(VarInsnNode(Opcodes.ALOAD, 1))

        instructions.add(
            MethodInsnNode(
                Opcodes.INVOKESTATIC,
                Type.getInternalName(this.javaClass),
                "addToSendQueue",
                "(Lnet/minecraft/src/Packet;)Z",
                false
            )
        )

        val jump = LabelNode()

        // if return of addToSendQueue is true
        // we add a return
        instructions.add(JumpInsnNode(Opcodes.IFEQ, jump))
        instructions.add(InsnNode(Opcodes.RETURN))
        instructions.add(jump)

        methodNode.instructions.insert(instructions)
    }

    companion object {
        @JvmStatic
        fun addToSendQueue(packet: Packet): Boolean {
            val event = SendPacketEvent(packet)
            Warden.bus.publish(event)

            return event.cancelled
        }
    }
}