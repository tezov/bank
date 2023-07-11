/*
 * Copyright (c) 2023.
 * Created by Tezov on 09/07/2023 17:00
 * Last modified 09/07/2023 16:53
 * MIT Licence
 * Feel free to contact me for any request / feedback at tezov.app@gmail.com
 */

package com.tezov.lib_core_kotlin.file

import com.tezov.lib_core_kotlin.extension.ExtensionNull.nullify
import java.util.regex.Pattern

class FileTree constructor(
    private var directory: Directory? = null,
    private var recursive: Boolean = false
) {
    private var directoryLinks: MutableList<String>? = null
    private var fileLinks: MutableList<String>? = null
    private var patternPath: Pattern? = null
    private var patternFileName: Pattern? = null

    constructor(
        storageType: StoragePackage.Type?,
        recursive: Boolean
    ) : this(Directory(storageType), recursive)

    private fun copyPattern(fileTreeSource: FileTree) {
        patternPath = fileTreeSource.patternPath
        patternFileName = fileTreeSource.patternFileName
    }

    private fun fileMatches(fileName: String): Boolean {
        return if (patternFileName != null) {
            val matcher = patternFileName!!.matcher(fileName)
            matcher.matches()
        } else {
            true
        }
    }

    private fun pathMatches(directoryPath: String): Boolean {
        return if (patternPath != null) {
            val matcher = patternPath!!.matcher(directoryPath)
            matcher.matches()
        } else {
            true
        }
    }

    private fun buildDirectory() {
        fileLinks = null
        directoryLinks = null
        if (directory == null) {
            return
        }
        val baseStorage: String? =
            if (directory!!.storage != null) directory!!.storage!!.name else null
        var basePath = directory!!.relativePathString
        val basePathMatches: Boolean
        if (basePath == null) {
            basePath = ""
            basePathMatches = true
        } else {
            basePathMatches = pathMatches(basePath)
        }
        val baseLink = directory!!.toLinkString()
        val fileNames = directory!!.file.list()
        if (fileNames != null) {
            fileLinks = ArrayList()
            directoryLinks = ArrayList()
            for (name in fileNames) {
                val file = java.io.File(directory!!.file, name)
                if (file.isFile) {
                    if (basePathMatches && fileMatches(name)) {
                        fileLinks!!.add(File.toLinkString(baseLink, name))
                    }
                } else if (file.isDirectory) {
                    val directoryPath = basePath + name + Directory.PATH_SEPARATOR
                    if (pathMatches(directoryPath)) {
                        val link = Directory.toLinkString(baseStorage, directoryPath)
                        directoryLinks!!.add(link)
                    }
                }
            }
        }
        fileLinks = fileLinks.nullify()
        directoryLinks = directoryLinks.nullify()
    }

    fun build(): FileTree {
        buildDirectory()
        if (recursive && directoryLinks != null) {
            val fileLinksAll: MutableList<String> = ArrayList()
            val directoryLinksAll: MutableList<String> = ArrayList()
            for (directoryLink in directoryLinks!!) {
                val directory = Directory.from(
                    directoryLink
                )
                val fileTree = FileTree(directory, true)
                fileTree.copyPattern(this)
                fileTree.build()
                if (fileTree.fileLinks != null) {
                    fileLinksAll.addAll(fileTree.fileLinks!!)
                }
                if (fileTree.directoryLinks != null) {
                    directoryLinksAll.addAll(fileTree.directoryLinks!!)
                }
            }
            if (fileLinksAll.isNotEmpty()) {
                if (fileLinks == null) {
                    fileLinks = fileLinksAll
                } else {
                    fileLinks!!.addAll(fileLinksAll)
                }
            }
            if (directoryLinksAll.isNotEmpty()) {
                directoryLinks!!.addAll(directoryLinksAll)
            }
        }
        return this
    }

    fun hasDirectoryLinks(): Boolean {
        return directoryLinks != null
    }

    fun getDirectoryLinks(): List<String>? {
        return directoryLinks
    }

    fun hasFileLinks(): Boolean {
        return fileLinks != null
    }

    fun getFileLinks(): List<String>? {
        return fileLinks
    }

    fun setDirectory(storage: StoragePackage.Type?, path: StoragePackage.Path?): FileTree {
        directory = Directory(storage, path)
        return this
    }

    fun setDirectory(storage: StoragePackage.Type?): FileTree {
        directory = Directory(storage)
        return this
    }

    fun setDirectory(directory: Directory?): FileTree {
        this.directory = directory
        return this
    }

    fun setRecursive(flag: Boolean): FileTree {
        recursive = flag
        return this
    }

    fun setPatternPath(pattern: String?): FileTree {
        if (pattern != null) {
            patternPath = Pattern.compile(pattern)
        } else {
            patternPath = null
        }
        return this
    }

    fun setPatternFileName(pattern: String?): FileTree {
        if (pattern != null) {
            patternFileName = Pattern.compile(pattern)
        } else {
            patternFileName = null
        }
        return this
    }
}