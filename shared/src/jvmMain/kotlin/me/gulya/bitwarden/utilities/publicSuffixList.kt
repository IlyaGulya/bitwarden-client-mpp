package me.gulya.bitwarden.utilities

actual fun publicSuffixList(): List<String> {
    return ClassLoader.getPlatformClassLoader().getResource("public_suffix_list.dat").readText(Charsets.UTF_8).split("\n")
}