package app.company.sportpop.data.source

import app.company.sportpop.domain.entities.User

interface LocalDataSource {
    suspend fun saveUser(user: User)
    suspend fun findByEmailUser(email: String): User
    suspend fun deleteUser(uid: String)
}
