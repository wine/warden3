package io.github.wine.warden.patch.annotate

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class Inject(
    val method: String,
    val descriptor: String
)