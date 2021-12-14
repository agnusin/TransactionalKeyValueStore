package ru.agnusin.transactions

import ru.agnusin.core.Action
import ru.agnusin.core.Store
import ru.agnusin.store.commands.ValueChangeAction
import ru.agnusin.transactions.commands.BeginTransactionCommand
import ru.agnusin.transactions.commands.CommitTransactionCommand
import ru.agnusin.transactions.commands.RollbackTransactionCommand
import ru.agnusin.transactions.commands.TransactionChangeAction
import java.util.*

class TransactionManager(store: Store) {

    private val storeStack = ArrayDeque<Store>()
        .apply { addFirst(store) }

    val supportedCommandSet = setOf(
        BeginTransactionCommand(),
        CommitTransactionCommand(),
        RollbackTransactionCommand()
    )

    fun <T, R> execAction(a: Action<T, R>): Action.Result<R> {
        return when (a) {
            is TransactionChangeAction -> {
                a.exec(this)
            }
            is ValueChangeAction -> {
                a.exec(getStore())
            }
            else -> throw RuntimeException("Unsupported action")
        }
    }

    private fun getStore() = storeStack.first

    fun beginTransaction() {
        val newCopyStore = storeStack.first.copy()
        storeStack.push(newCopyStore)
    }

    fun commitTransaction() {
        checkStack(storeStack.size > 1)

        val currentStore = storeStack.pop()
        storeStack.pop()
        storeStack.push(currentStore)
    }

    fun rollbackTransaction() {
        checkStack(storeStack.size > 1)
        storeStack.pop()
    }

    private fun checkStack(check: Boolean) {
        if (!check) throw IllegalStateException()
    }
}