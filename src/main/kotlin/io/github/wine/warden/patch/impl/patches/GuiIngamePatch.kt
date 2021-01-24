package io.github.wine.warden.patch.impl.patches

import io.github.wine.warden.Warden
import io.github.wine.warden.event.impl.game.events.RenderScreenEvent
import io.github.wine.warden.patch.annotate.Inject
import io.github.wine.warden.patch.impl.ClassPatch
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import org.objectweb.asm.tree.MethodInsnNode
import org.objectweb.asm.tree.MethodNode

class GuiIngamePatch : ClassPatch("net/minecraft/src/GuiIngame") {

    @Inject("renderGameOverlay", "(FZII)V")
    fun patchRenderGameOverlay(methodNode: MethodNode) {
        methodNode.instructions.insert(
            MethodInsnNode(
                Opcodes.INVOKESTATIC,
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