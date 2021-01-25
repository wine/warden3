package io.github.wine.warden.feature.impl.execute.transform

import com.xenomachina.argparser.InvalidArgumentException
import io.github.wine.warden.Warden
import io.github.wine.warden.feature.impl.intermittent.IntermittentFeature

fun String.toIntermittentFeature(): IntermittentFeature {
    val feature = Warden.features[this]
        ?: throw InvalidArgumentException("No feature with the identifier '$this' could be located")

    if (feature !is IntermittentFeature)
        throw InvalidArgumentException("Feature '$this' is not intermittent")

    return feature
}