package app.company.sportpop.data.repository

import app.company.sportpop.core.connectivity.base.ConnectivityProvider
import app.company.sportpop.core.error_handling.Failure
import app.company.sportpop.core.error_handling.Failure.DBError
import app.company.sportpop.core.error_handling.Failure.NetworkError.NoConnection
import app.company.sportpop.core.functional.Either
import app.company.sportpop.core.functional.getOrElse
import app.company.sportpop.data.source.LocalDataSource
import app.company.sportpop.data.source.RemoteDataSource
import app.company.sportpop.domain.entities.User
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RepositoryImplTest {

    private val remoteDataSource = mockk<RemoteDataSource>()
    private val localDataSource = mockk<LocalDataSource>()
    private val connectivityProvider = mockk<ConnectivityProvider>()

    private lateinit var sut: RepositoryIml
    private lateinit var user: User

    @Before
    fun setup() {
        sut = RepositoryIml(remoteDataSource, localDataSource, connectivityProvider)
        user = User("1", "f@f.com", "Felix", "photo_url")
    }

    @Test
    fun `when login hasInternet is true then get user from remote and save data local`() {
        // Prepare
        every { connectivityProvider.isNetworkAvailable() } returns true
        coEvery { remoteDataSource.login(any(), any()) } returns Either.Right(user)
        coEvery { localDataSource.saveUser(user) } returns Unit

        // Result
        val result = runBlocking { sut.login("f@f.com", "123") }

        // Check
        coVerifyOrder {
            remoteDataSource.login(any(), any())
            localDataSource.saveUser(any())
        }
        assertEquals(user, result.getOrElse(null))
    }

    @Test
    fun `when login hasInternet is false then get user from local`() {
        // Prepare
        every { connectivityProvider.isNetworkAvailable() } returns false
        coEvery { localDataSource.findByEmailUser(any()) } returns user

        // Result
        val result = runBlocking { sut.login("f@f.com", "123") }

        // Check
        coVerify { localDataSource.findByEmailUser(any()) }
        verify { remoteDataSource wasNot Called }
        assertEquals(user, result.getOrElse(null))
    }

    @Test
    fun `when login error db then get user from local`() {
        // Prepare
        every { connectivityProvider.isNetworkAvailable() } returns false
        coEvery { localDataSource.findByEmailUser(any()) }.throws(Exception())

        // Result
        val result = runBlocking { sut.login("f@f.com", "123") }

        // Check
        assert(result.isLeft)
        result.fold(fnL = {
            assertEquals(it, DBError)
        }, {})
    }

    @Test
    fun `when register hasInternet is true then get user from remote and save data local`() {
        // Prepare
        every { connectivityProvider.isNetworkAvailable() } returns true
        coEvery { remoteDataSource.register(any(), any(), any()) } returns Either.Right(user)
        coEvery { localDataSource.saveUser(user) } returns Unit

        // Result
        val result = runBlocking { sut.register("f@f.com", "Felix", "123") }

        // Check
        coVerifyOrder {
            remoteDataSource.register(any(), any(), any())
            localDataSource.saveUser(any())
        }
        assertEquals(user, result.getOrElse(null))
    }

    @Test
    fun `when register hasInternet is false then error no connection`() {
        // Prepare
        every { connectivityProvider.isNetworkAvailable() } returns false

        // Result
        val result = runBlocking { sut.register("f@f.com", "Felix", "123") }

        // Check
        verify { remoteDataSource wasNot Called }
        verify { localDataSource wasNot Called }

        // Check
        assert(result.isLeft)
        result.fold(fnL = {
            assertEquals(it, NoConnection)
        }, {})

    }

}
