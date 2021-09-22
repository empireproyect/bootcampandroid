package app.company.sportpop.framework.remote.model

data class ProductJson(
    val uid: String = "",
    val title: String = "",
    val description: String = "",
    val photo_url: String = "",
    val user_uid: String = "",
    var price: String = ""
) {
    companion object {
        val empty = ProductJson("","", "", "", "")
    }
}
