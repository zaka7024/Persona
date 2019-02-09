package persona.lemonlab.com.persona

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.android.gms.ads.AdRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_results.*
import java.lang.Math.abs

class Results : AppCompatActivity() {
    private var quizID=""
    private var hostCode=""
    private var hostFinished = false
    private var guestFinished = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        //init

        var request = AdRequest.Builder().build()
        adView_ResultActivity.loadAd(request)

        setTextAccordingToResult()
        iSawMyResults()
    }

    private fun iSawMyResults(){
        if(quizID.isNotEmpty())
            FirebaseDatabase.getInstance().getReference("pvp/$hostCode/results/hostFinished").setValue(true).addOnCompleteListener {
                deleteOnlineData()
            }
        else
            FirebaseDatabase.getInstance().getReference("pvp/$hostCode/results/guestFinished").setValue(true).addOnCompleteListener{
                deleteOnlineData()
            }
    }

    private fun deleteOnlineData(){
        val reference = FirebaseDatabase.getInstance().getReference("pvp/$hostCode/results/")
        reference.addValueEventListener(object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.child("guestFinished").exists())
                    guestFinished = p0.child("guestFinished").value.toString().toBoolean()
                if(p0.child("hostFinished").exists())
                    hostFinished = p0.child("hostFinished").value.toString().toBoolean()
                else{
                    reference.removeEventListener(this)
                    deleteOnlineData()
                }
            }
        })
        if(hostFinished && guestFinished){
            FirebaseDatabase.getInstance().getReference("pvp/$hostCode").removeValue()
        }
    }
    override fun onBackPressed() {
        deleteOnlineData()
        super.onBackPressed()
    }
    //Receives values from the previous activity to give a result here.
    private fun setTextAccordingToResult() {
        hostCode = intent.extras.getString("host_code", "")
        val xValue = intent.extras.getInt("result_x", 0)
        val yValue = intent.extras.getInt("result_y", 0)
        val xValueOther = intent.extras.getInt("another_x", 0)
        val yValueOther = intent.extras.getInt("another_y", 0)
        quizID = intent.extras.getString("quiz_ID", "")
        close_results.setOnClickListener {
            deleteOnlineData()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        val listOfResults = resources.getStringArray(R.array.results)

        Log.i("finalResults", "x is : $xValue, $xValueOther")
        Log.i("finalResults", "y is : $yValue, $yValueOther")
        val deltaX:Int
        val deltaY:Int
        if(quizID.length>1){ //Host Device(quidID is empty in the guest device)
            deltaX = abs(xValue-xValueOther)
            deltaY = abs(yValue-yValueOther)
        }
        else{ //Guest Device
            deltaX = abs(xValueOther-xValue)
            deltaY = abs(yValueOther-yValue)
        }
        if(deltaX<4 && deltaY<4)
            result_text.text = listOfResults[3]
        else if(deltaX>5 && deltaY>5)
            result_text.text = listOfResults[2]
        else if(deltaX<5 && deltaY>5)
            result_text.text = listOfResults[1]
        else
            result_text.text = listOfResults[0]
    }
}

