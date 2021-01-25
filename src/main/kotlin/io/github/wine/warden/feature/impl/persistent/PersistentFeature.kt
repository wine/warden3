package io.github.wine.warden.feature.impl.persistent

import io.github.wine.warden.event.bus.EventSubscriber
import io.github.wine.warden.feature.impl.LabeledFeature

/**
 * A persistent feature is continuously running and is stateless.
 * If a persistent feature requires [EventSubscriber] instances they should be registered in the init clause.
 */
abstract class PersistentFeature(override val identifier: String) : LabeledFeature()