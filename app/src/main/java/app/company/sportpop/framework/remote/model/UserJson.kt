package app.company.sportpop.framework.remote.model

data class UserJson(
    val uid: String,
    val display_name: String,
    val email: String,
    val photo_url: String
) {
    companion object {
        val empty = UserJson("","", "", "")
    }
}
