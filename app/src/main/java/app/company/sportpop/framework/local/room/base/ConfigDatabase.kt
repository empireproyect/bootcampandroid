package app.company.sportpop.framework.local.room.base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import app.company.sportpop.framework.local.room.model.UserModel

@Database(entities = [UserModel::class], version = 1)
abstract class ConfigDatabase: RoomDatabase() {

    abstract fun roomDao(): RoomDao

    companion object {
        fun build(context: Context) = Room.databaseBuilder(context, ConfigDatabase::class.java, "example_db").build()
    }
}
