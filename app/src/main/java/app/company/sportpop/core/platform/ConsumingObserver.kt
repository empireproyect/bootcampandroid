package app.company.sportpop.core.platform

import androidx.lifecycle.Observer


class ConsumingObserver<T>(private val action: (T) -> Unit) : Observer<Consumable<T>> {

    override fun onChanged(consumable: Consumable<T>?) {
        consumable?.consume { action(it) }
    }
}
