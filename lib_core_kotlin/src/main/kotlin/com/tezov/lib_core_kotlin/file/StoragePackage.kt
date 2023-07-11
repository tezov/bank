/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_kotlin.file

import com.tezov.lib_core_kotlin.type.collection.ListEntry

object StoragePackage {

    interface Environnement {
        val privateDataPath: String
        val privateDataCachePath: String
        val privateDataFilePath: String
        val privateDataBasePath: String
        val privateSharePreferencePath: String
        val publicDataPath: String
    }

    private lateinit var _environnement: Environnement
    var environnement: Environnement
        get() = _environnement
        set(value) {
            _environnement = value
            Type.items.clear()
            Type.items.apply {
                add("PRIVATE_DATA", Type("PRIVATE_DATA", _environnement.privateDataPath))
                add(
                    "PRIVATE_DATA_CACHE",
                    Type("PRIVATE_DATA_CACHE", _environnement.privateDataCachePath)
                )
                add(
                    "PRIVATE_DATA_FILE",
                    Type("PRIVATE_DATA_FILE", _environnement.privateDataFilePath)
                )
                add(
                    "PRIVATE_DATABASE",
                    Type("PRIVATE_DATABASE", _environnement.privateDataBasePath)
                )
                add(
                    "PRIVATE_SHARE_PREFERENCE",
                    Type("PRIVATE_SHARE_PREFERENCE", _environnement.privateSharePreferencePath)
                )
                add("PUBLIC_DATA", Type("PUBLIC_DATA", _environnement.publicDataPath))
            }
        }

    init {
        Path.items.apply {
            add("TEMP", Path("TEMP", "tmp"))
            add("TRASH", Path("TRASH", "trash"))
            add("DATABASE", Path("DATABASE", "databases"))
            add("DATABASE_DOCUMENT", Path("DATABASE_DOCUMENT", "databases_document"))
            add("SHARED_PREFERENCES", Path("SHARED_PREFERENCES", "shared_prefs"))
        }
    }


    class Type(val name: String, val path: String) {
        fun newDirectory(path: Path?) = path?.let { Directory(this, it) } ?: let { Directory(this) }
        fun newFile(path: Path?) = path?.let { File(this, it) } ?: let { File(this) }
        fun newFileW() = FileW(path)

        companion object {
            internal val items = ListEntry<String, Type>()
            fun valueOf(name: String) = items.getValue(name)

            val PRIVATE_DATA get() = valueOf("PRIVATE_DATA")
            val PRIVATE_DATA_CACHE get() = valueOf("PRIVATE_DATA_CACHE")
            val PRIVATE_DATA_FILE get() = valueOf("PRIVATE_DATA_FILE")
            val PRIVATE_DATABASE get() = valueOf("PRIVATE_DATABASE")
            val PRIVATE_SHARE_PREFERENCE get() = valueOf("PRIVATE_SHARE_PREFERENCE")
            val PUBLIC_DATA get() = valueOf("PUBLIC_DATA")
        }
    }

    class Path(val name: String, val value: String) {
        companion object {
            internal val items = ListEntry<String, Path>()
            fun valueOf(name: String) = items.getValue(name)
            val TEMP get() = valueOf("TEMP")
            val TRASH get() = valueOf("TRASH")
            val DATABASE get() = valueOf("DATABASE")
            val DATABASE_DOCUMENT get() = valueOf("DATABASE_DOCUMENT")
            val SHARED_PREFERENCES get() = valueOf("SHARED_PREFERENCES")
        }
    }


}