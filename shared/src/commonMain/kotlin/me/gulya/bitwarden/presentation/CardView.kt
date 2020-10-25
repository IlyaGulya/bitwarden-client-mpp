package me.gulya.bitwarden.presentation

class CardView(
    val cardholderName: String?,
    brand: String?,
    number: String?,
    val expMonth: String?,
    val expYear: String?,
    val code: String?,
    subTitle: String?,
) : View() {
    private var _brand: String? = brand
    private var _number: String? = number
    private var _subTitle: String? = subTitle

    val maskedCode: String?
        get() = code?.let { (0..(it.length)).joinToString { "•" } }
    var brand: String?
        get() = _brand
        set(value) {
            _brand = value
            _subTitle = null
        }
    var number: String?
        get() = _number
        set(value) {
            _number = value
            _subTitle = null
        }

    // Show last 5 on amex, last 4 for all others
    val subTitle: String?
        get() {
            if (_subTitle == null) {
                _subTitle = brand
                val number = number
                if (number != null && number.length >= 4) {
                    if (!_subTitle.isNullOrBlank()) {
                        _subTitle += ", "
                    } else {
                        _subTitle = ""
                    }
                    // Show last 5 on amex, last 4 for all others
                    // TODO Use more idiomatic way to express this.
                    val isAmex = number.length >= 5 && number.matches("^3[47]".toRegex())
                    val count =
                        if (isAmex) {
                            5
                        } else {
                            4
                        }
                    _subTitle += "*${number.substring(number.length - count)}"
                }
            }
            return _subTitle
        }

    val expiration: String?
        get() {
            val expMonth = expMonth
            val expYear = expYear
            return if (expMonth != null || expYear != null) {
                val expMo = expMonth?.padStart(2, '0') ?: "__"
                val expYr = expYear?.let { formatYear(expYear) } ?: "____"
                "$expMo / $expYr"
            } else {
                null
            }
        }

    private fun formatYear(year: String): String =
        if (year.length == 2) {
            "20$year"
        } else {
            year
        }
}