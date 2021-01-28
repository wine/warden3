package io.github.wine.warden.patch.impl.patches

import io.github.wine.warden.Warden
import io.github.wine.warden.event.impl.game.events.ReceivePacketEvent
import io.github.wine.warden.event.impl.game.events.SendPacketEvent
import io.github.wine.warden.patch.annotate.Inject
import io.github.wine.warden.patch.findLdcInstruction
import io.github.wine.warden.patch.findMethodInstruction
import io.github.wine.warden.patch.impl.ClassPatch
import net.minecraft.src.Packet
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.Type
import org.objectweb.asm.tree.*

class TcpConnectionPatch : ClassPatch("net/minecraft/src/TcpConnection") {

    @Inject("addToSendQueue", "(Lnet/minecraft/src/Packet;)V")
    fun patchAddToSendQueue(methodNode: MethodNode) {
        val instructions = InsnList()

        // Load packet reference onto the stack
        instructions.add(VarInsnNode(ALOAD, 1))

        instructions.add(
            MethodInsnNode(
                INVOKESTATIC,
                Type.getInternalName(this.javaClass),
                "addToSendQueue",
                "(Lnet/minecraft/src/Packet;)Z",
                false
            )
        )

        val jump = LabelNode()

        // if return of addToSendQueue is true
        // we add a return
        instructions.add(JumpInsnNode(IFEQ, jump))
        instructions.add(InsnNode(RETURN))
        instructions.add(jump)

        methodNode.instructions.insert(instructions)
    }

    @Inject("readPacket", "()Z")
    fun patchReadPacket(methodNode: MethodNode) {
        val instructions = InsnList()

        // <localVar:index=2 , name=var2 , desc=Lnet/minecraft/src/Packet;, sig=null, start=L4, end=L5>
        // Load packet reference onto the stack
        instructions.add(VarInsnNode(ALOAD, 2))

        instructions.add(
            MethodInsnNode(
                INVOKESTATIC,
                Type.getInternalName(this.javaClass),
                "readPacket",
                "(Lnet/minecraft/src/Packet;)Z",
                false
            )
        )

        val jump = LabelNode()

        // if return of readPacket is true
        // we add a return
        instructions.add(JumpInsnNode(IFEQ, jump))
        instructions.add(InsnNode(ICONST_0))
        instructions.add(InsnNode(IRETURN))
        instructions.add(jump)

        methodNode.instructions.insert(
            methodNode.findMethodInstruction(
                INVOKESTATIC,
                "net/minecraft/src/Packet",
                "readPacket",
                "(Lnet/minecraft/src/ILogAgent;Ljava/io/DataInput;ZLjava/net/Socket;)Lnet/minecraft/src/Packet;"
            )?.next,
            instructions
        )
    }

    companion object {
        @JvmStatic
        fun addToSendQueue(packet: Packet): Boolean {
            val event = SendPacketEvent(packet)
            Warden.bus.publish(event)

            return event.cancelled
        }

        @JvmStatic
        fun readPacket(packet: Packet): Boolean {
            val event = ReceivePacketEvent(packet)
            Warden.bus.publish(event)

            return event.cancelled
        }
    }
}