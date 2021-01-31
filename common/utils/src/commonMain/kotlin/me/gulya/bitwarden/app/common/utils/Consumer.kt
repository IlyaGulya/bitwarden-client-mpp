package me.gulya.bitwarden.app.common.utils

fun interface Consumer<T> {
    operator fun invoke(value: T)
}