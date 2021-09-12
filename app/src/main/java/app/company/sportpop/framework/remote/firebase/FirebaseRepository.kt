package app.company.sportpop.framework.remote.firebase

import app.company.sportpop.R
import app.company.sportpop.framework.remote.NetworkApi
import app.company.sportpop.framework.remote.Response
import app.company.sportpop.framework.remote.firebase.mapper.FirebaseUserMapperUserJson
import app.company.sportpop.framework.remote.model.UserJson
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class User(val email: String = "", val displayName: String = "", val photo_url: String = "")

class FirebaseRepository @Inject constructor(
    private val mapperUserJson: FirebaseUserMapperUserJson
) : NetworkApi {

    override suspend fun login(email: String, password: String): Response<UserJson> = withContext(Dispatchers.IO) {
        try {
            val authResult = FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
            responseUser(authResult)
        } catch (e: FirebaseAuthException) {
            e.printStackTrace()
            responseUserError(ErrorNetwork(R.string.credentials_invalid, TypeError.Auth))
        }
    }

    override suspend fun register(email: String, displayName: String, password: String): Response<UserJson> = withContext(Dispatchers.IO) {
        try {
            val authResult = FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).await()
            authResult.user?.uid?.let { uid ->
                FirebaseFirestore.getInstance().collection("users").document(uid).set(User(email, displayName, ""))
            }
            responseUser(authResult)
        } catch (e: FirebaseAuthException) {
            e.printStackTrace()
            responseUserError(ErrorNetwork(R.string.user_invalid, TypeError.Auth))
        }
    }

    private fun responseUserError(errorFirebase: ErrorNetwork): Response<UserJson> {
        return Response(
            isSuccessful = false,
            body = null,
            error = errorFirebase
        )
    }

    private fun responseUser(authResult: AuthResult): Response<UserJson> {
        return if (authResult.user != null) {
            Response(
                isSuccessful = true,
                body = mapperUserJson.mapTo(authResult.user!!)
            )
        } else {
            Response(
                isSuccessful = false,
                body = UserJson.empty
            )
        }
    }
}
