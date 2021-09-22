package app.company.sportpop.presentation.main.home

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import app.company.sportpop.core.platform.BaseViewModel
import app.company.sportpop.core.platform.Consumable
import app.company.sportpop.core.preference.PreferenceManager
import app.company.sportpop.domain.entities.Product
import app.company.sportpop.domain.use_case.main.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCase: HomeUseCase,
    private val manager: PreferenceManager
): BaseViewModel() {

    private val _event = MutableLiveData<Consumable<Event>>()
    val event: LiveData<Consumable<Event>>
        get() = _event

    private var listProduct = mutableListOf<Product>()
    var product: Product? = null
    var listState: Parcelable? = null

    fun getProducts() {
        triggerEvent(Event.Loading)
        homeUseCase(HomeUseCase.Params(), viewModelScope) { it.fold(::handlerFailure, ::handlerResult) }
    }

    fun isLoggedIn() = manager.isLoggedIn()


    private fun handlerResult(products: List<Product>) {
        listProduct.addAll(products)
        triggerEvent(Event.Content(products))
    }

    fun onProductClicked(product: Product) {
        this.product = product
        triggerEvent(Event.Detail)
    }

    private fun triggerEvent(event: Event) {
        _event.value = Consumable(event)
    }

    fun reloadLocalData() = triggerEvent(Event.Content(listProduct))

    sealed class Event {
        object Loading: Event()
        object Detail: Event()
        data class Content(val products: List<Product>) : Event()
    }
}
