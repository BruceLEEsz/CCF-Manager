package com.github.project_njust.ccf_manager.wrapper.token

import com.github.project_njust.ccf_manager.FileManager
import com.github.project_njust.ccf_manager.UserType
import com.github.project_njust.ccf_manager.wrapper.json.MemorySection
import org.apache.log4j.Logger
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.util.*
import kotlin.random.Random

object TokenManager {
    //    const val TOURIST_TOKEN = ""
    lateinit var Signature: String

    @JvmStatic
    fun init() {
        val f = File(FileManager.getBaseFolder(), "TokenSignature")
        if (!f.exists()) {
            f.createNewFile()
            val fw = FileWriter(f)
            fw.write("${UUID.randomUUID()}/${Math.random()}/${Random.nextLong()}".hashSHA256())
            fw.flush()
            fw.close()
        }
        val fr = FileReader(f)
        Signature = fr.readText()
        fr.close()
    }

    @JvmStatic
    fun deToken(token: String): Token? {
        val str = token.split(".")
        if (str.size != 3 || str[1] != HEADER) {
            return null
        }
        try {
            val data = MemorySection(str[1].deBase64())
            val auth = "${str[0]},${str[1]}".hashSHA256WithSalt("${data.getInt("uid")}")
            if (auth == str[2]) {
                val result = Token(
                        data.getInt("uid"),
                        UserType.valueOf(data.getString("userType")!!),
                        data.getLong("exp"),
                        data.getLong("iat"),
                        data.getLong("jti")
                )
                if (result.exp < System.currentTimeMillis()) {
                    return Token(result.uid, result.userType)
                }
                return result
            }
        } catch (t: Throwable) {
            Logger.getLogger(TokenManager::class.java).warn("解析token时出现错误", t)
        }
        return null
    }
}