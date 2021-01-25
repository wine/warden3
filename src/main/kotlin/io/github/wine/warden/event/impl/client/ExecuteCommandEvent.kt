package io.github.wine.warden.event.impl.client

import io.github.wine.warden.event.Event

class ExecuteCommandEvent(val identifier: String, val arguments: Array<String>) : Event