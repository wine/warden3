package io.github.wine.warden.event

interface Cancellable {

    var cancelled: Boolean

    fun cancel() {
        cancelled = true
    }
}