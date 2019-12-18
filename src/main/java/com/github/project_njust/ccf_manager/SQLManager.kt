package com.github.project_njust.ccf_manager

import com.github.project_njust.ccf_manager.sql.*
import com.github.project_njust.ccf_manager.sql.impl.*
import com.github.project_njust.ccf_manager.wrapper.json.MemorySection
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.apache.log4j.Logger
import java.io.File
import java.sql.Connection
import java.sql.SQLException
import kotlin.coroutines.coroutineContext

object SQLManager {
    lateinit var connectPool: HikariDataSource
    private var init = false

    @JvmStatic
    fun getConnection(): Connection {
        return connectPool.connection
    }

    @JvmStatic
    fun evictConnection(conn: Connection) {
        connectPool.evictConnection(conn)
    }

    suspend fun coroutinesConnection(func: suspend Connection.() -> Unit) {
        var conn: Connection? = null
        try {
            conn = connectPool.connection
            conn.func()
        } catch (e: SQLException) {
            Logger.getLogger(SQLManager::class.java).error("执行数据库回调中发生错误", e)
        } finally {
            if (conn != null) {
                connectPool.evictConnection(conn)
            }
        }
    }

    suspend fun async(func: suspend Connection.() -> Unit): Deferred<Boolean> {
        return GlobalScope.async(coroutineContext) {
            var conn: Connection? = null
            try {
                conn = connectPool.connection
                conn.func()
                return@async true
            } catch (e: SQLException) {
                Logger.getLogger(SQLManager::class.java).error("执行数据库回调中发生错误", e)
            } finally {
                if (conn != null) {
                    connectPool.evictConnection(conn)
                }
            }
            false
        }
    }

    suspend fun <R> asyncDeferred(func: suspend Connection.() -> R?): Deferred<R?> {
        return GlobalScope.async(coroutineContext) {
            var conn: Connection? = null
            try {
                conn = connectPool.connection
                return@async conn.func()
            } catch (e: SQLException) {
                Logger.getLogger(SQLManager::class.java).error("执行数据库回调中发生错误", e)
            } finally {
                if (conn != null) {
                    connectPool.evictConnection(conn)
                }
            }
            null
        }
    }

    inline fun operateConnection(func: Connection.() -> Unit) {
        var conn: Connection? = null
        try {
            conn = connectPool.connection
            conn.func()
        } catch (e: SQLException) {
            Logger.getLogger(SQLManager::class.java).error("执行数据库回调中发生错误", e)
        } finally {
            if (conn != null) {
                connectPool.evictConnection(conn)
            }
        }
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

    private fun createTable() {
        operateConnection {
            val sta = this.createStatement()
            sta.execute("""
CREATE TABLE IF NOT EXISTS User
(
    UID      INT PRIMARY KEY AUTO_INCREMENT,
    PASSWORD TEXT         NOT NULL,
    NAME     VARCHAR(255) NOT NULL,
    `TYPE`   INT          NOT NULL DEFAULT 0,
    DATA     JSON
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
            """)
            sta.execute("""
CREATE TABLE IF NOT EXISTS Student
(
    STUDENTID    VARCHAR(255) NOT NULL PRIMARY KEY,
    UID          INT          NOT NULL DEFAULT -1,
    IDENTITYCRAD VARCHAR(255) NOT NULL,
    DATA         JSON
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
            """)
            sta.execute("""
CREATE TABLE IF NOT EXISTS ExamInformation
(
    EXAMID    INT  NOT NULL AUTO_INCREMENT PRIMARY KEY,
    EXAMSCORE INT  NOT NULL,
    EXAMDATE  DATE NOT NULL,
    CODE      VARCHAR(255),
    DATA      JSON
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
            """)
            sta.execute("""
CREATE TABLE IF NOT EXISTS ExamScore
(
    UID         INT          NOT NULL,
    EXAMID      INT          NOT NULL,
    CONFIRM     BOOLEAN      NOT NULL DEFAULT FALSE,
    EXAMGRADE   INT          NOT NULL,
    DATA        JSON,
    PRIMARY KEY (UID, EXAMID),
    FOREIGN KEY (UID) REFERENCES User (UID),
    FOREIGN KEY (EXAMID) REFERENCES ExamInformation (EXAMID)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
            """)
            try {
                sta.execute("""
CREATE VIEW UserName (NAME, UID) AS (
    SELECT JSON_EXTRACT(DATA, '$.Name'), UID
    FROM User
    WHERE JSON_CONTAINS(DATA, '$.Name')
)
                """)
            } catch (e: Throwable) {
            }
        }
    }

    @JvmStatic
    fun getExamInfoManager(): IExamInfoManager = ExamInfoManagerImpl
    @JvmStatic
    fun getExamScoreManager(): IExamScoreManager = ExamScoreManagerImpl

    @JvmStatic
    fun getStudentManager(): IStudentManager = StudentManagerImpl
    @JvmStatic
    fun getUserManager(): IUserManager = UserManagerImpl
}