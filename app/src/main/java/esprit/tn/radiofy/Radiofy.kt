
package esprit.tn.radiofy

import android.app.Application
import esprit.tn.radiofy.helpers.AppThemeHelper
import esprit.tn.radiofy.helpers.LogHelper
import esprit.tn.radiofy.helpers.PreferencesHelper
import esprit.tn.radiofy.helpers.PreferencesHelper.initPreferences


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
