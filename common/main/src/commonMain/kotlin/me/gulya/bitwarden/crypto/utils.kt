package me.gulya.bitwarden.crypto

// Some users like to copy/paste passwords from external files. Sometimes this can lead to two different
// values on mobiles apps vs the web. For example, on Android an EditText will accept a new line character
// (\n), whereas whenever you paste a new line character on the web in a HTML input box it is converted
// to a space ( ). Normalize those values so that they are the same on all platforms.
fun normalizePassword(password: String): String {
    return password
        .replace("\r\n", " ") // Windows-style new line => space
        .replace("\n", " ") // New line => space
        .replace(" ", " ") // No-break space (00A0) => space
}
