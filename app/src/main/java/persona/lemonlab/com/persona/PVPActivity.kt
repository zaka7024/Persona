package persona.lemonlab.com.persona

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_pvp.*
import persona.lemonlab.com.persona.models.Question
import persona.lemonlab.com.persona.models.questionModel

class PVPActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pvp)

        test()
        buttonsAnimate()
    }

    fun test(){
        host.text = intent.extras.getString("PVP_HOTS_NAME")
        guest.text = intent.extras.getString("PVP_GUEST_NAME")
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

    fun hideOptions(){
        a_answer_btn.visibility = View.INVISIBLE
        b_answer_btn.visibility = View.INVISIBLE
        c_answer_btn.visibility = View.INVISIBLE
        d_answer_btn.visibility = View.INVISIBLE
    }
}
