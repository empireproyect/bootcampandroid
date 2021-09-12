package app.company.sportpop.core.error_handling

sealed class Failure {
    sealed class NetworkError : Failure() {
        data class Fatal(val reason: String? = "") : NetworkError()
        data class Recoverable(val reason: String? = "") : NetworkError()
        data class AuthError(val resourceId: Int): NetworkError()
        object NoConnection : NetworkError()
    }

    object DBError : Failure()
}
