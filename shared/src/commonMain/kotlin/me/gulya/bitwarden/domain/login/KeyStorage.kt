package me.gulya.bitwarden.domain.login

import me.gulya.bitwarden.domain.data.crypto.EncryptedKey
import me.gulya.bitwarden.domain.data.crypto.EncryptedPrivateKey
import me.gulya.bitwarden.domain.data.crypto.MasterKeyHash
import me.gulya.bitwarden.domain.data.crypto.OrganizationKey

interface KeyStorage {
    suspend fun saveMasterKeyHash(hash: MasterKeyHash)
    suspend fun getMasterKeyHash(): MasterKeyHash?
    suspend fun saveEncryptedKey(key: EncryptedKey)
    suspend fun getEncryptedKey(): EncryptedKey?
    suspend fun saveEncryptedPrivateKey(key: EncryptedPrivateKey)
    suspend fun getEncryptedPrivateKey(): EncryptedPrivateKey?
    suspend fun saveOrganizationKeys(keys: List<OrganizationIdAndKey>)
    suspend fun getOrganizationKey(organizationId: String): OrganizationKey?
}