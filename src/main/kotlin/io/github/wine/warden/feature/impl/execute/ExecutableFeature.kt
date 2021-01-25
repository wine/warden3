package io.github.wine.warden.feature.impl.execute

import com.xenomachina.argparser.ShowHelpException
import io.github.wine.warden.Warden
import io.github.wine.warden.event.bus.EventSubscriber
import io.github.wine.warden.event.impl.client.ExecuteCommandEvent
import io.github.wine.warden.feature.Feature
import net.minecraft.src.Minecraft
import java.io.StringWriter

abstract class ExecutableFeature(override val identifier: String) : Feature {

    private val executeCommandSubscriber = EventSubscriber<ExecuteCommandEvent> { event ->
        if (event.identifier == identifier) {
            try {
                execute(event.arguments)
            } catch(exception: ShowHelpException) {
                exception.printToMinecraft(identifier)
            } catch (exception: Exception) {
                exception.printToMinecraft()
            }
        }
    }

    init {
        Warden.bus.subscribe(executeCommandSubscriber)
    }

    abstract fun execute(arguments: Array<String>)
}