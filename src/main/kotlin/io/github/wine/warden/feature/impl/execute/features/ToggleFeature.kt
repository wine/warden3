package io.github.wine.warden.feature.impl.execute.features

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.InvalidArgumentException
import io.github.wine.warden.feature.impl.execute.ExecutableFeature
import io.github.wine.warden.feature.impl.execute.printToMinecraft
import io.github.wine.warden.feature.impl.execute.transform.toIntermittentFeature

class ToggleFeature : ExecutableFeature("toggle") {

    override fun execute(arguments: Array<String>) {
        ArgParser(arguments).parseInto(::ToggleArguments).run {
            feature.toggle()

            var output = "Toggled ${feature.identifier} "
            output += if (feature.state) "§a" + "on" else "§c" + "off"

            output.printToMinecraft()
        }
    }

    private class ToggleArguments(parser: ArgParser) {
        val feature by parser.positional("TARGET", "identifier of the target intermittent feature") {
            toIntermittentFeature() ?: throw InvalidArgumentException("Intermittent feature with the identifier '$this' could not be located")
        }
    }
}