package com.blumenstreetdoo.nora_pub.utils

import android.content.Context
import android.net.Uri
import java.io.File
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

suspend fun Context.copyUriToInternalFile(sourceUri: Uri): Uri = withContext(Dispatchers.IO) {
    val avatarsDir = File(filesDir, "avatars").apply { if (!exists()) mkdirs() }

    val destFile = File(avatarsDir, "avatar_${System.currentTimeMillis()}.jpg")

    contentResolver.openInputStream(sourceUri)?.use { input ->
        destFile.outputStream().use { output ->
            input.copyTo(output)
        }
    } ?: throw IOException("Failed to open input stream from URI: $sourceUri")

    return@withContext Uri.fromFile(destFile)
}
