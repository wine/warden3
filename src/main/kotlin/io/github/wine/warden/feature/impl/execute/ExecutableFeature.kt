package io.github.wine.warden.feature.impl.execute

import io.github.wine.warden.Warden
import io.github.wine.warden.event.bus.EventSubscriber
import io.github.wine.warden.event.impl.client.ExecuteCommandEvent
import io.github.wine.warden.feature.Feature

abstract class ExecutableFeature(override val identifier: String) : Feature {

    private val executeCommandSubscriber = EventSubscriber<ExecuteCommandEvent> {
        if (it.identifier == identifier)
            execute(it.arguments)
    }

    init {
        Warden.bus.subscribe(executeCommandSubscriber)
    }

    abstract fun execute(arguments: Array<String>)
}