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
            } catch(e: ShowHelpException) {
                val help = StringWriter().apply { e.printUserMessage(this, identifier, 60) }.toString().split("\n")
                help.forEach(Minecraft.getMinecraft().ingameGUI.chatGUI::printChatMessage)
            } catch (e: Exception) {
                Minecraft.getMinecraft().ingameGUI.chatGUI.printChatMessage(e.localizedMessage)
            }
        }
    }

    init {
        Warden.bus.subscribe(executeCommandSubscriber)
    }

    abstract fun execute(arguments: Array<String>)
}