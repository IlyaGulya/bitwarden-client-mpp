package me.gulya.bitwarden.server.response

data class BreachAccountResponse(
    val addedDate: String?,
    val breachDate: String?,
    val dataClasses: List<String>?,
    val description: String?,
    val domain: String?,
    val isActive: Boolean,
    val isVerified: Boolean,
    val logoPath: String?,
    val modifiedDate: String?,
    val name: String?,
    val pwnCount: Int,
    val title: String?,
)