

package esprit.tn.radiofy

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import esprit.tn.radiofy.helpers.AppThemeHelper
import esprit.tn.radiofy.helpers.FileHelper
import esprit.tn.radiofy.helpers.LogHelper
import esprit.tn.radiofy.helpers.PreferencesHelper


/*
 * MainActivity class
 */
class MainActivity: AppCompatActivity() {

    /* Define log tag */
    private val TAG: String = LogHelper.makeLogTag(MainActivity::class.java)


    /* Main class variables */
    private lateinit var appBarConfiguration: AppBarConfiguration


    /* Overrides onCreate from AppCompatActivity */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // house-keeping: determine if edit stations is enabled by default todo: remove in 2023
        if (PreferencesHelper.loadCollectionSize() != -1) {
            // existing user detected - enable Edit Stations by default
            PreferencesHelper.saveEditStationsEnabled(true)
        }

        // set up views
        setContentView(R.layout.activity_main_radio)

        // create .nomedia file - if not yet existing
        FileHelper.createNomediaFile(getExternalFilesDir(null))

        // set up action bar
        setSupportActionBar(findViewById(R.id.main_toolbar))
        val toolbar: Toolbar = findViewById<Toolbar>(R.id.main_toolbar)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_host_container) as NavHostFragment
        val navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration)
        supportActionBar?.hide()

        // register listener for changes in shared preferences
        PreferencesHelper.registerPreferenceChangeListener(sharedPreferenceChangeListener)
    }


    /* Overrides onSupportNavigateUp from AppCompatActivity */
    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_host_container) as NavHostFragment
        val navController = navHostFragment.navController
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    /* Overrides onDestroy from AppCompatActivity */
    override fun onDestroy() {
        super.onDestroy()
        // unregister listener for changes in shared preferences
        PreferencesHelper.unregisterPreferenceChangeListener(sharedPreferenceChangeListener)
    }


    /*
     * Defines the listener for changes in shared preferences
     */
    private val sharedPreferenceChangeListener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
        when (key) {
            Keys.PREF_THEME_SELECTION -> {
                AppThemeHelper.setTheme(PreferencesHelper.loadThemeSelection())
            }
        }
    }
    /*
     * End of declaration
     */

}
