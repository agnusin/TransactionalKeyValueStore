package ru.agnusin.store

import ru.agnusin.store.actions.ValueAction
import ru.agnusin.store.core.Action
import ru.agnusin.store.core.Store
import ru.agnusin.store.transactions.TransactionManager
import ru.agnusin.store.transactions.actions.TransactionAction

class KeyValueStore(
    private var store: Store
) {

    private val transactionManager = TransactionManager { s ->
        store = s
    }

    fun <T, R> process(action: Action<T, R>): Action.Result<R> {
        return when (action) {
            is ValueAction -> action.exec(store)
            is TransactionAction -> action.exec(transactionManager to store)
            else -> throw RuntimeException("Unsupported action type")
        }
    }
}