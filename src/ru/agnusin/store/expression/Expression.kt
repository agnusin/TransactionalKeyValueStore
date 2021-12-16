package ru.agnusin.store.expression

data class Expression(
    val commandLabel: String,
    val args: List<String> = emptyList()
)