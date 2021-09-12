package app.company.sportpop.core.di

import android.content.Context
import app.company.sportpop.core.connectivity.base.ConnectivityProvider
import app.company.sportpop.core.preference.PreferenceManager
import app.company.sportpop.core.preference.PreferenceRepository
import app.company.sportpop.core.preference.firebase.PreferenceFirebase
import app.company.sportpop.data.repository.RepositoryIml
import app.company.sportpop.data.source.LocalDataSource
import app.company.sportpop.data.source.RemoteDataSource
import app.company.sportpop.domain.repository.Repository
import app.company.sportpop.domain.use_case.auth.LoginUseCase
import app.company.sportpop.domain.use_case.auth.RegisterUseCase
import app.company.sportpop.framework.local.room.RoomDataSource
import app.company.sportpop.framework.local.room.base.RoomDao
import app.company.sportpop.framework.local.room.base.ConfigDatabase
import app.company.sportpop.framework.remote.NetworkApi
import app.company.sportpop.framework.remote.RemoteDataSourceImpl
import app.company.sportpop.framework.remote.firebase.FirebaseRepository
import app.company.sportpop.framework.remote.firebase.mapper.FirebaseUserMapperUserJson
import app.company.sportpop.framework.remote.mapper.UserJsonToMapperUser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ConnectivityModule {
    @Provides
    @Singleton
    fun connectivityProvider(@ApplicationContext context: Context): ConnectivityProvider =
        ConnectivityProvider.createProvider(context)
}

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun networkApiProvider(mapperUser: FirebaseUserMapperUserJson): NetworkApi = FirebaseRepository(mapperUser)
}


@Module
@InstallIn(SingletonComponent::class)
class MappersModule {
    @Provides
    @Singleton
    fun firebaseMapper(): FirebaseUserMapperUserJson = FirebaseUserMapperUserJson()

    @Provides
    @Singleton
    fun userJsonMapper(): UserJsonToMapperUser = UserJsonToMapperUser()
}

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    @Singleton
    fun repositoryProvider(remoteDataSource: RemoteDataSource, localDataSource: LocalDataSource, connectivityProvider: ConnectivityProvider): Repository =
        RepositoryIml(remoteDataSource, localDataSource, connectivityProvider)
}

@Module
@InstallIn(SingletonComponent::class)
class UseCasesModule {
    @Provides
    @Singleton
    fun loginUseCaseProvider(repository: Repository): LoginUseCase = LoginUseCase(repository)

    @Provides
    @Singleton
    fun registerUseCaseProvider(repository: Repository): RegisterUseCase = RegisterUseCase(repository)
}

@Module
@InstallIn(SingletonComponent::class)
object FrameworkModule {
    @Provides
    @Singleton
    fun remoteDataSourceProvider(networkApi: NetworkApi, mapper: UserJsonToMapperUser): RemoteDataSource =
        RemoteDataSourceImpl(networkApi, mapper)

    @Provides
    @Singleton
    fun localDataSourceProvider(roomDao: RoomDao): LocalDataSource = RoomDataSource(roomDao)

    @Provides
    @Singleton
    fun rooDataBaseProvider(@ApplicationContext context: Context): ConfigDatabase = ConfigDatabase.build(context)

    @Provides
    fun roomDaoProvider(configDatabase: ConfigDatabase): RoomDao = configDatabase.roomDao()

}

@Module
@InstallIn(SingletonComponent::class)
object PreferenceModule {
    @Provides
    @Singleton
    fun preferenceRepositoryProvider(): PreferenceRepository = PreferenceFirebase()

    @Provides
    @Singleton
    fun preferenceManagerProvider(repository: PreferenceRepository): PreferenceManager = PreferenceManager(repository)

}
