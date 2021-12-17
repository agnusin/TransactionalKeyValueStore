package ru.agnusin.store.actions.value

import ru.agnusin.store.core.Store


class DeleteValueAction(args: List<String>) : ValueAction<Unit>(args) {

    override fun exec(t: Store): Result<Unit> {
        return checkArgumentsAndRun(args.size == 1) {
            val key = args[0]
            t.remove(key)
                ?.let { Result.Success(Unit) }
                ?: Result.Error<Unit>("key not set")
        }
    }
}