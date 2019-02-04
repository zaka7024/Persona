package persona.lemonlab.com.persona

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_results.*
import java.lang.Math.abs

class Results : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)
        setTextAccordingToResult()
    }
    //Receives values from the previous activity to give a result here.
    private fun setTextAccordingToResult() {
        close_results.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            this.finish()
        }
        val listOfResults = resources.getStringArray(R.array.results)
        val xValue = intent.extras.getInt("result_x", 0)
        val yValue = intent.extras.getInt("result_y", 0)
        val xValueOther = intent.extras.getInt("another_x", 0)
        val yValueOther = intent.extras.getInt("another_y", 0)
        val quizID = intent.extras.getString("quiz_ID", "")
        val hostCode = intent.extras.getString("host_code", "")
        Log.i("finalResults", "x is : $xValue, $xValueOther")
        Log.i("finalResults", "y is : $yValue, $yValueOther")
        val deltaX:Int
        val deltaY:Int
        if(quizID.length>1){ //Host Device(quidID is empty in the guest device)
            deltaX = abs(xValue-xValueOther)
            deltaY = abs(yValue-yValueOther)
            FirebaseDatabase.getInstance().getReference("pvp/$hostCode").removeValue()
        }
        else{ //Guest Device
            deltaX = abs(xValueOther-xValue)
            deltaY = abs(yValueOther-yValue)
        }
        if(deltaX<4 && deltaY<4)
            result_text.text = listOfResults[3]
        else if(deltaX<6 && deltaY<6)
            result_text.text = listOfResults[2]
        else if(deltaX<5 && deltaY>5)
            result_text.text = listOfResults[1]
        else
            result_text.text = listOfResults[0]
    }
}

