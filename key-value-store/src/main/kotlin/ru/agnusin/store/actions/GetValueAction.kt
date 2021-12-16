package ru.agnusin.store.actions

import ru.agnusin.store.core.Store


class GetValueAction(args: List<String>) : ValueAction<String>(args) {

    override fun exec(t: Store): Result<String> {
        return checkArgumentsAndRun(args.size == 1) {
            val key = args[0]
            t.get(key)
                ?.let { e -> Result.Success(e.value) }
                ?: Result.Error<String>("key not set")
        }
    }
}