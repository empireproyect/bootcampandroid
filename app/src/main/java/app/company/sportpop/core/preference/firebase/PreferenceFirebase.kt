package app.company.sportpop.core.preference.firebase

import app.company.sportpop.core.preference.PreferenceRepository
import app.company.sportpop.domain.entities.User
import com.google.firebase.auth.FirebaseAuth

class PreferenceFirebase: PreferenceRepository {
    override fun isLoggedIn() = FirebaseAuth.getInstance().currentUser != null

    override fun getUser(): User? {
         FirebaseAuth.getInstance().currentUser?.let {
             return User(it.uid, it.email.toString(), it.displayName.toString(), it.photoUrl.toString())
        }
        return null
    }

    override fun setUser(user: User) {}

    override fun logout() {
        FirebaseAuth.getInstance().signOut();
    }
}
