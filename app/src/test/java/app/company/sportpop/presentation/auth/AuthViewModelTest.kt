package app.company.sportpop.presentation.auth

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.company.sportpop.CoroutinesTestRule
import app.company.sportpop.core.functional.Either
import app.company.sportpop.core.preference.PreferenceManager
import app.company.sportpop.domain.entities.User
import app.company.sportpop.domain.repository.Repository
import app.company.sportpop.domain.use_case.auth.LoginUseCase
import app.company.sportpop.domain.use_case.auth.RegisterUseCase
import app.company.sportpop.presentation.auth.AuthViewModel.Event.FieldError
import app.company.sportpop.presentation.auth.AuthViewModel.Event.RedirectHome
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class AuthViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    private lateinit var sut: AuthViewModel

    private val loginUseCase = spyk(LoginUseCase(mockk(Repository::class.java.name)))
    private val registerUseCase = spyk(RegisterUseCase(mockk(Repository::class.java.name)))
    private val manager = mockk<PreferenceManager>()
    private lateinit var user: User

    @Before
    fun setup() {
        sut = AuthViewModel(loginUseCase, registerUseCase, manager)
        user = User("1", "f@f.com", "Felix", "photo_url")
    }

    @Test
    fun `when call login and error params shoots event FieldError`() {
        // Result
        sut.login("ff", "111")

        // Check
        sut.event.observeForever {
            when(it) {
                is FieldError ->{
                    assertFalse(it.isEmailValid)
                    assertFalse(it.isPasswordValid)
                }
                else -> assertTrue(false)
            }
        }
    }

    @Test
    fun `when call loginUserCase shoots event RedirectHome`() {
        // Prepare
        coEvery { loginUseCase.run(any()) } returns Either.Right(user)
        every { manager.setUser(user) } returns Unit

        // Result
        sut.login("lujan@gmail.com", "fF@12345678")

        // Check
        sut.event.observeForever {
            when(it) {
                is RedirectHome -> assertTrue(true)
                else -> assertTrue(false)
            }
        }
    }

    @Test
    fun `when call register and error params shoots event FieldError`() {
        // Result
        sut.register("ff", "", "111")

        // Check
        sut.event.observeForever {
            when(it) {
                is FieldError ->{
                    assertFalse(it.isEmailValid)
                    assertFalse(it.isPasswordValid)
                    assertFalse(it.isDisplayName ?: true)
                }
                else -> assertTrue(false)
            }
        }
    }

    @Test
    fun `when call registerUserCase shoots event RedirectHome`() {
        // Prepare
        coEvery { registerUseCase.run(any()) } returns Either.Right(user)
        every { manager.setUser(user) } returns Unit

        // Result
        sut.register("lujan@gmail.com", "Felix", "fF@12345678")

        // Check
        sut.event.observeForever {
            when(it) {
                is RedirectHome -> assertTrue(true)
                else -> assertTrue(false)
            }
        }
    }
}
