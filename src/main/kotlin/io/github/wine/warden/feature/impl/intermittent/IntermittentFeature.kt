package io.github.wine.warden.feature.impl.intermittent

import io.github.wine.warden.event.bus.EventSubscriber
import io.github.wine.warden.feature.impl.LabeledFeature

/**
 * A intermittent features state is decided by the [state] variable,
 * when it's enabled [onEnable] is called and when it's disabled [onDisable] is called.
 *
 * The aforementioned callback functions should be used for setting local variables and handling
 * the [EventSubscriber] instances required for the feature to work.
 */
abstract class IntermittentFeature(override val identifier: String) : LabeledFeature() {

    var state = false
    private set(value) {
        if (value) {
            onEnable()
        }
        else {
            onDisable()
        }

        field = value
    }

    fun toggle() {
        state = !state
    }

    fun enable() {
        if (!state)
            state = true
    }

    fun disable() {
        if (state)
            state = false
    }

    abstract fun onEnable()
    abstract fun onDisable()
}