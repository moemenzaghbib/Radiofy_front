
package com.example.myapplication

import android.app.Application
import com.example.myapplication.helpers.AppThemeHelper
import com.example.myapplication.helpers.LogHelper
import com.example.myapplication.helpers.PreferencesHelper
import com.example.myapplication.helpers.PreferencesHelper.initPreferences



class Radiofy: Application () {

    /* Define log tag */
    private val TAG: String = LogHelper.makeLogTag(Radiofy::class.java)


    /* Implements onCreate */
    override fun onCreate() {
        super.onCreate()
        LogHelper.v(TAG, "Radiofy application started.")
        initPreferences()
        // set Dark / Light theme state
        AppThemeHelper.setTheme(PreferencesHelper.loadThemeSelection())
    }


    /* Implements onTerminate */
    override fun onTerminate() {
        super.onTerminate()
        LogHelper.v(TAG, "Radiofy application terminated.")
    }

}
