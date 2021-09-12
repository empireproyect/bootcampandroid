package app.company.sportpop.framework.local.room.base

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.company.sportpop.framework.local.room.model.UserModel

@Dao
interface RoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUser(user: UserModel)

    @Query("SELECT * FROM UserModel WHERE email = :email")
    fun findByEmailUser(email: String): UserModel

    @Query("DELETE FROM UserModel WHERE uid = :uid")
    fun deleteUser(uid: String)
}
