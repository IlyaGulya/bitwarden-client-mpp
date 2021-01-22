package me.gulya.bitwarden.server.request

import me.gulya.bitwarden.domain.data.Cipher
import me.gulya.bitwarden.domain.data.ifType
import me.gulya.bitwarden.enums.CipherType
import me.gulya.bitwarden.server.*
import me.gulya.bitwarden.server.response.DateTimeContainer

class CipherRequest(
    val type: CipherType,
    val organizationId: String?,
    val folderId: String?,
    val name: String?,
    val notes: String?,
    val favorite: Boolean?,
    val login: ServerLogin?,
    val secureNote: SecureNoteApi?,
    val card: ServerCard?,
    val identity: ServerIdentity?,
    val fields: List<ServerField>?,
    val passwordHistory: List<PasswordHistoryRequest>?,
    val attachments: Map<String, String>?,
    val attachments2: Map<String, AttachmentRequest>?,
) {


    constructor(
        cipher: Cipher
    ) : this(
        type = cipher.type,
        organizationId = cipher.organizationId,
        folderId = cipher.folderId,
        name = cipher.name?.encryptedString,
        notes = cipher.notes?.encryptedString,
        favorite = cipher.favorite,
        login = cipher.ifType(CipherType.Login) {
            login?.run {
                ServerLogin(
                    username = username?.encryptedString,
                    password = password?.encryptedString,
                    passwordRevisionDate = passwordRevisionDate?.let { DateTimeContainer(passwordRevisionDate) },
                    totp = totp?.encryptedString,
                    uris = uris.map { loginUri ->
                        ServerLoginUri(
                            match = loginUri.matchType,
                            uri = loginUri.uri.encryptedString,
                        )
                    },
                )
            }
        },
        card = cipher.ifType(CipherType.Card) {
            card?.run {
                ServerCard(
                    cardholderName = cardholderName?.encryptedString,
                    brand = brand?.encryptedString,
                    number = number?.encryptedString,
                    expMonth = expMonth?.encryptedString,
                    expYear = expYear?.encryptedString,
                    code = code?.encryptedString,
                )
            }
        },
        identity = cipher.ifType(CipherType.Identity) {
            identity?.run {
                ServerIdentity(
                    title = title?.encryptedString,
                    firstName = firstName?.encryptedString,
                    middleName = middleName?.encryptedString,
                    lastName = lastName?.encryptedString,
                    address1 = address1?.encryptedString,
                    address2 = address2?.encryptedString,
                    address3 = address3?.encryptedString,
                    city = city?.encryptedString,
                    state = state?.encryptedString,
                    postalCode = postalCode?.encryptedString,
                    country = country?.encryptedString,
                    company = company?.encryptedString,
                    email = email?.encryptedString,
                    phone = phone?.encryptedString,
                    sSN = sSN?.encryptedString,
                    username = username?.encryptedString,
                    passportNumber = passportNumber?.encryptedString,
                    licenseNumber = licenseNumber?.encryptedString,
                )
            }
        },
        secureNote = cipher.ifType(CipherType.SecureNote) {
            secureNote?.run {
                SecureNoteApi(
                    type = secureNote.type
                )
            }
        },
        fields = cipher.fields.takeIf { it.isNotEmpty() }?.map {
            it.run {
                ServerField(
                    type = type,
                    name = name?.encryptedString,
                    value = value?.encryptedString,
                )
            }
        },
        passwordHistory = cipher.passwordHistory.takeIf { it.isNotEmpty() }?.map { passwordHistory ->
            passwordHistory.run {
                PasswordHistoryRequest(
                    password = password.encryptedString,
                    lastUsedDate = lastUsedDate,
                )
            }
        },
        attachments = cipher.attachments.takeIf { it.isNotEmpty() }
            ?.associateBy({ it.id }, { it.fileName.encryptedString }),
        attachments2 = cipher.attachments.takeIf { it.isNotEmpty() }?.associateBy({ it.id }, {
            it.run {
                AttachmentRequest(
                    fileName = fileName.encryptedString,
                    key = key.encryptedString
                )
            }
        }),
    )
}