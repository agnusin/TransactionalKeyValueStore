package ru.agnusin.store.transactions.actions

import ru.agnusin.store.core.Action
import ru.agnusin.store.core.Store
import ru.agnusin.store.transactions.TransactionManager

abstract class TransactionAction<out R>(args: List<String>): Action<Pair<TransactionManager, Store>, R>(args)