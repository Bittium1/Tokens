package org.ethereum.lists.tokens

import java.io.File
import kotlin.system.exitProcess

suspend fun main(args: Array<String>) {

    if (args.isEmpty()) {
        error("fileToCheck not specified :-)\nPlease execute like this: ./gradlew -PfileToCheck=/path/you/want/to/import")
    }

    val fileToCheck = File(args[0])

    // Enhanced validation with detailed debug information
    if (!fileToCheck.exists()) {
        error("fileToCheck ($fileToCheck) does not exist\nAbsolute path: ${fileToCheck.absolutePath}\nExpected to be under: ${allNetworksTokenDir.absolutePath}")
    }

    if (!fileToCheck.isFile) {
        error("fileToCheck ($fileToCheck) is not a file\nAbsolute path: ${fileToCheck.absolutePath}\nIs directory: ${fileToCheck.isDirectory}")
    }

    val parentFile = fileToCheck.parentFile

    if (parentFile?.parentFile == allNetworksTokenDir) {
        println("checking $fileToCheck")
        checkTokenFile(fileToCheck, true, getChainId(parentFile.name))
    } else {
        // Provide detailed debug information for ignored files
        val parentPath = parentFile?.absolutePath ?: "null"
        val grandParentPath = parentFile?.parentFile?.absolutePath ?: "null"
        val expectedPath = allNetworksTokenDir.absolutePath
        
        println("ignoring $fileToCheck")
        println("Debug info:")
        println("  File path: ${fileToCheck.absolutePath}")
        println("  Parent path: $parentPath")
        println("  Grandparent path: $grandParentPath")
        println("  Expected tokens dir: $expectedPath")
        println("  Parent matches expected: ${parentFile?.parentFile?.absolutePath == expectedPath}")
    }

}

private fun error(message: String) {
    println("Error: $message")
    exitProcess(1)
}