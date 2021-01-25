package io.github.wine.warden.feature

interface Feature {

    /**
     * Internal identifier for the feature, should not contain any special characters, spaces or unicode characters.
     */
    val identifier: String
}