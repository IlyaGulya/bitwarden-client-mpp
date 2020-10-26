package me.gulya.bitwarden.domain.data

class MasterPasswordPolicyOptions {
    var minComplexity = 0
    var minLength = 0
    var requireUpper = false
    var requireLower = false
    var requireNumbers = false
    var requireSpecial = false
    fun inEffect(): Boolean {
        return minComplexity > 0 || minLength > 0 || requireUpper || requireLower || requireNumbers || requireSpecial
    }
}