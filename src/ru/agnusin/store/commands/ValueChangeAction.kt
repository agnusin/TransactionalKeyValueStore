package ru.agnusin.store.commands

import ru.agnusin.core.Action
import ru.agnusin.core.Store

interface ValueChangeAction<R>: Action<Store, R>