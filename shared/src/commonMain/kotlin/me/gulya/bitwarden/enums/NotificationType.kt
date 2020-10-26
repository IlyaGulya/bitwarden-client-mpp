package me.gulya.bitwarden.enums

import kotlinx.serialization.Serializable
import me.gulya.bitwarden.utilities.serialization.ByteEnumTypeSerializer

@Serializable(with = NotificationTypeSerializer::class)
enum class NotificationType(
    override val value: Byte,
) : Valued<Byte> {
    SYNC_CIPHER_UPDATE(0),
    SYNC_CIPHER_CREATE(1),
    SYNC_LOGIN_DELETE(2),
    SYNC_FOLDER_DELETE(3),
    SYNC_CIPHERS(4),
    SYNC_VAULT(5),
    SYNC_ORGANIZATION_KEYS(6),
    SYNC_FOLDER_CREATE(7),
    SYNC_FOLDER_UPDATE(8),
    SYNC_CIPHER_DELETE(9),
    SYNC_SETTINGS(10),
    LOG_OUT(11);

    companion object {
        fun forValue(value: Byte) = values().findValue(value)
    }

}

class NotificationTypeSerializer : ByteEnumTypeSerializer<NotificationType>(
    valueFactory = NotificationType.Companion::forValue
)