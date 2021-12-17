import org.junit.jupiter.api.*
import ru.agnusin.expression.ExpressionParser
import ru.agnusin.expression.ExpressionTranslator
import ru.agnusin.store.HashMapStore
import ru.agnusin.store.KeyValueStore

class ExpressionsTest {

    private lateinit var translator: ExpressionTranslator

    @BeforeEach
    fun init() {
        translator = ExpressionTranslator(KeyValueStore(HashMapStore()))
    }

    @Test
    fun Test_SET_and_GET_Value() {
        "SET foo 123".run()
        val r = "GET foo".run()
        Assertions.assertEquals(r, "123")
    }

    @Test
    fun Test_DELETE_Value() {
        "SET foo 123".run()
        "DELETE foo".run()
        val r = "GET foo".run()
        Assertions.assertEquals(r, "key not set")
    }

    @Test
    fun Test_COUNT_Value() {
        "SET foo 123".run()
        "SET bar 456".run()
        "SET baz 123".run()
        var r = "COUNT 123".run()
        Assertions.assertEquals(r, "2")

        r = "COUNT 456".run()
        Assertions.assertEquals(r, "1")
    }

    @Test
    fun Test_COMMIT_Transaction() {
        "BEGIN".run()
        "SET foo 456".run()
        "COMMIT".run()
        var r = "ROLLBACK".run()
        Assertions.assertEquals(r, "no transaction")

        r = "GET foo".run()
        Assertions.assertEquals(r, "456")
    }

    @Test
    fun Test_ROLLBACK_Transaction() {
        "SET foo 123".run()
        "SET bar abc".run()
        "BEGIN".run()
        "SET foo 456".run()
        var r = "GET foo".run()
        Assertions.assertEquals(r, "456")

        "SET bar def".run()
        r = "GET bar".run()
        Assertions.assertEquals(r, "def")

        "ROLLBACK".run()
        r = "GET foo".run()
        Assertions.assertEquals(r, "123")

        r = "GET bar".run()
        Assertions.assertEquals(r, "abc")

        r = "COMMIT".run()
        Assertions.assertEquals(r, "no transaction")
    }

    @Test
    fun Test_Nested_Transactions() {
        "SET foo 123".run()
        "BEGIN".run()
        "SET foo 456".run()
        "BEGIN".run()
        "SET foo 789".run()
        var r = "GET foo".run()
        Assertions.assertEquals(r, "789")

        "ROLLBACK".run()
        r = "GET foo".run()
        Assertions.assertEquals(r, "456")

        "ROLLBACK".run()
        r = "GET foo".run()
        Assertions.assertEquals(r, "123")
    }

    @Test
    fun Test_Wrong_Arguments() {
        val msg = "wrong number of arguments"

        var r = "SET a".run()
        Assertions.assertEquals(r, msg)

        r = "GET".run()
        Assertions.assertEquals(r, msg)

        r = "GET a 4 4".run()
        Assertions.assertEquals(r, msg)

        r = "DELETE a 4".run()
        Assertions.assertEquals(r, msg)

        r = "COUNT".run()
        Assertions.assertEquals(r, msg)

        r = "COUNT 3 4".run()
        Assertions.assertEquals(r, msg)

        r = "BEGIN 1".run()
        Assertions.assertEquals(r, msg)

        r = "COMMIT 1".run()
        Assertions.assertEquals(r, msg)

        r = "ROLLBACK 1".run()
        Assertions.assertEquals(r, msg)
    }


    private fun String.run(): String? {
        return translator.translate(ExpressionParser.parse(this))
    }
}