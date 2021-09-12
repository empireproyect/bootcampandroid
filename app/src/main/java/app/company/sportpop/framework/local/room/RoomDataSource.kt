package app.company.sportpop.framework.local.room

import app.company.sportpop.data.source.LocalDataSource
import app.company.sportpop.domain.entities.User
import app.company.sportpop.framework.local.room.base.RoomDao
import app.company.sportpop.framework.local.room.mapper.toRoom
import app.company.sportpop.framework.local.room.mapper.toUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoomDataSource @Inject constructor(
    private val roomDao: RoomDao
): LocalDataSource {
    override suspend fun saveUser(user: User) = withContext(Dispatchers.IO) { roomDao.saveUser(user.toRoom()) }

    override suspend fun findByEmailUser(email: String) = withContext(Dispatchers.IO) { roomDao.findByEmailUser(email).toUser() }

    override suspend fun deleteUser(uid: String) = withContext(Dispatchers.IO) { roomDao.deleteUser(uid) }
}
