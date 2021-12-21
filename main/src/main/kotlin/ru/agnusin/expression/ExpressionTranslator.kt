package ru.agnusin.expression

import ru.agnusin.store.KeyValueStore
import ru.agnusin.store.actions.value.CountValuesAction
import ru.agnusin.store.actions.value.DeleteValueAction
import ru.agnusin.store.actions.value.GetValueAction
import ru.agnusin.store.actions.value.SetValueAction
import ru.agnusin.store.core.Action
import ru.agnusin.store.actions.transaction.BeginTransactionAction
import ru.agnusin.store.actions.transaction.CommitTransactionAction
import ru.agnusin.store.actions.transaction.RollbackTransactionAction

class ExpressionTranslator(
    private val keyValueStore: KeyValueStore
) {

    enum class Command(val builder: (Expression) -> Action<*, *>) {
        SET({ SetValueAction(it.args) }),
        GET({ GetValueAction(it.args) }),
        DELETE({ DeleteValueAction(it.args) }),
        COUNT({ CountValuesAction(it.args) }),
        BEGIN({ BeginTransactionAction(it.args) }),
        COMMIT({ CommitTransactionAction(it.args) }),
        ROLLBACK({ RollbackTransactionAction(it.args) })
    }

    fun translate(exp: Expression): String? {
        return try {
            val action = Command.valueOf(exp.commandLabel).builder(exp)
            keyValueStore.process(action).asString()
        } catch (e: IllegalArgumentException) {
            "unsupported operation"
        }
    }

    private fun <T> Action.Result<T>.asString(): String? {
        return when (this) {
            is Action.Result.Success -> {
                return when (data) {
                    is Unit -> null
                    else -> data.toString()
                }
            }
            is Action.Result.Error -> msg
        }
    }
}