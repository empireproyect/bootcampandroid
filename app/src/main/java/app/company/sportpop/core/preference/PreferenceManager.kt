package app.company.sportpop.core.preference

import app.company.sportpop.domain.entities.User

class PreferenceManager(private val preference: PreferenceRepository) {

    fun isLoggedIn() = preference.isLoggedIn()

    fun getUser() = preference.getUser()

    fun setUser(user: User) = preference.setUser(user)

    fun logout() = preference.logout()

}
