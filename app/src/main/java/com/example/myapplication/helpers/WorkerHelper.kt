

package com.example.myapplication.helpers

import androidx.work.*
import com.example.myapplication.Keys
import java.util.*
import java.util.concurrent.TimeUnit


/*
 * WorkerHelper object
 */
object WorkerHelper {

    /* Define log tag */
    private val TAG: String = LogHelper.makeLogTag(WorkerHelper::class.java)


    /* Schedules a DownloadWorker that triggers background updates of the collection periodically */
    fun schedulePeriodicUpdateWorker(): UUID {
        LogHelper.v(TAG, "Starting / Updating periodic work: update collection")
        val requestData: Data = Data.Builder()
                .putInt(Keys.KEY_DOWNLOAD_WORK_REQUEST, Keys.REQUEST_UPDATE_COLLECTION)
                .build()
        val unmeteredConstraint = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .build()
        val updateCollectionPeriodicWork = PeriodicWorkRequestBuilder<DownloadWorker>(Keys.UPDATE_REPEAT_INTERVAL, TimeUnit.HOURS, 30, TimeUnit.MINUTES)
                //.setConstraints(unmeteredConstraint)
                .setInputData(requestData)
                .build()
        WorkManager.getInstance().enqueueUniquePeriodicWork(Keys.NAME_PERIODIC_COLLECTION_UPDATE_WORK,  ExistingPeriodicWorkPolicy.REPLACE, updateCollectionPeriodicWork)
        return updateCollectionPeriodicWork.id
    }

}
