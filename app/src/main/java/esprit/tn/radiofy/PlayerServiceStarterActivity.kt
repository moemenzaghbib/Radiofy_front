

package esprit.tn.radiofy

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import esprit.tn.radiofy.playback.PlayerService


/*
 * PlayerServiceStarterActivity class
 */
class PlayerServiceStarterActivity: Activity() {

    /* Overrides onCreate from Activity */
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent.action == Keys.ACTION_START_PLAYER_SERVICE) {
            // start player service and start playback
            handleStartPlayer()
        }
        finish()
    }


    /* Handles START_PLAYER_SERVICE intent */
    private fun handleStartPlayer() {
        // start player service and start playback
        val startIntent = Intent(this, PlayerService::class.java)
        startIntent.action = Keys.ACTION_START
        if (intent.hasExtra(Keys.EXTRA_STATION_UUID)) {
            val uuid: String? = intent.getStringExtra(Keys.EXTRA_STATION_UUID)
            startIntent.putExtra(Keys.EXTRA_STATION_UUID, uuid)
        } else if (intent.hasExtra(Keys.EXTRA_STREAM_URI)) {
            val streamUri: String? = intent.getStringExtra(Keys.EXTRA_STREAM_URI)
            startIntent.putExtra(Keys.EXTRA_STREAM_URI, streamUri)
        }
        startService(startIntent)
    }

}
