
package com.example.myapplication.search

import android.support.v4.media.session.PlaybackStateCompat
import com.google.gson.annotations.Expose
import com.example.myapplication.Keys
import com.example.myapplication.core.Station
import java.util.*


/*
 * RadioBrowserResult class
 */
data class RadioBrowserResult (@Expose val changeuuid: String,
                               @Expose val stationuuid: String,
                               @Expose val name: String,
                               @Expose val url: String,
                               @Expose val url_resolved: String,
                               @Expose val homepage: String,
                               @Expose val favicon: String) {

    /* Converts RadioBrowserResult to Station  */
    fun toStation(): Station = Station(
            starred = false,
            name = name,
            nameManuallySet = false,
            streamUris = mutableListOf(url_resolved),
            stream = 0,
            streamContent = Keys.MIME_TYPE_UNSUPPORTED,
            homepage = homepage,
            image = Keys.LOCATION_DEFAULT_STATION_IMAGE,
            smallImage = Keys.LOCATION_DEFAULT_STATION_IMAGE,
            imageColor = -1,
            imageManuallySet = false,
            remoteImageLocation = favicon,
            remoteStationLocation = url,
            modificationDate = GregorianCalendar.getInstance().time,
            playbackState = PlaybackStateCompat.STATE_STOPPED,
            radioBrowserStationUuid = stationuuid,
            radioBrowserChangeUuid = changeuuid)

}

