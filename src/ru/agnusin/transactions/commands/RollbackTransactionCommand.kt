package ru.agnusin.transactions.commands

import ru.agnusin.core.Action
import ru.agnusin.core.Command
import ru.agnusin.transactions.TransactionManager

class RollbackTransactionCommand: Command<TransactionManager, Unit> {
    override val label: String
        get() = "ROLLBACK"

    override fun getAction(vararg args: String): TransactionChangeAction<Unit> {
        checkArguments(args.isEmpty())

        return object : TransactionChangeAction<Unit> {
            override fun exec(t: TransactionManager): Action.Result<Unit> {
                return try {
                    t.rollbackTransaction()
                    Action.Result.Success(Unit)
                } catch (e: IllegalStateException) {
                    Action.Result.Error("no transaction")
                }
            }
        }
    }
}