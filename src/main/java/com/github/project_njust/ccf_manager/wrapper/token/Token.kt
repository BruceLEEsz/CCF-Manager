package com.github.project_njust.ccf_manager.wrapper.token

import com.github.project_njust.ccf_manager.UserType
import com.github.project_njust.ccf_manager.model.User
import com.github.project_njust.ccf_manager.wrapper.json.JsonSection
import org.apache.log4j.Logger
import java.security.MessageDigest
import java.util.*
import kotlin.random.Random


const val HEADER = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"


class Token(
        val uid: Int,
        val userType: UserType,
        val exp: Long = System.currentTimeMillis() + 60L * 1000L,
        val iat: Long = System.currentTimeMillis(),
        val jti: Long = Random.nextLong()
) {

    constructor(user: User) : this(user.uid, UserType.getType(user.type))

    fun toTokenString(): String {
        val json = JsonSection.createSection()
        json["uid"] = uid
        json["userType"] = userType.name
        json["exp"] = exp
        json["iat"] = iat
        json["jti"] = jti
        val data = json.toString()
        Logger.getLogger(Token::class.java).debug("生成token: $data")
        val base64 = data.toBase64()
        val auth = "$HEADER,$base64".hashSHA256WithSalt("$uid")
        return "$HEADER.$base64.$auth"
    }

}


fun String.toBase64(): String {
    return String(Base64.getUrlEncoder().withoutPadding().encode(this.toByteArray()))
}

fun String.deBase64(): String {
    return String(Base64.getUrlDecoder().decode(this))
}

val instance = MessageDigest.getInstance("SHA-256")
fun String.hashSHA256(): String {
    val ba = instance.digest("$this-ccf".toByteArray())
    return byteArrayToHexString(ba)
}

fun String.hashSHA256WithSalt(exsalt: String, salt: String = TokenManager.Signature): String {
    val ba = instance.digest("$exsalt-$this-$salt-ccf".toByteArray())
    return byteArrayToHexString(ba)
}

private val hexDigIts = "0123456789ABCDEF".toCharArray()
fun byteArrayToHexString(b: ByteArray): String {
    val resultSb = StringBuffer()
    for (i in b.indices) {
        resultSb.append(byteToHexString(b[i]))
    }
    return resultSb.toString()
}

fun byteToHexString(b: Byte): String {
    var n = b.toInt()
    if (n < 0) {
        n += 256
    }
    val d1 = n / 16
    val d2 = n % 16
    return hexDigIts[d1] + "" + hexDigIts[d2]
}