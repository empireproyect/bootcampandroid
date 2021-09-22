package app.company.sportpop.domain.use_case.main

import app.company.sportpop.domain.entities.Product
import app.company.sportpop.domain.repository.Repository
import app.company.sportpop.domain.use_case.UseCase
import javax.inject.Inject

class HomeUseCase @Inject constructor(private val repository: Repository): UseCase<List<Product>, HomeUseCase.Params>() {

    override suspend fun run(params: Params) = repository.getProducts()

    class Params
}
