package app.company.sportpop.framework.remote

import app.company.sportpop.core.error_handling.Failure
import app.company.sportpop.core.error_handling.Failure.NetworkError.AuthError
import app.company.sportpop.core.error_handling.Failure.NetworkError.Fatal
import app.company.sportpop.core.functional.Either
import app.company.sportpop.data.source.RemoteDataSource
import app.company.sportpop.domain.entities.User
import app.company.sportpop.framework.remote.firebase.ErrorNetwork
import app.company.sportpop.framework.remote.firebase.TypeError
import app.company.sportpop.framework.remote.mapper.UserJsonToMapperUser
import app.company.sportpop.framework.remote.model.UserJson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val networkApi: NetworkApi,
    private val mapperUser: UserJsonToMapperUser
): RemoteDataSource {
    override suspend fun login(email: String, password: String): Either<Failure, User> = withContext(Dispatchers.IO) {
        val response = networkApi.login(email, password)
        handlerResponse(
            response,
            data = response.body,
            transform = { mapperUser.mapTo(it) },
            default = UserJson.empty
        )
    }

    override suspend fun register(email: String, displayName: String, password: String): Either<Failure, User>  = withContext(Dispatchers.IO) {
        val response = networkApi.register(email, displayName, password)
        handlerResponse(
            response,
            data = response.body,
            transform = { mapperUser.mapTo(it) },
            default = UserJson.empty
        )
    }


    private fun <Json, D, R> handlerResponse(response: Response<Json>, data: D?, transform: (D) -> R, default: D): Either<Failure, R> {
        return when (response.isSuccessful) {
            true -> Either.Right(transform(data ?: default))
            false -> Either.Left(getNetWorkError(response.error!!))
        }
    }

    private fun getNetWorkError(error: ErrorNetwork): Failure {
        return when(error.typeError) {
            TypeError.Auth -> AuthError(error.resourceStringId)
            else -> Fatal()
        }
    }
}
