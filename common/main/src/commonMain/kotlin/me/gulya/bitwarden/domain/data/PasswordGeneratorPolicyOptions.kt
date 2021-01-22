package me.gulya.bitwarden.domain.data

data class PasswordGeneratorPolicyOptions(
    val defaultType: String = "",
    val minLength: Int = 0,
    val useUppercase: Boolean = false,
    val useLowercase: Boolean = false,
    val useNumbers: Boolean = false,
    val numberCount: Int = 0,
    val useSpecial: Boolean = false,
    val specialCount: Int = 0,
    val minNumberOfWords: Int = 0,
    val capitalize: Boolean = false,
    val includeNumber: Boolean = false,
) {
    fun hasEffect(): Boolean {
        return defaultType != "" || minLength > 0 || numberCount > 0 || specialCount > 0 || useUppercase || useLowercase || useNumbers || useSpecial || minNumberOfWords > 0 || capitalize || includeNumber
    }
}