package io.github.wine.warden.event.bus

import io.github.wine.warden.event.Event

fun interface EventSubscriber<T : Event> {

    fun onPublish(event: T)
}