package ru.agnusin.expression

data class Expression(
    val commandLabel: String,
    val args: List<String> = emptyList()
)