package app.company.sportpop.presentation.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.company.sportpop.core.platform.BaseViewModel
import app.company.sportpop.core.preference.PreferenceManager
import app.company.sportpop.presentation.main.home.HomeViewModel.Event.RedirectLogin
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val manager: PreferenceManager
): BaseViewModel() {
    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event>
        get() = _event


    fun isLoggedIn() {
        if (!manager.isLoggedIn()) {
            _event.value = RedirectLogin
        }
    }

    sealed class Event {
        object RedirectLogin: Event()
    }
}
