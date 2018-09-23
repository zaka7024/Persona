package persona.lemonlab.com.persona

import android.content.Context
import android.media.MediaPlayer

/**
 * Created by HP on 07/09/2018.
 */
object Extenstions {
    fun Context.playSound(){
        // play sound
        var player = MediaPlayer.create(this,resources.getIdentifier(R.raw::class.java.fields.first().name,"raw",packageName))
        player.start()
    }
}