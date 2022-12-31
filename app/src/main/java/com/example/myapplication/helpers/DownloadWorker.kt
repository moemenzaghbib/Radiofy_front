<<<<<<< HEAD
/*
 * DownloadWorker.kt
 * Implements the DownloadWorker class
 * A DownloadWorker is a worker that triggers download actions when the app is not in use
 *
 * This file is part of
 * TRANSISTOR - Radio App for Android
 *
 * Copyright (c) 2015-22 - Y20K.org
 * Licensed under the MIT-License
 * http://opensource.org/licenses/MIT
 */
=======
>>>>>>> cf56406c4122f712c0822f597e758e8f4a2dd8b5


package com.example.myapplication.helpers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.myapplication.Keys
<<<<<<< HEAD
import com.example.myapplication.helpers.LogHelper
import com.example.myapplication.helpers.PreferencesHelper
=======
>>>>>>> cf56406c4122f712c0822f597e758e8f4a2dd8b5


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
<<<<<<< HEAD
        // (Returning Result.retry() tells WorkManager to try this task again later; Result.failure() says not to try again.)
=======
>>>>>>> cf56406c4122f712c0822f597e758e8f4a2dd8b5
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
