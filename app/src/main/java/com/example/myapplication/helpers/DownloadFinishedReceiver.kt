

package com.example.myapplication.helpers

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


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
