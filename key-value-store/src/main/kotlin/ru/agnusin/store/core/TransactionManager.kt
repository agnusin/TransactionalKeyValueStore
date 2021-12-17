package ru.agnusin.store.core

import java.util.*

class TransactionManager(
    private val changeStore: (s: Store) -> Unit
) {

    internal val stateStack = ArrayDeque<State>()

    fun beginTransaction(store: Store) {
        val state = State(store.copy())
        stateStack.push(state)
    }

    @Throws(IllegalStateException::class)
    fun commitTransaction() {
        checkStack(stateStack.size > 0)
        stateStack.pop()
    }

    @Throws(IllegalStateException::class)
    fun rollbackTransaction() {
        checkStack(stateStack.size > 0)
        val state = stateStack.pop()
        changeStore(state.store)
    }

    private fun checkStack(check: Boolean) {
        if (!check) throw IllegalStateException()
    }

    class State (val store: Store)
}