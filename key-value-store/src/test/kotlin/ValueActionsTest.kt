import org.junit.jupiter.api.*
import ru.agnusin.store.HashMapStore
import ru.agnusin.store.KeyValueStore
import ru.agnusin.store.actions.value.CountValuesAction
import ru.agnusin.store.actions.value.DeleteValueAction
import ru.agnusin.store.actions.value.GetValueAction
import ru.agnusin.store.actions.value.SetValueAction
import ru.agnusin.store.core.Store
import ru.agnusin.store.core.Action.Result

class ValueActionsTest {

    private lateinit var store: Store
    private lateinit var keyValueStore: KeyValueStore

    @BeforeEach
    fun init() {
        store = HashMapStore()
        keyValueStore = KeyValueStore(store)
    }

    @Test
    fun Test_Set_Action() {
        var a = SetValueAction(listOf("foo", "123"))
        keyValueStore.process(a)
        Assertions.assertEquals(store.get("foo")?.value, "123")

        a = SetValueAction(listOf("foo"))
        val r = keyValueStore.process(a)
        Assertions.assertEquals(r, Result.Error<String>("wrong number of arguments"))
    }

    @Test
    fun Test_Get_Action() {
        store.put(Store.Entry("foo", "123"))
        var a = GetValueAction(listOf("foo"))
        var r = keyValueStore.process(a)
        Assertions.assertEquals(r, Result.Success("123"))

        a = GetValueAction(emptyList())
        r = keyValueStore.process(a)
        Assertions.assertEquals(r, Result.Error<String>("wrong number of arguments"))

        a = GetValueAction(listOf("bar"))
        r = keyValueStore.process(a)
        Assertions.assertEquals(r, Result.Error<String>("key not set"))
    }

    @Test
    fun Test_Delete_Action() {
        store.put(Store.Entry("foo", "123"))
        var a = DeleteValueAction(listOf("foo"))
        keyValueStore.process(a)
        Assertions.assertNull(store.get("foo"))

        a = DeleteValueAction(emptyList())
        var r = keyValueStore.process(a)
        Assertions.assertEquals(r, Result.Error<String>("wrong number of arguments"))

        a = DeleteValueAction(listOf("bar"))
        r = keyValueStore.process(a)
        Assertions.assertEquals(r, Result.Error<String>("key not set"))
    }

    @Test
    fun Test_Count_Action() {
        store.put(Store.Entry("foo", "123"))
        var a = CountValuesAction(listOf("123"))
        var r = keyValueStore.process(a)
        Assertions.assertEquals(r, Result.Success(1))

        store.put(Store.Entry("bar", "123"))
        a = CountValuesAction(listOf("123"))
        r = keyValueStore.process(a)
        Assertions.assertEquals(r, Result.Success(2))

        a = CountValuesAction(listOf("456"))
        r = keyValueStore.process(a)
        Assertions.assertEquals(r, Result.Success(0))

        a = CountValuesAction(emptyList())
        r = keyValueStore.process(a)
        Assertions.assertEquals(r, Result.Error<String>("wrong number of arguments"))
    }
}