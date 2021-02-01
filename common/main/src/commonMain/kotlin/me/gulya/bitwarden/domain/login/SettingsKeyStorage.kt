package me.gulya.bitwarden.domain.login

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.gulya.bitwarden.domain.data.crypto.*

class SettingsKeyStorage(
    private val settings: Settings,
    private val json: Json,
) : KeyStorage {
    companion object {
        const val KEY_MASTER_KEY_HASH = "master_key_hash"
        const val KEY_SESSION_KEY = "session_key"
        const val KEY_ENCRYPTED_KEY = "encrypted_key"
        const val KEY_ENCRYPTED_PRIVATE_KEY = "encrypted_private_key"
        const val KEY_ORGANIZATION_KEYS = "organization_keys"
    }

    private var masterKeyHash: MasterKeyHash? = null
    private var sessionKey: SessionKey? = null
    private var encryptedKey: EncryptedKey? = null
    private var encryptedPrivateKey: EncryptedPrivateKey? = null
    private val organizationKeys = mutableMapOf<String, OrganizationKey>()

    override suspend fun saveMasterKeyHash(hash: MasterKeyHash) {
        settings[KEY_MASTER_KEY_HASH] = hash.hash
    }

    override suspend fun getMasterKeyHash(): MasterKeyHash? {
        return settings.getStringOrNull(KEY_MASTER_KEY_HASH)?.let { MasterKeyHash(it) }
    }

    override suspend fun saveSessionKey(key: SessionKey) {
        settings[KEY_SESSION_KEY] = key.key
    }

    override suspend fun getSessionKey(): SessionKey? {
        return settings.getStringOrNull(KEY_SESSION_KEY)?.let { SessionKey(it) }
    }

    override suspend fun saveEncryptedKey(key: EncryptedKey) {
        settings[KEY_ENCRYPTED_KEY] = key.key
    }

    override suspend fun getEncryptedKey(): EncryptedKey? {
        return settings.getStringOrNull(KEY_ENCRYPTED_KEY)?.let { EncryptedKey(it) }
    }

    override suspend fun saveEncryptedPrivateKey(key: EncryptedPrivateKey) {
        settings[KEY_ENCRYPTED_PRIVATE_KEY] = key.key
    }

    override suspend fun getEncryptedPrivateKey(): EncryptedPrivateKey? {
        return settings.getStringOrNull(KEY_ENCRYPTED_PRIVATE_KEY)?.let { EncryptedPrivateKey(it) }
    }

    override suspend fun saveOrganizationKeys(keys: List<OrganizationIdAndKey>) {
        val map = keys.associateBy({ (id, _) -> id }, { (_, key) -> key.key })
        settings.putString(KEY_ORGANIZATION_KEYS, json.encodeToString(map))
    }

    override suspend fun getOrganizationKey(organizationId: String): OrganizationKey? {
        val strategy = MapSerializer(String.serializer(), String.serializer())
        val keysRaw =
            settings.getStringOrNull(KEY_ORGANIZATION_KEYS)?.let { json.decodeFromString(strategy, it) } ?: emptyMap()
        return keysRaw[organizationId]?.let { OrganizationKey(it) }
    }

}