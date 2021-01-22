package me.gulya.bitwarden.domain.login

import me.gulya.bitwarden.api.BitwardenApi
import me.gulya.bitwarden.server.response.SyncResponse

class SyncInteractor(
    private val api: BitwardenApi,
    private val tokenInteractor: TokenInteractor,
) {

    suspend fun sync(): SyncResponse {
        val token = tokenInteractor.accessToken()
        return api.sync()
    }

}