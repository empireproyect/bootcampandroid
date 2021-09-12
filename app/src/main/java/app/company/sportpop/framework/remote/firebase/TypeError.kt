package app.company.sportpop.framework.remote.firebase

enum class TypeError {
    Auth,
    Firestore
}

data class ErrorNetwork(
    val resourceStringId: Int,
    val typeError: TypeError
)
