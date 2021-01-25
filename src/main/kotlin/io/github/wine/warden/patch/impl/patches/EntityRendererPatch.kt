package io.github.wine.warden.patch.impl.patches

import io.github.wine.warden.Warden
import io.github.wine.warden.event.impl.game.events.RenderWorldEvent
import io.github.wine.warden.patch.annotate.Inject
import io.github.wine.warden.patch.findLdcInstruction
import io.github.wine.warden.patch.impl.ClassPatch
import org.objectweb.asm.Opcodes.INVOKESTATIC
import org.objectweb.asm.Type
import org.objectweb.asm.tree.MethodInsnNode
import org.objectweb.asm.tree.MethodNode

class EntityRendererPatch : ClassPatch("net/minecraft/src/EntityRenderer") {

    @Inject("renderWorld", "(FJ)V")
    fun patchRenderWorld(methodNode: MethodNode) {
        methodNode.instructions.insertBefore(
            methodNode.findLdcInstruction("hand")?.next,
            MethodInsnNode(
                INVOKESTATIC,
                Type.getInternalName(this.javaClass),
                "renderWorld",
                "()V",
                false
            )
        )
    }

    companion object {
        @JvmStatic
        fun renderWorld() {
            Warden.bus.publish(RenderWorldEvent())
        }
    }
}