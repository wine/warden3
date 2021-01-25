package io.github.wine.warden.feature.impl.execute.features

import com.xenomachina.argparser.ArgParser
import io.github.wine.warden.Warden
import io.github.wine.warden.feature.impl.execute.ExecutableFeature
import io.github.wine.warden.feature.impl.intermittent.IntermittentFeature

class ToggleFeature : ExecutableFeature("toggle") {

    override fun execute(arguments: Array<String>) {
        ArgParser(arguments).parseInto(::ToggleArguments).run {
            feature?.toggle()
        }
    }

    private class ToggleArguments(parser: ArgParser) {
        val feature by parser.positional("TARGET", "Identifier of the target intermittent feature") {
            Warden.features[this] as? IntermittentFeature
        }
    }
}