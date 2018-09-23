package persona.lemonlab.com.persona

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity

class LemonLabActivity : AppCompatActivity() {

        private var mDelayHandler: Handler? = null
        private val SPLASH_DELAY: Long = 1700 //3 seconds

        internal val mRunnable: Runnable = Runnable {
            if (!isFinishing) {

                var first_time = getSharedPreferences("app_data",0).getBoolean("first_time",true)

                if(first_time){
                    var intent = Intent(this,splashActivity::class.java)
                    startActivity(intent)
                }else{
                    var intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                }
                finish()
            }
        }

            override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_lemon_lab)

                //Initialize the Handler
                mDelayHandler = Handler()

                //Navigate with delay
                mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)

        }

    public override fun onDestroy() {

        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }
}
