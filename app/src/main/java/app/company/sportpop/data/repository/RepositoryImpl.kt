package app.company.sportpop.data.repository

import app.company.sportpop.core.connectivity.base.ConnectivityProvider
import app.company.sportpop.core.error_handling.Failure
import app.company.sportpop.core.error_handling.Failure.DBError
import app.company.sportpop.core.error_handling.Failure.NetworkError.NoConnection
import app.company.sportpop.core.functional.Either
import app.company.sportpop.core.functional.getOrElse
import app.company.sportpop.data.source.LocalDataSource
import app.company.sportpop.data.source.RemoteDataSource
import app.company.sportpop.domain.entities.User
import app.company.sportpop.domain.repository.Repository
import java.lang.Exception
import javax.inject.Inject

class RepositoryIml @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val connectivityProvider: ConnectivityProvider
) : Repository {
    override suspend fun login(email: String, password: String): Either<Failure, User> {
        if (connectivityProvider.isNetworkAvailable()) {
            val response = remoteDataSource.login(email, password)
            response.getOrElse(null)?.let {
                localDataSource.saveUser(it)
            }
            return response
        }

        return try {
            return Either.Right(localDataSource.findByEmailUser(email))
        } catch (e: Exception) {
            Either.Left(DBError)
        }
    }

    override suspend fun register(email: String, displayName: String, password: String): Either<Failure, User> {
        return if (connectivityProvider.isNetworkAvailable()) {
            val response =  remoteDataSource.register(email, displayName, password)
            response.getOrElse(null)?.let {
                localDataSource.saveUser(it)
            }
            response
        } else {
            Either.Left(NoConnection)
        }
    }
}
