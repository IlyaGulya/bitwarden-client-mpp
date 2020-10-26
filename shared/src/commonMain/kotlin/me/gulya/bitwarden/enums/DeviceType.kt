package me.gulya.bitwarden.enums

import kotlinx.serialization.Serializable
import me.gulya.bitwarden.utilities.serialization.ByteEnumTypeSerializer

@Serializable(with = DeviceTypeSerializer::class)
enum class DeviceType(
    override val value: Byte,
) : Valued<Byte> {
    Android(0),
    iOS(1),
    ChromeExtension(2),
    FirefoxExtension(3),
    OperaExtension(4),
    EdgeExtension(5),
    WindowsDesktop(6),
    MacOsDesktop(7),
    LinuxDesktop(8),
    ChromeBrowser(9),
    FirefoxBrowser(10),
    OperaBrowser(11),
    EdgeBrowser(12),
    IEBrowser(13),
    UnknownBrowser(14),
    AndroidAmazon(15),
    UWP(16),
    SafariBrowser(17),
    VivaldiBrowser(18),
    VivaldiExtension(19),
    SafariExtension(20);

    companion object {
        fun forValue(value: Byte) = values().findValue(value)
    }
}

class DeviceTypeSerializer : ByteEnumTypeSerializer<DeviceType>(
    valueFactory = DeviceType.Companion::forValue
)