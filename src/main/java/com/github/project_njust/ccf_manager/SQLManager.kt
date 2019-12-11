package com.github.project_njust.ccf_manager

import com.github.project_njust.ccf_manager.wrapper.json.MemorySection
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.apache.log4j.Logger
import java.io.File
import java.sql.Connection
import java.sql.SQLException
import kotlin.coroutines.coroutineContext

object SQLManager {
    private lateinit var connectPool: HikariDataSource
    private var init = false

    @JvmStatic
    fun getConnection():Connection{
        return connectPool.connection
    }

    @JvmStatic
    fun evictConnection(conn:Connection){
        connectPool.evictConnection(conn)
    }

    @JvmStatic
    fun init() {
        if (init) return
        init = true
        Logger.getLogger(SQLManager::class.java).info("开始初始化数据库连接池")
        try {
            val configs = Thread.currentThread().contextClassLoader.getResourceAsStream("config.json")!!
            val file = File(FileManager.getBaseFolder(), "config.json")
            if (!file.exists()) {
                FileManager.saveResources(file, configs)
            }
            val config = MemorySection(file).getJsonSection("SQL")!!
            val url = "jdbc:${
            config.getString("jdbc")
            }://${
            config.getString("host")
            }:${
            config.getInt("port")
            }/${config.getString("database")}?user=${config.getString("user")}&password=${
            config.getString("password")
            }"
            val cfg = HikariConfig()
            try {
                Class.forName("com.mysql.jdbc.Driver")
            } catch (t: Throwable) {
            }
            cfg.jdbcUrl = url
            config.getJsonSection("options")?.let { c ->
                c.keys?.forEach {
                    cfg.addDataSourceProperty(it, c.getString(it))
                }
            }
            config.getJsonSection("HikariCP")?.let {
                cfg.idleTimeout = it.getLong("IdleTimeout")
                cfg.connectionTimeout = it.getLong("ConnectionTimeout")
                cfg.validationTimeout = it.getLong("ValidationTimeout")
                cfg.maxLifetime = it.getLong("MaxLifetime")
                cfg.maximumPoolSize = it.getInt("MaximumPoolSize")
            }
            connectPool = HikariDataSource(cfg)
        } catch (t: Throwable) {
            Logger.getLogger(SQLManager::class.java).error("初始化数据库中发生错误", t)
        }
    }
}