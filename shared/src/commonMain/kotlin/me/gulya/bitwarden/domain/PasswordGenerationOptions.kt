package me.gulya.bitwarden.domain

data class PasswordGenerationOptions(
    val length: Int?,
    val ambiguous: Boolean?,
    val number: Boolean?,
    val minNumber: Int?,
    val uppercase: Boolean?,
    val minUppercase: Int?,
    val lowercase: Boolean?,
    val minLowercase: Int?,
    val special: Boolean?,
    val minSpecial: Int?,
    val type: String?,
    val numWords: Int?,
    val wordSeparator: String?,
    val capitalize: Boolean?,
    val includeNumber: Boolean?,
) {
    constructor(defaultOptions: Boolean) : this(
        length = 14.takeIf { defaultOptions },
        ambiguous = false.takeIf { defaultOptions },
        number = true.takeIf { defaultOptions },
        minNumber = 1.takeIf { defaultOptions },
        uppercase = true.takeIf { defaultOptions },
        minUppercase = 0.takeIf { defaultOptions },
        lowercase = true.takeIf { defaultOptions },
        minLowercase = 0.takeIf { defaultOptions },
        special = false.takeIf { defaultOptions },
        minSpecial = 1.takeIf { defaultOptions },
        type = "password".takeIf { defaultOptions },
        numWords = 3.takeIf { defaultOptions },
        wordSeparator = "-".takeIf { defaultOptions },
        capitalize = false.takeIf { defaultOptions },
        includeNumber = false.takeIf { defaultOptions },
    )

}