package me.gulya.bitwarden.domain.data

data class MasterPasswordPolicyOptions(
    val minComplexity: Int = 0,
    val minLength: Int = 0,
    val requireUpper: Boolean = false,
    val requireLower: Boolean = false,
    val requireNumbers: Boolean = false,
    val requireSpecial: Boolean = false,
) {
    fun inEffect(): Boolean {
        return minComplexity > 0 || minLength > 0 || requireUpper || requireLower || requireNumbers || requireSpecial
    }
}