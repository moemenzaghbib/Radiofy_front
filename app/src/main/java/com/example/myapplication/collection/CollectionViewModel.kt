<<<<<<< HEAD

=======
/*
 * CollectionViewModel.kt
 * Implements the CollectionViewModel class
 * A CollectionViewModel stores the collection of stations as live data
 *
 * This file is part of
 * TRANSISTOR - Radio App for Android
 *
 * Copyright (c) 2015-22 - Y20K.org
 * Licensed under the MIT-License
 * http://opensource.org/licenses/MIT
 */
>>>>>>> 539e1dd2488e299a3a264c5982dd4d8f087c2889


package com.example.myapplication.collection

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kotlinx.coroutines.launch
import com.example.myapplication.Keys
<<<<<<< HEAD
=======
import com.example.myapplication.core.Collection
>>>>>>> 539e1dd2488e299a3a264c5982dd4d8f087c2889
import com.example.myapplication.helpers.FileHelper
import com.example.myapplication.helpers.LogHelper
import java.util.*


/*
 * CollectionViewModel.class
 */
class CollectionViewModel(application: Application) : AndroidViewModel(application) {

<<<<<<< HEAD

=======
>>>>>>> 539e1dd2488e299a3a264c5982dd4d8f087c2889
    /* Define log tag */
    private val TAG: String = LogHelper.makeLogTag(CollectionViewModel::class.java)


    /* Main class variables */
<<<<<<< HEAD
    val collectionLiveData: MutableLiveData<com.example.myapplication.core.Collection> = MutableLiveData<com.example.myapplication.core.Collection>()
=======
    val collectionLiveData: MutableLiveData<Collection> = MutableLiveData<Collection>()
>>>>>>> 539e1dd2488e299a3a264c5982dd4d8f087c2889
    val collectionSizeLiveData: MutableLiveData<Int> = MutableLiveData<Int>()
    private var modificationDateViewModel: Date = Date()
    private var collectionChangedReceiver: BroadcastReceiver


    /* Init constructor */
    init {
        // load collection
        loadCollection(application)
        // create and register collection changed receiver
        collectionChangedReceiver = createCollectionChangedReceiver()
        LocalBroadcastManager.getInstance(application).registerReceiver(collectionChangedReceiver, IntentFilter(Keys.ACTION_COLLECTION_CHANGED))
    }


    /* Overrides onCleared */
    override fun onCleared() {
        super.onCleared()
        LocalBroadcastManager.getInstance(getApplication()).unregisterReceiver(collectionChangedReceiver)
    }


    /* Creates the collectionChangedReceiver - handles Keys.ACTION_COLLECTION_CHANGED */
    private fun createCollectionChangedReceiver(): BroadcastReceiver {
        return object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (intent.hasExtra(Keys.EXTRA_COLLECTION_MODIFICATION_DATE)) {
                    val date: Date = Date(intent.getLongExtra(Keys.EXTRA_COLLECTION_MODIFICATION_DATE, 0L))
                    // check if reload is necessary
                    if (date.after(modificationDateViewModel)) {
                        LogHelper.v(TAG, "CollectionViewModel - reload collection after broadcast received.")
                        loadCollection(context)
                    }
                }
            }
        }
    }


    /* Reads collection of radio stations from storage using GSON */
    private fun loadCollection(context: Context) {
        LogHelper.v(TAG, "Loading collection of stations from storage")
        viewModelScope.launch {
            // load collection on background thread
<<<<<<< HEAD
            val collection: com.example.myapplication.core.Collection = FileHelper.readCollectionSuspended(getApplication())
=======
            val collection: Collection = FileHelper.readCollectionSuspended(getApplication())
>>>>>>> 539e1dd2488e299a3a264c5982dd4d8f087c2889
            // get updated modification date
            modificationDateViewModel = collection.modificationDate
            // update collection view model
            collectionLiveData.value = collection
            // update collection sie
            collectionSizeLiveData.value = collection.stations.size
        }
    }
<<<<<<< HEAD
=======

>>>>>>> 539e1dd2488e299a3a264c5982dd4d8f087c2889
}
