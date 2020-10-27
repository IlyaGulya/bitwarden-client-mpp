package me.gulya.bitwarden.api

import io.ktor.client.request.forms.*
import me.gulya.bitwarden.domain.data.EnvironmentUrls
import me.gulya.bitwarden.server.request.CipherCollectionsRequest
import me.gulya.bitwarden.server.request.CipherCreateRequest
import me.gulya.bitwarden.server.request.CipherRequest
import me.gulya.bitwarden.server.request.CipherShareRequest
import me.gulya.bitwarden.server.request.DeviceTokenRequest
import me.gulya.bitwarden.server.request.EventRequest
import me.gulya.bitwarden.server.request.FolderRequest
import me.gulya.bitwarden.server.request.KeysRequest
import me.gulya.bitwarden.server.request.PasswordHintRequest
import me.gulya.bitwarden.server.request.PasswordVerificationRequest
import me.gulya.bitwarden.server.request.PreloginRequest
import me.gulya.bitwarden.server.request.RegisterRequest
import me.gulya.bitwarden.server.request.SetPasswordRequest
import me.gulya.bitwarden.server.request.TokenRequest
import me.gulya.bitwarden.server.request.TwoFactorEmailRequest
import me.gulya.bitwarden.server.response.BreachAccountResponse
import me.gulya.bitwarden.server.response.CipherResponse
import me.gulya.bitwarden.server.response.FolderResponse
import me.gulya.bitwarden.server.response.IdentityResponse
import me.gulya.bitwarden.server.response.IdentityTokenResponse
import me.gulya.bitwarden.server.response.PreloginResponse
import me.gulya.bitwarden.server.response.ProfileResponse
import me.gulya.bitwarden.server.response.SyncResponse

interface BitwardenApi {

    suspend fun deleteCipher(id: String)
    suspend fun deleteCipherAttachment(id: String, attachmentId: String)
    suspend fun deleteFolder(id: String)
    suspend fun doRefreshToken(): IdentityTokenResponse
    suspend fun getAccountRevisionDate(): Long
    suspend fun getActiveBearerToken(): String
    suspend fun getCipher(id: String): CipherResponse
    suspend fun getFolder(id: String): FolderResponse
    suspend fun getProfile(): ProfileResponse
    suspend fun sync(): SyncResponse
    suspend fun postAccountKeys(request: KeysRequest)
    suspend fun verifyAccountPassword(request: PasswordVerificationRequest)
    suspend fun postCipher(request: CipherRequest): CipherResponse
    suspend fun postCipherCreate(request: CipherCreateRequest): CipherResponse
    suspend fun postFolder(request: FolderRequest): FolderResponse
    suspend fun identityToken(request: TokenRequest, identityClientId: String): IdentityResponse
    suspend fun postPasswordHint(request: PasswordHintRequest)
    suspend fun setPassword(request: SetPasswordRequest)
    suspend fun prelogin(request: PreloginRequest): PreloginResponse
    suspend fun postRegister(request: RegisterRequest)
    suspend fun putCipher(id: String?, request: CipherRequest): CipherResponse
    suspend fun putCipherCollections(id: String, request: CipherCollectionsRequest)
    suspend fun putFolder(
        id: String,
        request: FolderRequest
    ): FolderResponse

    suspend fun putShareCipher(id: String, request: CipherShareRequest): CipherResponse
    suspend fun putDeleteCipher(id: String)
    suspend fun putRestoreCipher(id: String)
    suspend fun refreshIdentityToken()
    suspend fun preValidateSso(identifier: String): Any

    suspend fun setUrls(urls: EnvironmentUrls)
    suspend fun postCipherAttachment(id: String, data: MultiPartFormDataContent): CipherResponse
    suspend fun postShareCipherAttachment(
        id: String,
        attachmentId: String,
        data: MultiPartFormDataContent,
        organizationId: String
    )

    suspend fun getHibpBreach(username: String): List<BreachAccountResponse>
    suspend fun postTwoFactorEmail(request: TwoFactorEmailRequest)
    suspend fun putDeviceToken(identifier: String, request: DeviceTokenRequest)
    suspend fun postEventsCollect(request: Iterable<EventRequest>)
}