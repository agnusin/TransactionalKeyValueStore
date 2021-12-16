package ru.agnusin.store.transactions.actions

import ru.agnusin.store.core.Store
import ru.agnusin.store.transactions.TransactionManager

class RollbackTransactionAction(args: List<String>): TransactionAction<Unit>(args) {

    override fun exec(t: Pair<TransactionManager, Store>): Result<Unit> {
        return checkArgumentsAndRun(args.isEmpty()) {
            try {
                t.first.rollbackTransaction()
                Result.Success(Unit)
            } catch (e: IllegalStateException) {
                Result.Error<Unit>("no transaction")
            }
        }
    }
}