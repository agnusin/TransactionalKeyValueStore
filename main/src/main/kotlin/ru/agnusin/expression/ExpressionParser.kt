package ru.agnusin.expression

object ExpressionParser {

    fun parse(line: String): Expression {
        val commandAndArguments = line.split(" ", limit = 2)
        return if (commandAndArguments.size > 1) {
            val args = commandAndArguments[1].split(" ").filter { it.isNotBlank() }
            Expression(commandAndArguments[0], args)
        } else Expression(commandAndArguments[0])
    }
}