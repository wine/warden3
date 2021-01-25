package io.github.wine.warden.feature.impl.execute

import com.xenomachina.argparser.ShowHelpException
import net.minecraft.src.Minecraft
import java.io.StringWriter

fun Any.printToMinecraft() {
    Minecraft.getMinecraft().ingameGUI.chatGUI.printChatMessage(this.toString())
}

fun ShowHelpException.printToMinecraft(identifier: String) {
    val help = StringWriter().apply { printUserMessage(this, identifier, 60) }.toString().split("\n")
    help.forEach(Minecraft.getMinecraft().ingameGUI.chatGUI::printChatMessage)
}

fun Exception.printToMinecraft() {
    Minecraft.getMinecraft().ingameGUI.chatGUI.printChatMessage(localizedMessage)
}