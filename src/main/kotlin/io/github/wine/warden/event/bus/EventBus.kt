package io.github.wine.warden.event.bus

import io.github.wine.warden.event.Event
import kotlin.reflect.KClass

class EventBus {

    private val map = mutableMapOf<Class<*>, EventPublisher<*>>()

    operator fun <T : Event> get(type: Class<T>): EventPublisher<T> {
        val handler = map.getOrPut(type, { EventPublisher<T>() })

        @Suppress("UNCHECKED_CAST")
        return handler as EventPublisher<T>
    }

    operator fun <T : Event> get(type: KClass<T>): EventPublisher<T> {
        return get(type.java)
    }

    inline fun <reified T : Event> publish(event: T) {
        this[T::class].publish(event)
    }

    inline fun <reified T : Event> subscribe(subscriber: EventSubscriber<T>) {
        this[T::class].subscribe(subscriber)
    }

    inline fun <reified T : Event> unsubscribe(subscriber: EventSubscriber<T>) {
        this[T::class].unsubscribe(subscriber)
    }
}