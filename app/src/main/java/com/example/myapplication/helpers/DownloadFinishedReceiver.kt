<<<<<<< HEAD
/*
 * DownloadFinishedReceiver.kt
 * Implements the DownloadFinishedReceiver class
 * A DownloadFinishedReceiver listens for finished downloads
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

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
<<<<<<< HEAD
import com.example.myapplication.helpers.LogHelper
=======
>>>>>>> 539e1dd2488e299a3a264c5982dd4d8f087c2889


/*
 * DownloadFinishedReceiver class
 */
class DownloadFinishedReceiver(): BroadcastReceiver() {


    /* Define log tag */
    private val TAG: String = LogHelper.makeLogTag(DownloadFinishedReceiver::class.java)


    /* Overrides onReceive */
    override fun onReceive(context: Context, intent: Intent) {
        // process the finished download
        DownloadHelper.processDownload(context, intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L))
    }
}
