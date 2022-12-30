
<<<<<<< HEAD
=======

>>>>>>> 539e1dd2488e299a3a264c5982dd4d8f087c2889
package com.example.myapplication.ui

import android.os.Parcelable
import android.support.v4.media.session.PlaybackStateCompat
<<<<<<< HEAD
import com.example.myapplication.Keys
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize


=======
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.annotations.Expose
import kotlinx.parcelize.Parcelize
import com.example.myapplication.Keys
>>>>>>> 539e1dd2488e299a3a264c5982dd4d8f087c2889


/*
 * PlayerState class
 */
@Parcelize
data class PlayerState (@Expose var stationUuid: String = String(),
                        @Expose var playbackState: Int = PlaybackStateCompat.STATE_STOPPED,
                        @Expose var bottomSheetState: Int = BottomSheetBehavior.STATE_HIDDEN,
                        @Expose var sleepTimerState: Int = Keys.STATE_SLEEP_TIMER_STOPPED): Parcelable
