package app.company.sportpop.domain.entities

data class Product(
    val uid: String,
    val title: String,
    val description: String,
    val photoUrl: String,
    val userUid: String,
    val price: String
)
