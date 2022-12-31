

package com.example.myapplication.helpers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.myapplication.Keys




/*
 * DownloadWorker class
 */
class DownloadWorker(context : Context, params : WorkerParameters): Worker(context, params) {

    /* Define log tag */
    private val TAG: String = LogHelper.makeLogTag(DownloadWorker::class.java)


    /* Overrides doWork */
    override fun doWork(): Result {
        // determine what type of download is requested
        when(inputData.getInt(Keys.KEY_DOWNLOAD_WORK_REQUEST,0)) {
            // CASE: update collection
            Keys.REQUEST_UPDATE_COLLECTION -> {
                doOneTimeHousekeeping()
                updateCollection()
            }
        }
        return Result.success()

    }


    /* Updates collection of stations */
    private fun updateCollection() {
        // todo implement / or delete
    }


    /* Execute one-time housekeeping */
    private fun doOneTimeHousekeeping() {
        if (PreferencesHelper.isHouseKeepingNecessary()) {
            /* add whatever housekeeping is necessary here */

            // housekeeping finished - save state
            // PreferencesHelper.saveHouseKeepingNecessaryState() // TODO uncomment if you need to do housekeeping here
        }
    }

}
