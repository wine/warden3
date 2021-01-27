package io.github.wine.warden.feature.impl.execute.transform

import io.github.wine.warden.Warden
import io.github.wine.warden.feature.impl.intermittent.IntermittentFeature

fun String.toIntermittentFeature(): IntermittentFeature? {
    return Warden.features[this] as? IntermittentFeature
}