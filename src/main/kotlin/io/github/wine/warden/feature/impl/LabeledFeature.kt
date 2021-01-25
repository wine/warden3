package io.github.wine.warden.feature.impl

import io.github.wine.warden.feature.Feature

abstract class LabeledFeature : Feature {

    /**
     * The [identifier] in "human" form.
     */
    val label: String
        get() = identifier
            .replace("_", " ")
            .capitalize()
}