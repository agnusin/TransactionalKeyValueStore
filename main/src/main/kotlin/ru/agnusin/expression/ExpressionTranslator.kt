package ru.agnusin.expression

import ru.agnusin.store.KeyValueStore
import ru.agnusin.store.actions.CountValuesAction
import ru.agnusin.store.actions.DeleteValueAction
import ru.agnusin.store.actions.GetValueAction
import ru.agnusin.store.actions.SetValueAction
import ru.agnusin.store.core.Action
import ru.agnusin.store.transactions.actions.BeginTransactionAction
import ru.agnusin.store.transactions.actions.CommitTransactionAction
import ru.agnusin.store.transactions.actions.RollbackTransactionAction

class ExpressionTranslator(
    private val keyValueStore: KeyValueStore
){

    fun translate(exp: Expression): String? {
        val action = when (exp.commandLabel) {
            "SET" -> SetValueAction(exp.args)
            "GET" -> GetValueAction(exp.args)
            "DELETE" -> DeleteValueAction(exp.args)
            "COUNT" -> CountValuesAction(exp.args)
            "BEGIN" -> BeginTransactionAction(exp.args)
            "COMMIT" -> CommitTransactionAction(exp.args)
            "ROLLBACK" -> RollbackTransactionAction(exp.args)
            else -> null
        }

        return if (action != null) {
            keyValueStore.process(action).asString()
        } else "unsupported operation"
    }

    private fun <T> Action.Result<T>.asString(): String? {
        return when (this) {
            is Action.Result.Success -> {
                return when(data) {
                    is Unit -> null
                    else -> data.toString()
                }
            }
            is Action.Result.Error -> msg
        }
    }

}