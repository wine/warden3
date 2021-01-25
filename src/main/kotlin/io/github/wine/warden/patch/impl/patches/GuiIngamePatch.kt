package io.github.wine.warden.patch.impl.patches

import io.github.wine.warden.Warden
import io.github.wine.warden.event.impl.game.events.RenderScreenEvent
import io.github.wine.warden.patch.annotate.Inject
import io.github.wine.warden.patch.impl.ClassPatch
import io.github.wine.warden.patch.insertInstructionBeforeReturn
import org.objectweb.asm.Opcodes.INVOKESTATIC
import org.objectweb.asm.Type
import org.objectweb.asm.tree.MethodInsnNode
import org.objectweb.asm.tree.MethodNode

class GuiIngamePatch : ClassPatch("net/minecraft/src/GuiIngame") {

    @Inject("renderGameOverlay")
    fun patchRenderGameOverlay(methodNode: MethodNode) {
        methodNode.insertInstructionBeforeReturn(
            MethodInsnNode(
                INVOKESTATIC,
                Type.getInternalName(this.javaClass),
                "renderGameOverlay",
                "()V",
                false
            )
        )
    }

    companion object {
        @JvmStatic
        fun renderGameOverlay() {
            Warden.bus.publish(RenderScreenEvent())
        }
    }
}