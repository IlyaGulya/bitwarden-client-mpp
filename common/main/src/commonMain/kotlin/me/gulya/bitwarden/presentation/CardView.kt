package me.gulya.bitwarden.presentation

class CardView(
    val cardholderName: String?,
    val brand: String?,
    val number: String?,
    val expMonth: String?,
    val expYear: String?,
    val code: String?,
) {

    val maskedCode: String?
        get() = code?.let { (0..(it.length)).joinToString { "•" } }

    val subTitle: String? by lazy {
        val number = number ?: ""
        val isAmex = number.length >= 5 && number.matches("^3[47]".toRegex())
        val digitAmount = if (isAmex) 5 else 4
        val preparedNumber = number.takeLast(digitAmount)
        val titleAndNumber =
            listOfNotNull(brand, preparedNumber)
                .asSequence()
                .filter { it.isNotBlank() }
                .joinToString(", *")

        titleAndNumber
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