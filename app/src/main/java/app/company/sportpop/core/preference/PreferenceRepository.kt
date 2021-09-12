package app.company.sportpop.core.preference

import app.company.sportpop.domain.entities.User

interface PreferenceRepository {
    fun isLoggedIn(): Boolean
    fun getUser(): User?
    fun setUser(user: User)
    fun logout()
}
