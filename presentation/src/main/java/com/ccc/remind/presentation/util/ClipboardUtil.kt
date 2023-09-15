package com.ccc.remind.presentation.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.net.Uri

class ClipboardUtil(private val context: Context) {
    private val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    fun copyText(label: String, text: String) {
        val clip: ClipData = ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)
    }

    fun copyUri(label: String, uri: Uri) {
        val clip: ClipData = ClipData.newRawUri(label, uri)
        clipboard.setPrimaryClip(clip)
    }
}