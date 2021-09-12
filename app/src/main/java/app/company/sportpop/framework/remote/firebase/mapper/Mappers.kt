package app.company.sportpop.framework.remote.firebase.mapper

import app.company.sportpop.core.mapper.MapperTo
import app.company.sportpop.framework.remote.model.UserJson
import com.google.firebase.auth.FirebaseUser


class FirebaseUserMapperUserJson: MapperTo<FirebaseUser, UserJson> {
    override fun mapTo(t: FirebaseUser) =
        UserJson(
            t.uid,
            t.displayName.toString(),
            t.email.toString(),
            t.photoUrl.toString()
        )
}
