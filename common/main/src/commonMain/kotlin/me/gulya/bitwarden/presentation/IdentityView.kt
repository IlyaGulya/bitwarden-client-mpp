package me.gulya.bitwarden.presentation

data class IdentityView(
    val title: String?,
    val firstName: String?,
    val middleName: String?,
    val lastName: String?,
    val address1: String?,
    val address2: String?,
    val address3: String?,
    val city: String?,
    val state: String?,
    val postalCode: String?,
    val country: String?,
    val company: String?,
    val email: String?,
    val phone: String?,
    val sSN: String?,
    val username: String?,
    val passportNumber: String?,
    val licenseNumber: String?,
) {
    val subTitle: String by lazy {
        listOfNotNull(firstName, lastName).joinToString(" ")
    }

    val fullName: String? by lazy {
        listOfNotNull(
            title,
            firstName,
            middleName,
            lastName
        )
            .filter { it.isNotBlank() }
            .joinToString(" ")
            .trim()
            .takeIf { it.isNotBlank() }
    }

    val fullAddress: String by lazy {
        listOfNotNull(address1, address2, address3)
            .filter { it.isNotBlank() }
            .joinToString(", ")

    }

    val fullAddressPart2: String? by lazy {
        listOf(city, state, postalCode)
            .joinToString(", ") { it.takeIf { !it.isNullOrBlank() } ?: "-" }
            .takeIf { it.isNotBlank() }
    }
}