package app.company.sportpop.core.platform

import java.util.concurrent.atomic.AtomicBoolean

class Consumable<out T>(private val value: T) {

    private val isConsumed = AtomicBoolean(false)

    fun consume(action: (T) -> Unit) {
        if (isConsumed.compareAndSet(false, true)) {
            action(value)
        }
    }
}
