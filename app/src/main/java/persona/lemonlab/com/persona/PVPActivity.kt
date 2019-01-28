package persona.lemonlab.com.persona

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_pvp.*
import persona.lemonlab.com.persona.models.Question
import persona.lemonlab.com.persona.models.code
import persona.lemonlab.com.persona.models.questionModel

class PVPActivity : AppCompatActivity() {

    var hostCode:String = ""
    var hostName:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pvp)

        hostCode = intent.extras.getString("PVP_HOST_CODE","")

        test()
        buttonsAnimate()
        getHostName()
        checkIfHostAndGuestIsHere()
    }

    override fun onBackPressed() {
        Log.i("PVPActivity", "on back -> host name: ${getHostName()}")
        removeQuiz()
        super.onBackPressed()
    }


    fun test(){
        host.text = intent.extras.getString("PVP_HOTS_NAME")
        guest.text = intent.extras.getString("PVP_GUEST_NAME")
    }

    fun checkIfHostAndGuestIsHere(){
        val ref = FirebaseDatabase.getInstance().getReference("pvp/${hostCode}")
        var currentUserNmae = getUserName()

        ref.addValueEventListener(object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    var pvp_code = p0.getValue(code::class.java)

                    if (pvp_code!!.host_name == currentUserNmae){ // this device is the host

                        if(pvp_code!!.guestIsHere){
                            guest.setTextColor(resources.getColor(R.color.green))
                        }else{
                            guest.setTextColor(resources.getColor(R.color.red))
                            removeQuiz()
                            ref.removeEventListener(this)
                        }

                    }else{ // this device is the guest

                        if(pvp_code!!.hostIsHere){
                            host.setTextColor(resources.getColor(R.color.green))
                        }else{
                            host.setTextColor(resources.getColor(R.color.red))
                            removeQuiz()
                            ref.removeEventListener(this)
                        }

                    }
                    Log.i("PVPActivity", "host is here (in quiz): ${pvp_code!!.hostIsHere}")
                    Log.i("PVPActivity", "guest is here (in quiz): ${pvp_code!!.guestIsHere}")
                }else{
                    this@PVPActivity.finish()
                }
            }

        })
    }



    private fun buttonsAnimate(){
        // Reset ScaleX and ScaleY for all Animating Buttons
        a_answer_btn.scaleX = 0.01f
        a_answer_btn.scaleY = 0.01f
        b_answer_btn.scaleX = 0.01f
        b_answer_btn.scaleY = 0.01f
        c_answer_btn.scaleX = 0.01f
        c_answer_btn.scaleY = 0.01f
        d_answer_btn.scaleX = 0.01f
        d_answer_btn.scaleY = 0.01f

        a_answer_btn.animate().apply{
            scaleY(1.0f)
            scaleX(1.0f)
            duration = 600
        }

        b_answer_btn.animate().apply{
            scaleY(1.0f)
            scaleX(1.0f)
            duration = 700
        }

        c_answer_btn.animate().apply{
            scaleY(1.0f)
            scaleX(1.0f)
            duration = 800
        }

        d_answer_btn.animate().apply{
            scaleY(1.0f)
            scaleX(1.0f)
            duration = 900
        }
    }

    fun setDataToUI(index:Int, data:ArrayList<Question>){
        // we do not know until this moment how online questions will be
    }

    fun getUserName():String{
        val ref = getSharedPreferences("app_data", 0)
        var name = ref.getString("username", "المضيف")
        Log.i("PVPActivity", "current device user name :${name}")
        return name
    }

    /* fun hostLeftQuiz(){
        val ref = FirebaseDatabase.getInstance().getReference("pvp/${hostCode}/hostIsHere")
        ref.setValue(false)
    }

    fun guestLeftQuiz(){
        val ref = FirebaseDatabase.getInstance().getReference("pvp/${hostCode}/guestIsHere")
        ref.setValue(false)
    }

    fun hostBackToQuiz(){
        val ref = FirebaseDatabase.getInstance().getReference("pvp/${hostCode}/hostIsHere")
        ref.setValue(true)
    }

    fun questBackToQiuz(){
        val ref = FirebaseDatabase.getInstance().getReference("pvp/${hostCode}/guestIsHere")
        ref.setValue(true)
    } */

    fun getHostName(){
        val ref = FirebaseDatabase.getInstance().getReference("pvp/${hostCode}/host_name")
        ref.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                hostName =  p0.getValue(String::class.java)!!
                ref.removeEventListener(this)
            }

        })
    }

    fun removeQuiz(){

        if (hostCode.isEmpty())return

        /*val host_left = FirebaseDatabase.getInstance().getReference("pvp/${hostCode}/hostIsHere")
        val guest_left = FirebaseDatabase.getInstance().getReference("pvp/${hostCode}/guestIsHere")
        host_left.setValue(false)
        guest_left.setValue(false)*/

        Toast.makeText(this, "لقد غادر شخص ما الاختبار", Toast.LENGTH_LONG).show()

        val ref = FirebaseDatabase.getInstance().getReference("pvp/${hostCode}")
        ref.removeValue()
        this.finish()
    }
}
