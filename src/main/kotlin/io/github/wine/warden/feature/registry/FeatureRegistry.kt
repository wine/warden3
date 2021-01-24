package io.github.wine.warden.feature.registry

import io.github.wine.warden.feature.Feature

/**
 * [MutableMap] with the [Feature.identifier] as key and the corresponding
 * [Feature] instance as value.
 */
class FeatureRegistry : MutableMap<String, Feature> by mutableMapOf() {

    /**
     * Associates the [feature] identifier with the [feature] instance in the registry.
     */
    fun put(feature: Feature) {
        this[feature.identifier] = feature
    }
}