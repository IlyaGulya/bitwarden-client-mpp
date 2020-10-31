package me.gulya.bitwarden.domain.login

import me.gulya.bitwarden.domain.data.crypto.*

class InMemoryKeyStorage : KeyStorage {
    private var masterKeyHash: MasterKeyHash? = null
    private var sessionKey: SessionKey? = null
    private var encryptedKey: EncryptedKey? = null
    private var encryptedPrivateKey: EncryptedPrivateKey? = null
    private val organizationKeys = mutableMapOf<String, OrganizationKey>()

    override suspend fun saveMasterKeyHash(hash: MasterKeyHash) {
        masterKeyHash = hash
    }

    override suspend fun getMasterKeyHash(): MasterKeyHash? {
        return masterKeyHash
    }

    override suspend fun saveSessionKey(key: SessionKey) {
        sessionKey = key
    }

    override suspend fun getSessionKey(): SessionKey? {
        return sessionKey
    }

    override suspend fun saveEncryptedKey(key: EncryptedKey) {
        encryptedKey = key
    }

    override suspend fun getEncryptedKey(): EncryptedKey? {
        return encryptedKey
    }

    override suspend fun saveEncryptedPrivateKey(key: EncryptedPrivateKey) {
        encryptedPrivateKey = key
    }

    override suspend fun getEncryptedPrivateKey(): EncryptedPrivateKey? {
        return encryptedPrivateKey
    }

    override suspend fun saveOrganizationKeys(keys: List<OrganizationIdAndKey>) {
        this.organizationKeys.clear()
        this.organizationKeys.putAll(
            keys.map { (id, key) -> id to key }
        )
    }

    override suspend fun getOrganizationKey(organizationId: String): OrganizationKey? {
        return organizationKeys[organizationId]
    }

}