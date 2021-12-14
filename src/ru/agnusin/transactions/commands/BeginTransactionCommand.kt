package ru.agnusin.transactions.commands

import ru.agnusin.core.Action
import ru.agnusin.core.Command
import ru.agnusin.transactions.TransactionManager

class BeginTransactionCommand: Command<TransactionManager, Unit> {
    override val label: String
        get() = "BEGIN"

    override fun getAction(vararg args: String): TransactionChangeAction<Unit> {
        checkArguments(args.isEmpty())

        return object : TransactionChangeAction<Unit> {
            override fun exec(t: TransactionManager): Action.Result<Unit> {
                t.beginTransaction()
                return Action.Result.Success(Unit)
            }
        }
    }
}