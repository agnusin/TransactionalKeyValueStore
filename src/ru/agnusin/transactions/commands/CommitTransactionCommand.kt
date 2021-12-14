package ru.agnusin.transactions.commands

import ru.agnusin.core.Action
import ru.agnusin.core.Command
import ru.agnusin.transactions.TransactionManager

class CommitTransactionCommand: Command<TransactionManager, Unit> {
    override val label: String
        get() = "COMMIT"

    override fun getAction(vararg args: String): TransactionChangeAction<Unit> {
        checkArguments(args.isEmpty())

        return object : TransactionChangeAction<Unit> {
            override fun exec(t: TransactionManager): Action.Result<Unit> {
                return try {
                    t.commitTransaction()
                    Action.Result.Success(Unit)
                } catch (e: IllegalStateException) {
                    Action.Result.Error("no transaction")
                }
            }
        }
    }
}