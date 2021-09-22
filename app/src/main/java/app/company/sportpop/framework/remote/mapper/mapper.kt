package app.company.sportpop.framework.remote.mapper

import app.company.sportpop.core.mapper.MapperTo
import app.company.sportpop.domain.entities.Product
import app.company.sportpop.domain.entities.User
import app.company.sportpop.framework.remote.model.ProductJson
import app.company.sportpop.framework.remote.model.UserJson


class UserJsonToMapperUser: MapperTo<UserJson, User> {
    override fun mapTo(t: UserJson) =
        User(
            t.uid,
            t.email,
            t.display_name,
            t.photo_url
        )

}

class ProductJsonToMapperProduct: MapperTo<ProductJson, Product> {
    override fun mapTo(t: ProductJson) =
        Product(
            t.uid,
            t.title,
            t.description,
            t.photo_url,
            t.user_uid,
            t.price
        )
}
