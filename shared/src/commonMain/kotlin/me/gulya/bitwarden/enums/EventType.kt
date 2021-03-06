package me.gulya.bitwarden.enums

import kotlinx.serialization.Serializable
import me.gulya.bitwarden.utilities.serialization.ByteEnumTypeSerializer
import me.gulya.bitwarden.utilities.serialization.IntegerEnumTypeSerializer

@Serializable(with = EventTypeSerializer::class)
enum class EventType(
    override val value: Int,
): Valued<Int> {
    USER_LOGGED_IN(1000),
    USER_CHANGED_PASSWORD(1001),
    USER_UPDATED_2FA(1002),
    USER_DISABLED_2FA(1003),
    USER_RECOVERED_2FA(1004),
    USER_FAILED_LOGIN(1005),
    USER_FAILED_LOGIN_2FA(1006),
    USER_CLIENT_EXPORTED_VAULT(1007),
    CIPHER_CREATED(1100),
    CIPHER_UPDATED(1101),
    CIPHER_DELETED(1102),
    CIPHER_ATTACHMENT_CREATED(1103),
    CIPHER_ATTACHMENT_DELETED(1104),
    CIPHER_SHARED(1105),
    CIPHER_UPDATED_COLLECTIONS(1106),
    CIPHER_CLIENT_VIEWED(1107),
    CIPHER_CLIENT_TOGGLED_PASSWORD_VISIBLE(1108),
    CIPHER_CLIENT_TOGGLED_HIDDEN_FIELD_VISIBLE(1109),
    CIPHER_CLIENT_TOGGLED_CARD_CODE_VISIBLE(1110),
    CIPHER_CLIENT_COPIED_PASSWORD(1111),
    CIPHER_CLIENT_COPIED_HIDDEN_FIELD(1112),
    CIPHER_CLIENT_COPIED_CARD_CODE(1113),
    CIPHER_CLIENT_AUTOFILLED(1114),
    CIPHER_SOFT_DELETED(1115),
    CIPHER_RESTORED(1116),
    COLLECTION_CREATED(1300),
    COLLECTION_UPDATED(1301),
    COLLECTION_DELETED(1302),
    GROUP_CREATED(1400),
    GROUP_UPDATED(1401),
    GROUP_DELETED(1402),
    ORGANIZATION_USER_INVITED(1500),
    ORGANIZATION_USER_CONFIRMED(1501),
    ORGANIZATION_USER_UPDATED(1502),
    ORGANIZATION_USER_REMOVED(1503),
    ORGANIZATION_USER_UPDATED_GROUPS(1504),
    ORGANIZATION_UPDATED(1600),
    ORGANIZATION_PURGED_VAULT(1601);

    companion object {
        fun forValue(value: Int) = values().findValue(value)
    }
}

class EventTypeSerializer : IntegerEnumTypeSerializer<EventType>(
    valueFactory = EventType.Companion::forValue
)