package me.gulya.bitwarden.api

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import me.gulya.bitwarden.domain.EnvironmentUrls
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
import me.gulya.bitwarden.server.response.IdentityTokenResponse
import me.gulya.bitwarden.server.response.IdentityTwoFactorResponse
import me.gulya.bitwarden.server.response.PreloginResponse
import me.gulya.bitwarden.server.response.ProfileResponse
import me.gulya.bitwarden.server.response.SyncResponse

class KtorBitwardenApiImpl(
    val client: HttpClient
) : BitwardenApi {
    private val endpoint
        get() = "***REMOVED***"

    private fun makeUrl(path: String): String {
        val realPath =
            if (path.startsWith("/")) {
                path
            } else {
                "/$path"
            }
        return "$endpoint$realPath"
    }

    override suspend fun deleteCipher(id: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCipherAttachment(id: String, attachmentId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFolder(id: String) {
        TODO("Not yet implemented")
    }

    override suspend fun doRefreshToken(): IdentityTokenResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getAccountRevisionDate(): Long {
        TODO("Not yet implemented")
    }

    override suspend fun getActiveBearerToken(): String {
        TODO("Not yet implemented")
    }

    override suspend fun getCipher(id: String): CipherResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getFolder(id: String): FolderResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getProfile(): ProfileResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getSync(): SyncResponse {
        TODO("Not yet implemented")
    }

    override suspend fun postAccountKeys(request: KeysRequest) {
        TODO("Not yet implemented")
    }

    override suspend fun verifyAccountPassword(request: PasswordVerificationRequest) {
        client.post<Unit> {
            url {
                takeFrom(makeUrl("/accounts/verify-password"))
                body = request
            }
        }
    }

    override suspend fun postCipher(request: CipherRequest): CipherResponse {
        TODO("Not yet implemented")
    }

    override suspend fun postCipherCreate(request: CipherCreateRequest): CipherResponse {
        TODO("Not yet implemented")
    }

    override suspend fun postFolder(request: FolderRequest): FolderResponse {
        TODO("Not yet implemented")
    }

    override suspend fun postIdentityToken(request: TokenRequest): Pair<IdentityTokenResponse, IdentityTwoFactorResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun postPasswordHint(request: PasswordHintRequest) {
        TODO("Not yet implemented")
    }

    override suspend fun setPassword(request: SetPasswordRequest) {
        TODO("Not yet implemented")
    }

    override suspend fun postPrelogin(request: PreloginRequest): PreloginResponse {
        TODO("Not yet implemented")
    }

    override suspend fun postRegister(request: RegisterRequest) {
        TODO("Not yet implemented")
    }

    override suspend fun putCipher(id: String?, request: CipherRequest): CipherResponse {
        TODO("Not yet implemented")
    }

    override suspend fun putCipherCollections(id: String, request: CipherCollectionsRequest) {
        TODO("Not yet implemented")
    }

    override suspend fun putFolder(id: String, request: FolderRequest): FolderResponse {
        TODO("Not yet implemented")
    }

    override suspend fun putShareCipher(id: String, request: CipherShareRequest): CipherResponse {
        TODO("Not yet implemented")
    }

    override suspend fun putDeleteCipher(id: String) {
        TODO("Not yet implemented")
    }

    override suspend fun putRestoreCipher(id: String) {
        TODO("Not yet implemented")
    }

    override suspend fun refreshIdentityToken() {
        TODO("Not yet implemented")
    }

    override suspend fun preValidateSso(identifier: String): Any {
        TODO("Not yet implemented")
    }

    override suspend fun setUrls(urls: EnvironmentUrls) {
        TODO("Not yet implemented")
    }

    override suspend fun postCipherAttachment(id: String, data: MultiPartFormDataContent): CipherResponse {
        TODO("Not yet implemented")
    }

    override suspend fun postShareCipherAttachment(
        id: String,
        attachmentId: String,
        data: MultiPartFormDataContent,
        organizationId: String
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun getHibpBreach(username: String): List<BreachAccountResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun postTwoFactorEmail(request: TwoFactorEmailRequest) {
        TODO("Not yet implemented")
    }

    override suspend fun putDeviceToken(identifier: String, request: DeviceTokenRequest) {
        TODO("Not yet implemented")
    }

    override suspend fun postEventsCollect(request: Iterable<EventRequest>) {
        TODO("Not yet implemented")
    }
}