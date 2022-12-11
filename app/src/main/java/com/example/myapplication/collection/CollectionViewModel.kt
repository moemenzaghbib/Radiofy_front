


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
import com.example.myapplication.helpers.FileHelper
import com.example.myapplication.helpers.LogHelper
import java.util.*


/*
 * CollectionViewModel.class
 */
class CollectionViewModel(application: Application) : AndroidViewModel(application) {


    /* Define log tag */
    private val TAG: String = LogHelper.makeLogTag(CollectionViewModel::class.java)


    /* Main class variables */
    val collectionLiveData: MutableLiveData<com.example.myapplication.core.Collection> = MutableLiveData<com.example.myapplication.core.Collection>()
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
            val collection: com.example.myapplication.core.Collection = FileHelper.readCollectionSuspended(getApplication())
            // get updated modification date
            modificationDateViewModel = collection.modificationDate
            // update collection view model
            collectionLiveData.value = collection
            // update collection sie
            collectionSizeLiveData.value = collection.stations.size
        }
    }
}
