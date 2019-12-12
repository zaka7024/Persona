package persona.lemonlab.com.persona

import android.content.Context
import android.media.MediaPlayer


object Extensions {
    fun Context.playSound() {
        // play sound
        MediaPlayer.create(this, resources.getIdentifier(R.raw::class.java.fields.first().name, "raw", packageName)).start()
    }
}