package ru.agnusin.transactions.commands

import ru.agnusin.core.Action
import ru.agnusin.transactions.TransactionManager

interface TransactionChangeAction<R>: Action<TransactionManager, R>