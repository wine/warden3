package io.github.wine.warden.patch.impl.patches

import io.github.wine.warden.Warden
import io.github.wine.warden.event.impl.game.events.TickEvent
import io.github.wine.warden.patch.annotate.Inject
import io.github.wine.warden.patch.impl.ClassPatch
import net.minecraft.src.Minecraft
import org.objectweb.asm.Opcodes.INVOKESTATIC
import org.objectweb.asm.Type
import org.objectweb.asm.tree.MethodInsnNode
import org.objectweb.asm.tree.MethodNode

class MinecraftPatch : ClassPatch("net/minecraft/src/Minecraft") {

    @Inject("runTick", "()V")
    fun patchRunTick(methodNode: MethodNode) {
        println("MinecraftPatch#patchRunTick")

        methodNode.instructions.insert(
            MethodInsnNode(
                INVOKESTATIC,
                Type.getInternalName(this.javaClass),
                "runTick",
                "()V",
                false
            )
        )
    }

    companion object {
        @JvmStatic
        fun runTick() {
            Warden.bus.publish(TickEvent())
        }
    }
}