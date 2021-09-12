package app.company.sportpop.domain.use_case.auth

import app.company.sportpop.core.error_handling.Failure
import app.company.sportpop.core.functional.Either
import app.company.sportpop.domain.entities.User
import app.company.sportpop.domain.repository.Repository
import app.company.sportpop.domain.use_case.UseCase
import app.company.sportpop.domain.use_case.auth.RegisterUseCase.Params
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val repository: Repository): UseCase<User, Params>() {

    override suspend fun run(params: Params): Either<Failure, User> = repository.register(params.email, params.displayName, params.password)

    data class Params(
        val displayName: String,
        val email: String,
        val password: String
    )
}
