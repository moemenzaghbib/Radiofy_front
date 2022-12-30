<<<<<<< HEAD
/*
 * AudioHelper.kt
 * Implements the AudioHelper object
 * A AudioHelper provides helper methods for handling audio files
 *
 * This file is part of
 * TRANSISTOR - Radio App for Android
 *
 * Copyright (c) 2015-22 - Y20K.org
 * Licensed under the MIT-License
 * http://opensource.org/licenses/MIT
 */

=======
>>>>>>> 539e1dd2488e299a3a264c5982dd4d8f087c2889

package com.example.myapplication.helpers

import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
<<<<<<< HEAD
import com.example.myapplication.helpers.LogHelper
=======
>>>>>>> 539e1dd2488e299a3a264c5982dd4d8f087c2889


/*
 * AudioHelper object
 */
object AudioHelper {

    /* Define log tag */
    private val TAG: String = LogHelper.makeLogTag(AudioHelper::class.java)


    /* Extract duration metadata from audio file */
    fun getDuration(context: Context, audioFileUri: Uri): Long {
        val metadataRetriever: MediaMetadataRetriever = MediaMetadataRetriever()
        var duration: Long = 0L
        try {
            metadataRetriever.setDataSource(context, audioFileUri)
            val durationString = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION) ?: String()
            duration = durationString.toLong()
        } catch (exception: Exception) {
            LogHelper.e(TAG, "Unable to extract duration metadata from audio file")
        }
        return duration
    }

}
