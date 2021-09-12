package app.company.sportpop.domain.repository

import app.company.sportpop.core.error_handling.Failure
import app.company.sportpop.core.functional.Either
import app.company.sportpop.domain.entities.User

interface Repository {
    suspend fun login(email: String, password: String): Either<Failure, User>
    suspend fun register(email: String, displayName: String, password: String): Either<Failure, User>
}
