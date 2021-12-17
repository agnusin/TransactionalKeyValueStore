package ru.agnusin.store.actions.transaction

import ru.agnusin.store.core.Store
import ru.agnusin.store.core.TransactionManager

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