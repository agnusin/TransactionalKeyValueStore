package ru.agnusin

import ru.agnusin.store.HashMapStore
import ru.agnusin.store.KeyValueStore
import ru.agnusin.expression.ExpressionParser
import ru.agnusin.expression.ExpressionTranslator


fun main() {
    val translator = ExpressionTranslator(KeyValueStore(HashMapStore()))
    println("Welcome to Transactional Key Value Store")
    while (true) {
        readLine()?.let { line ->
            val expression = ExpressionParser.parse(line)
            translator.translate(expression)
                ?.let { println(it) }
        }
    }
}