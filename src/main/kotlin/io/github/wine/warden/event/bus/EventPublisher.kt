package io.github.wine.warden.event.bus

import io.github.wine.warden.event.Event

class EventPublisher<T : Event> {

    private val subscribers = mutableListOf<EventSubscriber<T>>()

    fun publish(event: T) {
        subscribers.forEach {
            try {
                it.onPublish(event)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun subscribe(subscriber: EventSubscriber<T>) {
        subscribers.add(subscriber)
    }

    fun unsubscribe(subscriber: EventSubscriber<T>) {
        subscribers.remove(subscriber)
    }
}