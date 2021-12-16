package ru.agnusin.store.actions

import ru.agnusin.store.core.Store


class CountValuesAction(args: List<String>): ValueAction<Int>(args) {

    override fun exec(t: Store): Result<Int> {
        return checkArgumentsAndRun(args.size == 1) {
            val value = args[0]
            val count = t.values().count { it.value == value }
            Result.Success(count)
        }
    }
}
