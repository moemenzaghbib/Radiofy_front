/*
 * Transistor.kt
 * Implements the Transistor class
 * Transistor is the base Application class that sets up day and night theme
 *
 * This file is part of
 * TRANSISTOR - Radio App for Android
 *
 * Copyright (c) 2015-22 - Y20K.org
 * Licensed under the MIT-License
 * http://opensource.org/licenses/MIT
 */


package esprit.tn.radiofy

import android.app.Application
import esprit.tn.radiofy.helpers.AppThemeHelper
import esprit.tn.radiofy.helpers.LogHelper
import esprit.tn.radiofy.helpers.PreferencesHelper
import esprit.tn.radiofy.helpers.PreferencesHelper.initPreferences


/**
 * Transistor.class
 */
class Transistor: Application () {

    /* Define log tag */
    private val TAG: String = LogHelper.makeLogTag(Transistor::class.java)


    /* Implements onCreate */
    override fun onCreate() {
        super.onCreate()
        LogHelper.v(TAG, "Transistor application started.")
        initPreferences()
        // set Dark / Light theme state
        AppThemeHelper.setTheme(PreferencesHelper.loadThemeSelection())
    }


    /* Implements onTerminate */
    override fun onTerminate() {
        super.onTerminate()
        LogHelper.v(TAG, "Transistor application terminated.")
    }

}
