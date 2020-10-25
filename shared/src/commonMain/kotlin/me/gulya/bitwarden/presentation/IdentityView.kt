package me.gulya.bitwarden.presentation

class IdentityView(
    var title: String?,
    firstName: String?,
    var middleName: String?,
    lastName: String?,
    var address1: String?,
    var address2: String?,
    var address3: String?,
    var city: String?,
    var state: String?,
    var postalCode: String?,
    var country: String?,
    var company: String?,
    var email: String?,
    var phone: String?,
    var sSN: String?,
    var username: String?,
    var passportNumber: String?,
    var licenseNumber: String?,
) : View() {
    private var _firstName: String? = firstName
    private var _lastName: String? = lastName
    private var _subTitle: String? = null

    var firstName: String?
        get() = _firstName
        set(value) {
            _firstName = value
            _subTitle = null
        }
    var lastName: String?
        get() = _lastName
        set(value) {
            _lastName = value
            _subTitle = null
        }

    val subTitle: String?
        get() {
            if (_subTitle == null && (firstName != null || lastName != null)) {
                _subTitle = ""
                if (firstName != null) {
                    _subTitle = firstName
                }
                if (lastName != null) {
                    if (_subTitle != "") {
                        _subTitle += " "
                    }
                    _subTitle += lastName
                }
            }
            return _subTitle
        }

    val fullName: String?
        get() {
            val hasTitle = !title.isNullOrBlank()
            val hasFirstName = !firstName.isNullOrBlank()
            val hasMiddleName = !middleName.isNullOrBlank()
            val hasLastName = !lastName.isNullOrBlank()
            if (hasTitle || hasFirstName || hasMiddleName || hasLastName) {
                return listOfNotNull(
                    title,
                    firstName,
                    middleName,
                    lastName
                ).joinToString(" ").trim()
            }
            return null
        }

    val fullAddress: String?
        get() = listOfNotNull(address1, address2, address3)
            .filter { it.isNotBlank() }
            .joinToString(", ")

    val fullAddressPart2: String?
        get() = listOf(city, state, postalCode)
            .joinToString(", ") { it.takeIf { !it.isNullOrBlank() } ?: "-" }
            .takeIf { !it.isBlank() }
}