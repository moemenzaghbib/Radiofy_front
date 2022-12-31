
package com.example.myapplication.ui

import android.os.Parcelable
import android.support.v4.media.session.PlaybackStateCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.annotations.Expose
import kotlinx.parcelize.Parcelize
import com.example.myapplication.Keys


/*
 * PlayerState class
 */
@Parcelize
data class PlayerState (@Expose var stationUuid: String = String(),
                        @Expose var playbackState: Int = PlaybackStateCompat.STATE_STOPPED,
                        @Expose var bottomSheetState: Int = BottomSheetBehavior.STATE_HIDDEN,
                        @Expose var sleepTimerState: Int = Keys.STATE_SLEEP_TIMER_STOPPED): Parcelable
