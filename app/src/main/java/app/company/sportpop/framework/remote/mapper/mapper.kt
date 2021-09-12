package app.company.sportpop.framework.remote.mapper

import app.company.sportpop.core.mapper.MapperTo
import app.company.sportpop.domain.entities.User
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
