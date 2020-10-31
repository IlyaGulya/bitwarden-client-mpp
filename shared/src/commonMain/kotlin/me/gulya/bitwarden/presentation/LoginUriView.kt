package me.gulya.bitwarden.presentation

import io.ktor.http.*
import me.gulya.bitwarden.enums.UriMatchType
import me.gulya.bitwarden.utilities.DomainName

val TLD_ENDING_REGEX = ".*\\.(com|net|org|edu|uk|gov|ca|de|jp|fr|au|ru|ch|io|es|us|co|xyz|info|ly|mil)$".toRegex()
val IP_REGEX = ("^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
        "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
        "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
        "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$").toRegex()

data class LoginUriView(
    val uri: String?,
    var matchType: UriMatchType,
) {

    val domain: String? by lazy {
        getDomain(uri).takeIf { !it.isNullOrBlank() }
    }

    companion object {

        private val CAN_LAUNCH_WHITE_LIST = listOf(
            "https://",
            "http://",
            "ssh://",
            "ftp://",
            "sftp://",
            "irc://",
            "vnc://",
            "chrome://",
            "iosapp://",
            "androidapp://"
        ).toHashSet()
    }


    val host: String?
        get() {
            if (matchType === UriMatchType.REGULAR_EXPRESSION) {
                return null
            }
            return getHost(uri).takeIf { !it.isNullOrBlank() }
        }

    val hostOrUri: String?
        get() = host ?: uri

    val isWebsite: Boolean by lazy {
        uri != null && uri.contains("://") && uri.matches(TLD_ENDING_REGEX)
    }

    val canLaunch: Boolean by lazy {
        if (uri != null && matchType !== UriMatchType.REGULAR_EXPRESSION) {
            val uri = launchUri
            CAN_LAUNCH_WHITE_LIST.any { prefix -> uri?.startsWith(prefix) == true }
        } else {
            false
        }
    }

    val launchUri: String? by lazy {
        if (uri != null) {
            val doesNotHaveProtocol = !uri.contains("://")
            val isTld = uri.matches(TLD_ENDING_REGEX)
            "http://$uri".takeIf { doesNotHaveProtocol && isTld } ?: uri
        } else {
            null
        }
    }
}

fun String?.toUriOrNull() = getUri(this)

fun getUri(uriString: String?): Url? {
    if (uriString.isNullOrBlank()) {
        return null
    }
    val hasHttpProtocol = uriString.startsWith("http://") || uriString.startsWith("https://")
    if (!hasHttpProtocol && uriString.contains(".")) {
        val uri = runCatching { URLBuilder("http://$uriString") }.getOrNull()
        if (uri != null) {
            return uri.build()
        }
    }
    return runCatching { URLBuilder(uriString).build() }.getOrNull()
}


fun getHost(uriString: String?): String? {
    val uri = getUri(uriString)
    if (uri != null && uri.host.isNotBlank()) {
        return if (uri.protocol.defaultPort == uri.port) {
            uri.host
        } else {
            uri.hostWithPort
        }
    }
    return null
}

fun getDomain(uriString: String?): String? {
    val uri = uriString.toUriOrNull()
    if (uri != null) {
        if (uri.host == "localhost" || uri.host.matches(IP_REGEX)) {
            return uri.host
        }
        return runCatching {
            val baseDomain = DomainName.tryParseBaseDomain(uri.host)
            return baseDomain ?: uri.host
        }.getOrNull()
    } else {
        return null
    }

}
