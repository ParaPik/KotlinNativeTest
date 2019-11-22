package sample

class GenericDataType<T : Any>(val data: T) {
    fun stringValue() = data.toString()
}

enum class EventType(val analyticsName: String) {
    USER_TRIGGER("user_trigger"),
    BACKGROUND_TRIGGER("background_trigger")
}

interface AnalyticsSender {
    fun sendAnalytics(name: String, parameters: Map<String, Any>)
}

data class Parameters(val timestamp: Long, val account: String)

class Analytics(private val sender: AnalyticsSender) {

    fun prepareAnalytics(type: EventType, parametersGenerator: () -> Parameters?) {
        parametersGenerator()?.also {
            sender.sendAnalytics(
                type.analyticsName,
                mapOf(
                    "timestamp" to it.timestamp, "account" to it.account
                )
            )
        }
    }

}