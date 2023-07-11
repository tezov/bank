/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:55
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_kotlin.file

import com.tezov.lib_core_kotlin.file.UtilsFile.FileName
import com.tezov.lib_core_kotlin.file.UtilsFile.FileName.Companion.number
import com.tezov.lib_core_kotlin.util.UtilsString
import java.util.*

//TODO refactor in kolin style

object StorageFile {
    private const val URI_OBTAIN_MAX_RETRY = 5
    private const val URI_RANDOM_HEX_LENGTH = 6

    fun obtain(directoryPath: String?, fileFullName: String): java.io.File? {
        return obtain(java.io.File(directoryPath), fileFullName)
    }

    fun obtain(directory: java.io.File, fileFullName: String): java.io.File? {
        return try {
            if (!directory.exists() && !directory.mkdirs()) {
                throw Throwable("directory can't be created")
            }
            if (!directory.canWrite()) {
                throw Throwable("directory can't be written")
            }
            val mediaFile = java.io.File(directory, fileFullName)
            if (mediaFile.exists() || !mediaFile.createNewFile()) {
                throw Throwable("file can't be created")
            }
            java.io.File(directory, fileFullName)
        } catch (e: Throwable) {
            null
        }
    }

    private fun selectFileLike(
        directory: java.io.File,
        fileFullName: String?,
        excludeFileFullName: Boolean
    ): List<String>? {
        return if (!directory.exists()) {
            null
        } else {
            val fileFullNameExploded = FileName(fileFullName!!)
            val predicate: (String) -> Boolean =
                newPredicateSelectFileLike(fileFullNameExploded, excludeFileFullName)
            val files =
                directory.list { dir: java.io.File, fullName: String -> predicate(fullName) }
            if (files != null && files.size > 0) {
                Arrays.asList(*files)
            } else {
                null
            }
        }
    }

    fun newPredicateSelectFileLike(
        fileFullNameExploded: FileName,
        excludeFileFullName: Boolean
    ): (String) -> Boolean {
        val fileFullName = fileFullNameExploded.fullName
        val fileFullNameWithoutNumber = fileFullNameExploded.fullNameWithoutNumber
        val fileFullNameNumber = fileFullNameExploded.number
        return { fullName ->
            var result = false
            val fullNameExploded = FileName(fullName)
            if (fileFullNameWithoutNumber == fullNameExploded.fullNameWithoutNumber) {
                if (!excludeFileFullName || fullName != fileFullName) {
                    result = if (fileFullNameNumber == null) true
                    else {
                        val fullNameNumber = fullNameExploded.number
                        fullNameNumber != null && fullNameNumber >= fileFullNameNumber
                    }
                }
            }
            result
        }
    }

    fun obtainUnique(directoryPath: String, fileFullName: String): java.io.File? {
        return obtainUnique(java.io.File(directoryPath), fileFullName)
    }

    fun obtainUnique(directory: java.io.File, fileFullName: String): java.io.File? {
        val fileNames = selectFileLike(directory, fileFullName, false)
        var fileNamesSorted: List<String?>? = null
        if (fileNames != null) {
            val comparator: Comparator<String> = Comparator { s1, s2 ->
                val n1: Int? = s1.number()
                val n2: Int? = s2.number()
                if (n1 != null && n2 != null) {
                    Integer.compare(n1, n2)
                } else if (n1 != null) {
                    1
                } else {
                    -1
                }
            }
            fileNamesSorted = fileNames.sortedWith(comparator)
        }
        val fileName = FileName(fileFullName)
        if (fileNamesSorted != null) {
            var biggerNumber: Int? = fileNamesSorted.get(fileNamesSorted.size - 1).number()
            if (biggerNumber == null || biggerNumber < 0) {
                biggerNumber = 0
            }
            fileName.number = biggerNumber + 1
        }
        return try {
            if (!directory.exists() && !directory.mkdirs()) {
                throw Throwable("directory can't be created")
            }
            var attempt = 0
            do {
                val mediaFile = java.io.File(directory, fileName.fullName)
                if (mediaFile.createNewFile()) {
                    return java.io.File(directory, fileName.fullName)
                }
                fileName.incNumber()
                attempt++
            } while (attempt < URI_OBTAIN_MAX_RETRY)
            fileName.appendToName("_" + UtilsString.randomBase49(URI_RANDOM_HEX_LENGTH)).number =
                null
            val mediaFile = java.io.File(directory, fileName.fullName)
            if (mediaFile.createNewFile()) {
                java.io.File(directory, fileName.fullName)
            } else {
                null
            }
        } catch (e: Throwable) {
            null
        }
    }

    fun obtainClosest(directoryPath: String?, fileFullName: String): java.io.File? {
        return obtainClosest(java.io.File(directoryPath), fileFullName)
    }

    fun obtainClosest(directory: java.io.File, fileFullName: String): java.io.File? {
        var file = findFile(directory, fileFullName)
        if (file != null) {
            return file
        }
        file = obtain(directory, fileFullName)
        if (file != null) {
            return file
        }
        val fileNames = selectFileLike(directory, fileFullName, true)
        var fileNamesSorted: List<String?>? = null
        if (fileNames != null) {
            val comparator: Comparator<String> = Comparator { s1, s2 ->
                val n1: Int? = s1.number()
                val n2: Int? = s2.number()
                if (n1 != null && n2 != null) {
                    Integer.compare(n1, n2)
                } else if (n1 != null) {
                    1
                } else {
                    -1
                }
            }
            fileNamesSorted = fileNames.sortedWith(comparator)
        }
        return try {
            if (!directory.exists() && !directory.mkdirs()) {
                throw Throwable("directory can't be created")
            }
            var biggerNumber: Int? = null
            if (fileNamesSorted != null) {
                for (s in fileNamesSorted) {
                    val mediaFile = java.io.File(directory, s)
                    if (mediaFile.canWrite()) {
                        return java.io.File(directory, s)
                    }
                }
                biggerNumber = fileNamesSorted.get(fileNamesSorted.size - 1).number()
                if (biggerNumber == null || biggerNumber < 0) {
                    biggerNumber = 0
                }
            }
            var attempt = 0
            val fileName = FileName(fileFullName)
            if (biggerNumber == null) {
                fileName.number = null
            } else {
                fileName.number = biggerNumber + 1
            }
            try {
                do {
                    val mediaFile = java.io.File(directory, fileName.fullName)
                    if (mediaFile.createNewFile()) {
                        return java.io.File(directory, fileName.fullName)
                    }
                    fileName.incNumber()
                    attempt++
                } while (attempt < URI_OBTAIN_MAX_RETRY)
            } catch (e: Throwable) {
            }
            fileName.appendToName("_" + UtilsString.randomBase49(URI_RANDOM_HEX_LENGTH)).number =
                null
            val mediaFile = java.io.File(directory, fileName.fullName)
            if (mediaFile.createNewFile()) {
                java.io.File(directory, fileName.fullName)
            } else {
                null
            }
        } catch (e: Throwable) {
            null
        }
    }

    fun findFile(directoryPath: String?, fileFullName: String?): java.io.File? {
        return findFile(java.io.File(directoryPath), fileFullName)
    }

    fun findFile(directory: java.io.File, fileFullName: String?): java.io.File? {
        if (!directory.exists()) {
            return null
        }
        val mediaFile = java.io.File(directory, fileFullName)
        if (!mediaFile.exists()) {
            return null
        }
        return if (!mediaFile.canWrite()) {
            null
        } else {
            mediaFile
        }
    }

    fun obtainFile(directory: Directory, fileFullName: String): File? {
        return obtainFile(directory.path, fileFullName)
    }

    fun obtainFile(directoryPath: String?, fileFullName: String): File? {
        val file = obtain(directoryPath, fileFullName)
        return try {
            if (file != null) {
                File(file)
            } else {
                null
            }
        } catch (e: Throwable) {
            file!!.delete()
            null
        }
    }

    fun obtainUniqueFile(directory: Directory, fileFullName: String): File? {
        return obtainUniqueFile(directory.path, fileFullName)
    }

    fun obtainUniqueFile(directoryPath: String, fileFullName: String): File? {
        val file = obtainUnique(directoryPath, fileFullName)
        return try {
            if (file != null) {
                File(file)
            } else {
                null
            }
        } catch (e: Throwable) {
            file!!.delete()
            null
        }
    }

    fun obtainClosestFile(directory: Directory, fileFullName: String): File? {
        return obtainClosestFile(directory.path, fileFullName)
    }

    fun obtainClosestFile(directoryPath: String?, fileFullName: String): File? {
        val file = obtainClosest(directoryPath, fileFullName)
        return try {
            if (file != null) {
                File(file)
            } else {
                null
            }
        } catch (e: Throwable) {
            file!!.delete()
            null
        }
    }
}