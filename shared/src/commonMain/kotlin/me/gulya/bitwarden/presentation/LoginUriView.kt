package me.gulya.bitwarden.presentation

import me.gulya.bitwarden.utilities.DomainName
import io.ktor.http.*
import me.gulya.bitwarden.domain.LoginUri
import me.gulya.bitwarden.enums.UriMatchType

val TLD_ENDING_REGEX = ".*\\.(com|net|org|edu|uk|gov|ca|de|jp|fr|au|ru|ch|io|es|us|co|xyz|info|ly|mil)$".toRegex()
val IP_REGEX = ("^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
        "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
        "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
        "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$").toRegex()

data class LoginUriView(
    private var _uri: String? = null,
    private var _domain: String? = null,
    private var _host: String? = null,
    private var _canLaunch: Boolean? = null,
) : View() {
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


    constructor(u: LoginUri) {
        match = u.match
    }

    var match: UriMatchType? = null
    var uri: String?
        get() = _uri
        set(value) {
            _uri = value
            _domain = null
            _canLaunch = null
        }
    val domain: String?
        get() {
            if (_domain == null && uri != null) {
                _domain = getDomain(uri)
                if (_domain == "") {
                    _domain = null
                }
            }
            return _domain
        }
    val host: String?
        get() {
            if (match === UriMatchType.RegularExpression) {
                return null
            }
            if (_host == null && uri != null) {
                _host = getHost(uri)
                if (_host == "") {
                    _host = null
                }
            }
            return _host
        }
    val hostOrUri: String?
        get() = host ?: uri

    val isWebsite: Boolean
        get() {
            val uri = uri
            return uri != null && uri.contains("://") && uri.matches(TLD_ENDING_REGEX)
        }
    val canLaunch: Boolean
        get() {
            return _canLaunch ?: {
                if (uri != null && match !== UriMatchType.RegularExpression) {
                    val uri = launchUri
                    CAN_LAUNCH_WHITE_LIST.any { prefix -> uri?.startsWith(prefix) == true }.also { canLaunch ->
                        _canLaunch = canLaunch
                    }
                } else {
                    _canLaunch = false
                    false
                }
            }()
        }
    val launchUri: String?
        get() {
            val uri = uri
            return if (uri != null) {
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
