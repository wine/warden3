package io.github.wine.warden.load

import io.github.wine.warden.Warden
import io.github.wine.warden.patch.annotate.Inject
import java.lang.instrument.ClassFileTransformer
import java.security.ProtectionDomain

class Transformer : ClassFileTransformer {

    override fun transform(
        loader: ClassLoader?,
        name: String?,
        beingRedefined: Class<*>?,
        protectionDomain: ProtectionDomain?,
        buffer: ByteArray?
    ): ByteArray {
        // ensure buffer is not null
        buffer ?: return buffer!!

        // find patch, if it's not found just (e.g there is no patch for the class)
        // return the original buffer.
        val patch = Warden.patches[name] ?: return buffer

        println("Found class patch for $name")

        val classNode = buffer.toClassNode()

        patch.javaClass.declaredMethods
            .filter { it.isAnnotationPresent(Inject::class.java) }
            .filter { it.parameterCount > 0 }
            .forEach {
                val injection = it.getAnnotation(Inject::class.java)

                // get method node for the injection points method
                val methodNode = classNode.getMethodNode(injection.method, injection.descriptor)

                // invoke injections method (e.g the method that patches the method node)
                it.invoke(patch, methodNode)

                println("Patched $name#${injection.method}")
            }

        return classNode.toByteArray()
    }
}