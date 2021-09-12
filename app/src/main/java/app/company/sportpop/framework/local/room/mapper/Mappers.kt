package app.company.sportpop.framework.local.room.mapper

import app.company.sportpop.domain.entities.User
import app.company.sportpop.framework.local.room.model.UserModel


fun User.toRoom(): UserModel = UserModel(uid, email, displayName, photoUrl)

fun UserModel.toUser(): User = User(uid, email, displayName, photoUrl)
