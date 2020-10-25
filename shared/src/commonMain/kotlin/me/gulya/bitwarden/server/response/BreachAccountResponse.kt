package me.gulya.bitwarden.server.response

data class BreachAccountResponse(
    val addedDate: String?,
    val breachDate: String?,
    val dataClasses: List<String>?,
    val description: String?,
    val domain: String?,
    var isActive: Boolean,
    var isVerified: Boolean,
    val logoPath: String?,
    val modifiedDate: String?,
    val name: String?,
    var pwnCount: Int,
    val title: String?,
)