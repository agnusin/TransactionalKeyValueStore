package ru.agnusin.store.actions.transaction

import ru.agnusin.store.core.Store
import ru.agnusin.store.core.TransactionManager

class BeginTransactionAction(args: List<String>): TransactionAction<Unit>(args) {

    override fun exec(t: Pair<TransactionManager, Store>): Result<Unit> {
        return checkArgumentsAndRun(args.isEmpty()) {
            t.first.beginTransaction(t.second)
            Result.Success(Unit)
        }
    }
}
