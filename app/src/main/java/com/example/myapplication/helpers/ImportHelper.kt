<<<<<<< HEAD
/*
 * ImportHelper.kt
 * Implements the ImportHelper object
 * A ImportHelper provides methods for integrating station files from Transistor v3
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
<<<<<<< HEAD
import com.example.myapplication.core.Station
=======
>>>>>>> 539e1dd2488e299a3a264c5982dd4d8f087c2889
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import com.example.myapplication.Keys
<<<<<<< HEAD

=======
import com.example.myapplication.core.Collection
import com.example.myapplication.core.Station
>>>>>>> 539e1dd2488e299a3a264c5982dd4d8f087c2889
import java.io.File
import java.util.*


/*
 * ImportHelper object
 */
object ImportHelper {

    /* Define log tag */
    private val TAG: String = LogHelper.makeLogTag(ImportHelper::class.java)


    /* Converts older station of type .m3u  */
    fun convertOldStations(context: Context): Boolean {
        val oldStations: ArrayList<Station> = arrayListOf()
        val oldCollectionFolder: File? = context.getExternalFilesDir(Keys.TRANSISTOR_LEGACY_FOLDER_COLLECTION)
        if (oldCollectionFolder != null && shouldStartImport(oldCollectionFolder)) {
            CoroutineScope(IO).launch {
                var success: Boolean = false
                // start import
                oldCollectionFolder.listFiles()?.forEach { file ->
                    // look for station files from Transistor v3
                    if (file.name.endsWith(Keys.TRANSISTOR_LEGACY_STATION_FILE_EXTENSION)) {
                        // read stream uri and name
                        val station: Station = FileHelper.readStationPlaylist(file.inputStream())
                        station.nameManuallySet = true
                        // detect stream content
                        station.streamContent = NetworkHelper.detectContentType(station.getStreamUri()).type
                        // try to also import station image
                        val sourceImageUri: String = getLegacyStationImageFileUri(context, station)
                        if (sourceImageUri != Keys.LOCATION_DEFAULT_STATION_IMAGE) {
                            // create and add image and small image + get main color
                            station.image = FileHelper.saveStationImage(context, station.uuid, sourceImageUri, Keys.SIZE_STATION_IMAGE_CARD, Keys.STATION_SMALL_IMAGE_FILE).toString()
                            station.smallImage = FileHelper.saveStationImage(context, station.uuid, sourceImageUri, Keys.SIZE_STATION_IMAGE_MAXIMUM, Keys.STATION_IMAGE_FILE).toString()
                            station.imageColor = ImageHelper.getMainColor(context, sourceImageUri)
                            station.imageManuallySet = true
                        }
                        // improvise a name if empty
                        if (station.name.isEmpty()) {
                            station.name = file.name.substring(0, file.name.lastIndexOf("."))
                            station.nameManuallySet = false
                        }
                        station.modificationDate = GregorianCalendar.getInstance().time
                        // add station
                        oldStations.add(station)
                        success = true
                    }
                }
                // check for success (= at least one station was found)
                if (success) {
                    // delete files from Transistor v3
                    oldCollectionFolder.deleteRecursively()
                    // sort and save collection
<<<<<<< HEAD
                    val newCollection: com.example.myapplication.core.Collection = CollectionHelper.sortCollection(com.example.myapplication.core.Collection(stations = oldStations))
=======
                    val newCollection: Collection = CollectionHelper.sortCollection(Collection(stations = oldStations))
>>>>>>> 539e1dd2488e299a3a264c5982dd4d8f087c2889
                    CollectionHelper.saveCollection(context, newCollection)
                }
            }
            // import has been started
            return true
        } else {
            // import has NOT been started
            return false

        }
    }


    /* Checks if conditions for a station import are met */
    private fun shouldStartImport(oldCollectionFolder: File): Boolean {
        return oldCollectionFolder.exists() &&
                oldCollectionFolder.isDirectory &&
                oldCollectionFolder.listFiles()?.isNotEmpty()!! &&
                !FileHelper.checkForCollectionFile(oldCollectionFolder)
    }


    /* Gets Uri for station images created by older Transistor versions */
    private fun getLegacyStationImageFileUri(context: Context, station: Station): String {
        val collectionFolder: File? = context.getExternalFilesDir(Keys.TRANSISTOR_LEGACY_FOLDER_COLLECTION)
        if (collectionFolder != null && collectionFolder.exists() && collectionFolder.isDirectory) {
            val stationNameCleaned: String = station.name.replace(Regex("[:/]"), "_")
            val legacyStationImage = File("$collectionFolder/$stationNameCleaned.png")
            if (legacyStationImage.exists()) {
                return legacyStationImage.toString()
            } else {
                return Keys.LOCATION_DEFAULT_STATION_IMAGE
            }
        } else {
            return Keys.LOCATION_DEFAULT_STATION_IMAGE
        }
    }


}
