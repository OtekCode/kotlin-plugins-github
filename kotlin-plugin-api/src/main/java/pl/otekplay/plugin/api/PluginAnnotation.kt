package pl.otekplay.plugin.api


@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class PluginAnnotation(val name: String, val dependency: Array<String>)