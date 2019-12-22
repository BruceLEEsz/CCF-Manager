package com.github.project_njust.ccf_manager

import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

object FileManager {
    val tempFilesFolder = File(getBaseFolder(), "tempFiles")
        get() {
            if (!field.exists()) {
                field.mkdirs()
            }
            return field
        }
    val configFolder = File(getBaseFolder(), "config")

    @JvmStatic
    fun clearTempFiles() {
        for (f in tempFilesFolder.listFiles()) {
            f.deleteOnExit()
        }
    }

    @JvmStatic
    fun checkFolder() {
        val bf = getBaseFolder()
        println(bf.absolutePath)
        if (!bf.exists()) {
            bf.mkdirs()
        }
        val mf = tempFilesFolder
        if (!mf.exists()) {
            mf.mkdir()
        }
        val cf = configFolder
        if (!cf.exists()) {
            cf.mkdir()
        }
    }

    @JvmStatic
    fun getBaseFolder() = File("CCF Manager")

    @JvmStatic
    fun saveResources(file: File, ins: InputStream) {
        if (file.exists()) {
            file.delete()
        }
        file.createNewFile()
        val fos = FileOutputStream(file)
        while (true) {
            val i = ins.read();
            if (i == -1) {
                break
            }
            fos.write(i)
        }
        fos.flush()
        fos.close()
    }
}