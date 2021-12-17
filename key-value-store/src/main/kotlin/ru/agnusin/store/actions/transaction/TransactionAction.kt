package ru.agnusin.store.actions.transaction

import ru.agnusin.store.core.Action
import ru.agnusin.store.core.Store
import ru.agnusin.store.core.TransactionManager

abstract class TransactionAction<out R>(args: List<String>): Action<Pair<TransactionManager, Store>, R>(args)