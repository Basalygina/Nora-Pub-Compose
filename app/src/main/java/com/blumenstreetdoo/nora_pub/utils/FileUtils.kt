package com.blumenstreetdoo.nora_pub.utils

import android.content.Context
import android.net.Uri
import java.io.File

fun Context.copyUriToInternalFile(sourceUri: Uri): Uri {
    val avatarsDir = File(filesDir, "avatars").apply { if (!exists()) mkdirs() }

    val destFile = File(avatarsDir, "avatar_${System.currentTimeMillis()}.jpg")

    contentResolver.openInputStream(sourceUri)?.use { input ->
        destFile.outputStream().use { output ->
            input.copyTo(output)
        }
    }

    return Uri.fromFile(destFile)
}