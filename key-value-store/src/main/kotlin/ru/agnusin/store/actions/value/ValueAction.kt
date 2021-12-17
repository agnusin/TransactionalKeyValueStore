package ru.agnusin.store.actions.value

import ru.agnusin.store.core.Action
import ru.agnusin.store.core.Store

abstract class ValueAction<out R>(args: List<String>): Action<Store, R>(args)