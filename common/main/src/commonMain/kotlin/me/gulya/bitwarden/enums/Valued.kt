package me.gulya.bitwarden.enums

interface Valued<ValueType> {
    val value: ValueType
}

fun <T : Valued<ValueType>, ValueType> Array<T>.findValue(toFind: ValueType): T = this.first { it.value == toFind }