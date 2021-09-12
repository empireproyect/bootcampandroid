package app.company.sportpop.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import app.company.sportpop.core.error_handling.Failure
import app.company.sportpop.core.extensions.isValidEmail
import app.company.sportpop.core.extensions.isValidPassword
import app.company.sportpop.core.platform.BaseViewModel
import app.company.sportpop.core.preference.PreferenceManager
import app.company.sportpop.domain.entities.User
import app.company.sportpop.domain.use_case.auth.LoginUseCase
import app.company.sportpop.domain.use_case.auth.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val manager: PreferenceManager
): BaseViewModel() {

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event>
        get() = _event


    fun login(email: String, password: String) {
        if (email.isValidEmail() && password.isValidPassword()) {
            _event.value = Event.Loading
            loginUseCase(LoginUseCase.Params(email, password), viewModelScope) { it.fold(::handlerFailure, ::handlerResult) }
        } else {
            _event.value = Event.FieldError(email.isValidEmail(), password.isValidPassword())
        }
    }

    fun register(email: String, displayName: String, password: String) {
        if (email.isValidEmail() && password.isValidPassword() && displayName.isNotEmpty()) {
            _event.value = Event.Loading
            registerUseCase(RegisterUseCase.Params(displayName, email , password), viewModelScope) { it.fold(::handlerFailure, ::handlerResult) }
        } else {
            _event.value = Event.FieldError(email.isValidEmail(), password.isValidPassword(), displayName.isNotEmpty())
        }
    }

    override fun handlerFailure(failure: Failure) {
        super.handlerFailure(failure)
        _event.value = Event.Init
    }

    private fun handlerResult(user: User) {
        manager.setUser(user)
        _event.value = Event.RedirectHome
    }

    sealed class Event {
        object Init: Event()
        object Loading: Event()
        object RedirectHome: Event()
        data class FieldError(val isEmailValid: Boolean, val isPasswordValid: Boolean, val isDisplayName: Boolean? = null): Event()
    }
}
