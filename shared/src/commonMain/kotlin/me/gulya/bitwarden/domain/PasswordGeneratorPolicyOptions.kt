package me.gulya.bitwarden.domain

class PasswordGeneratorPolicyOptions {
    var defaultType = ""
    var minLength = 0
    var useUppercase = false
    var useLowercase = false
    var useNumbers = false
    var numberCount = 0
    var useSpecial = false
    var specialCount = 0
    var minNumberOfWords = 0
    var capitalize = false
    var includeNumber = false
    fun InEffect(): Boolean {
        return defaultType != "" || minLength > 0 || numberCount > 0 || specialCount > 0 || useUppercase || useLowercase || useNumbers || useSpecial || minNumberOfWords > 0 || capitalize || includeNumber
    }
}