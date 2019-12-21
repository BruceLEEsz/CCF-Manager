package com.github.project_njust.ccf_manager.wrapper.json

import com.google.gson.*
import java.io.File
import java.io.FileReader

open class MemorySection(
        private val name: String,
        private var keys: MutableSet<String>
) : JsonSection {

    companion object {
        val gson: Gson = Gson()
        val parser = JsonParser()
    }

    protected val map: MutableMap<String, Any?> = mutableMapOf()


    constructor(json: JsonObject) : this("", HashSet(json.keySet())) {
        for (key in json.keySet()) {
            val get = json.get(key)
            if (get.isJsonObject) {
                map[key] = MemorySection(get as JsonObject)
            } else {
                map[key] = get
            }
        }
    }

    constructor() : this("", mutableSetOf())

    constructor(json: String) : this(parser.parse(json).asJsonObject)

    constructor(file: File) : this() {
        val reader = FileReader(file)
        this.init(JsonParser().parse(reader).asJsonObject)
    }

    protected fun init(json: JsonObject) {
        keys = json.keySet()
        map.clear()
        for (key in json.keySet()) {
            val get = json.get(key)
            if (get.isJsonObject) {
                map[key] = MemorySection(get as JsonObject)
            } else {
                map[key] = get
            }
        }
    }

    override fun getName(): String = name

    override fun toJsonObject(): JsonObject {
        val obj = JsonObject()
        for (key in keys) {
            val any = map[key]
            if (any is JsonSection) {
                obj.add(key, any.toJsonObject())
            } else {
                if (any is JsonElement) {
                    obj.add(key, any)
                }
            }
        }
        return obj
    }

    override fun getKeys(): MutableSet<String> = keys

    fun getJson(path: String): JsonElement? {
        if (path.isEmpty()) {
            return null
        }
        val paths = path.split(",".toRegex(), 2)
        val any = map[paths[0]] ?: return null
        if (paths.size == 1) {
            if (any is JsonElement) {
                return any
            }
            return null
        }
        if (any is MemorySection) {
            return any.getJson(paths[1])
        }
        return null
    }


    private fun getArray(path: String): JsonArray? {
        val json = getJson(path) ?: return null
        return if (json.isJsonArray) {
            json.asJsonArray
        } else {
            null
        }
    }

    private fun getPrimitive(path: String): JsonPrimitive? {
        val json = getJson(path) ?: return null
        return if (json.isJsonPrimitive) {
            json.asJsonPrimitive
        } else {
            null
        }
    }


    override fun getBoolean(path: String): Boolean {
        return getPrimitive(path)?.asBoolean ?: false
    }

    override fun getInt(path: String): Int {
        return getPrimitive(path)?.asNumber?.toInt() ?: 0
    }

    override fun getDouble(path: String): Double {
        return getPrimitive(path)?.asNumber?.toDouble() ?: 0.0
    }

    override fun getLong(path: String): Long {
        return getPrimitive(path)?.asNumber?.toLong() ?: 0L
    }

    override fun getString(path: String): String? {
        return getPrimitive(path)?.asString ?: null
    }

    override fun getStringList(path: String): List<String>? {
        val array = getArray(path) ?: return null
        val list = mutableListOf<String>()
        for (v in array) {
            if (v.isJsonPrimitive) {
                val jp = v.asJsonPrimitive
                list.add(jp.asString)
                continue
            }
            return null
        }
        return list
    }

    override fun getList(path: String): List<Any>? {
        val array = getArray(path) ?: return null
        val list = mutableListOf<Any>()
        for (v in array) {
            if (v.isJsonPrimitive) {
                val jp = v.asJsonPrimitive
                if (jp.isString) {
                    list.add(jp.asString)
                } else if (jp.isBoolean) {
                    list.add(jp.asBoolean)
                } else if (jp.isNumber) {
                    list.add(jp.asNumber)
                } else if (jp.isBoolean) {
                    list.add(jp.asBoolean)
                }
                continue
            }
            return null
        }
        return list
    }

    override fun getNumberList(path: String): List<Number>? {
        val array = getArray(path) ?: return null
        val list = mutableListOf<Number>()
        for (v in array) {
            if (v.isJsonPrimitive) {
                val jp = v.asJsonPrimitive
                if (jp.isNumber) {
                    list.add(jp.asNumber)
                }
            }
        }
        return list
    }

    override fun getJsonSection(path: String): JsonSection? {
        if (path.isEmpty()) {
            return null
        }
        val paths = path.split(",".toRegex(), 2)
        val any = map[paths[0]] ?: return null
        if (paths.size == 1) {
            if (any is MemorySection) {
                return any
            }
            return null
        }
        if (any is MemorySection) {
            return any.getJsonSection(paths[1])
        }
        return null
    }

    override fun has(path: String): Boolean = getJson(path) != null

    override fun get(path: String): Any? {
        if (path.isEmpty()) {
            return null
        }
        val paths = path.split(",".toRegex(), 2)
        val any = map[paths[0]] ?: return null
        if (paths.size == 1) {
            if (any is MemorySection) {
                return any
            }
            if (any is JsonElement) {
                if (any.isJsonPrimitive) {
                    val jp = any.asJsonPrimitive
                    if (jp.isString) {
                        return jp.asString
                    } else if (jp.isBoolean) {
                        return jp.asBoolean
                    } else if (jp.isNumber) {
                        return jp.asNumber
                    } else if (jp.isBoolean) {
                        return jp.asBoolean
                    }
                }
                if (any.isJsonArray) {
                    val array = any.asJsonArray
                    val list = mutableListOf<Any>()
                    for (v in array) {
                        if (v.isJsonPrimitive) {
                            val jp = v.asJsonPrimitive
                            if (jp.isString) {
                                list.add(jp.asString)
                            } else if (jp.isBoolean) {
                                list.add(jp.asBoolean)
                            } else if (jp.isNumber) {
                                list.add(jp.asNumber)
                            } else if (jp.isBoolean) {
                                list.add(jp.asBoolean)
                            }
                            continue
                        }
                        return null
                    }
                    return list
                }
            }
            return null
        }
        if (any is MemorySection) {
            return any.get(paths[1])
        }
        return null
    }

    override fun set(path: String, data: Any?) {
        if (path.isEmpty()) {
            return
        }
        val paths = path.split(",".toRegex(), 2)
        if (paths.size == 1) {
            if (data == null) {
                keys?.remove(paths[0])
                map.remove(paths[0])
                return
            }
            keys?.add(paths[0])
            map[paths[0]] = warpObject(data)
            return
        }
        val any = map[paths[0]] ?: return

        if (any is MemorySection) {
            any[paths[1]] = data
        }
    }

    private fun warpObject(data: Any): JsonElement {
        if (data is String) {
            return JsonPrimitive(data)
        }
        if (data is Number) {
            return JsonPrimitive(data)
        }
        if (data is Char) {
            return JsonPrimitive(data)
        }
        if (data is Boolean) {
            return JsonPrimitive(data)
        }
        if (data is JsonElement) {
            return data
        }
        if(data is JsonSection){
            val obj = data.toJsonObject()
            return obj
        }
        if (data is Collection<*>) {
            val arr = JsonArray()
            for (v in data) {
                if (v == null) continue
                if (v is String) {
                    arr.add(v)
                } else if (v is Number) {
                    arr.add(v)
                } else if (v is Boolean) {
                    arr.add(v)
                }
                if (v is JsonElement) {
                    arr.add(v)
                }
                if(v is JsonSection){
                    arr.add(v.toJsonObject())
                }
            }
            return arr
        }
        throw IllegalArgumentException("无法包装对象")
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MemorySection) return false

        if (name != other.name) return false
        if (keys != other.keys) return false
        if (map != other.map) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + (keys?.hashCode() ?: 0)
        result = 31 * result + map.hashCode()
        return result
    }

    override fun toString(): String {
        val jo = toJsonObject()
        return gson.toJson(jo)
    }
}