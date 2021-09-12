package app.company.sportpop.domain.use_case.auth

import app.company.sportpop.core.error_handling.Failure
import app.company.sportpop.core.functional.Either
import app.company.sportpop.domain.entities.User
import app.company.sportpop.domain.repository.Repository
import app.company.sportpop.domain.use_case.UseCase
import app.company.sportpop.domain.use_case.auth.LoginUseCase.Params
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: Repository): UseCase<User, Params>() {

    override suspend fun run(params: Params): Either<Failure, User> = repository.login(params.email, params.password)

    data class Params(
        val email: String,
        val password: String
    )
}
