package app.company.sportpop.framework.remote

import app.company.sportpop.framework.remote.model.UserJson

interface NetworkApi {
    suspend fun login(email: String, password: String): Response<UserJson>
    suspend fun register(email: String, displayName: String, password: String): Response<UserJson>
}
