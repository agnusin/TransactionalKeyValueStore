import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.agnusin.store.HashMapStore
import ru.agnusin.store.KeyValueStore
import ru.agnusin.store.actions.transaction.BeginTransactionAction
import ru.agnusin.store.actions.transaction.CommitTransactionAction
import ru.agnusin.store.actions.transaction.RollbackTransactionAction
import ru.agnusin.store.core.Action

class TransactionActionsTest {

    private lateinit var keyValueStore: KeyValueStore

    @BeforeEach
    fun init() {
        keyValueStore = KeyValueStore(HashMapStore())
    }

    @Test
    fun Test_Begin_Transaction() {
        var a = BeginTransactionAction(emptyList())
        keyValueStore.process(a)
        Assertions.assertEquals(keyValueStore.transactionManager.stateStack.size, 1)

        a = BeginTransactionAction(listOf("foo"))
        val r = keyValueStore.process(a)
        Assertions.assertEquals(r, Action.Result.Error<String>("wrong number of arguments"))
    }

    @Test
    fun Test_Commit_Transaction() {
        var a: Action<*, *> = BeginTransactionAction(emptyList())
        keyValueStore.process(a)
        Assertions.assertEquals(keyValueStore.transactionManager.stateStack.size, 1)

        a = CommitTransactionAction(emptyList())
        keyValueStore.process(a)
        Assertions.assertEquals(keyValueStore.transactionManager.stateStack.size, 0)

        val r = keyValueStore.process(a)
        Assertions.assertEquals(r, Action.Result.Error<String>("no transaction"))
    }

    @Test
    fun Test_Rollback_Transaction() {
        var a: Action<*, *> = BeginTransactionAction(emptyList())
        keyValueStore.process(a)
        Assertions.assertEquals(keyValueStore.transactionManager.stateStack.size, 1)

        a = RollbackTransactionAction(emptyList())
        keyValueStore.process(a)
        Assertions.assertEquals(keyValueStore.transactionManager.stateStack.size, 0)

        val r = keyValueStore.process(a)
        Assertions.assertEquals(r, Action.Result.Error<String>("no transaction"))
    }
}