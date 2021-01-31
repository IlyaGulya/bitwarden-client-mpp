package me.gulya.bitwarden.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import me.gulya.bitwarden.domain.data.EnvironmentUrls
import me.gulya.bitwarden.domain.login.TokenInteractor
import me.gulya.bitwarden.server.request.*
import me.gulya.bitwarden.server.response.*

class KtorBitwardenApiImpl(
    private val client: HttpClient,
    private val endpointUrlHolder: EndpointUrlHolder,
    private val tokenInteractor: TokenInteractor,
) : BitwardenApi {

    private fun makeUrl(path: String): String {
        val realPath =
            if (path.startsWith("/")) {
                path
            } else {
                "/$path"
            }
        return "${endpointUrlHolder.getEndpointUrl()}$realPath"
    }

    private fun makeApiUrl(path: String) = makeUrl("/api$path")
    private fun makeIdentityUrl(path: String) = makeUrl("/identity$path")
    private fun makeEventsUrl(path: String) = makeUrl("/events$path")

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

    override suspend fun sync(): SyncResponse {
        return client.get {
            url.takeFrom(makeApiUrl("/sync"))
            auth()
        }
    }

    override suspend fun accountKeys(request: KeysRequest) {
        TODO("Not yet implemented")
    }

    override suspend fun verifyAccountPassword(request: PasswordVerificationRequest) {
        client.post<Unit> {
            url.takeFrom(makeApiUrl("/accounts/verify-password"))
            jsonBody()
            body = request
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

    override suspend fun identityToken(request: TokenRequest, identityClientId: String): IdentityResponse {
        return try {
            val response = client.post<IdentityTokenResponse> {
                url.takeFrom(makeIdentityUrl("/connect/token"))
                accept(ContentType.Application.Json)
                body = FormDataContent(Parameters.build {
                    request
                        .toIdentityToken(identityClientId)
                        .forEach { (key, value) -> append(key, value) }
                })
            }
            IdentityResponse.Token(response)
        } catch (e: ClientRequestException) {
            if (e.response.status == HttpStatusCode.BadRequest) {
                IdentityResponse.TwoFactor(e.response.receive())
            } else {
                throw e
            }
        }
    }

    override suspend fun postPasswordHint(request: PasswordHintRequest) {
        TODO("Not yet implemented")
    }

    override suspend fun setPassword(request: SetPasswordRequest) {
        TODO("Not yet implemented")
    }

    override suspend fun prelogin(request: PreloginRequest): PreloginResponse {
        return client.post {
            url.takeFrom(makeApiUrl("/accounts/prelogin"))
            jsonBody()
            body = request
        }
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

    private fun HttpRequestBuilder.auth() {
        val token = tokenInteractor.rawAccessToken()
        if (token != null) {
            header("Authorization", "Bearer $token")
        } else {
            throw IllegalStateException("Attempt to perform authorized request without access token!")
        }
    }

    private fun HttpRequestBuilder.jsonBody() {
        header(HttpHeaders.ContentType, ContentType.Application.Json)
    }
}