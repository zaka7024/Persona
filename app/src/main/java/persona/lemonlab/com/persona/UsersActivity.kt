package persona.lemonlab.com.persona

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_question.*
import persona.lemonlab.com.persona.models.questionModel

class UsersActivity : AppCompatActivity() {

    var onlineData:ArrayList<questionModel>? = null
    var index = 0
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        getDataFromFirebase()

        buttonsAnimate()
    }

    fun getDataFromFirebase(){
        val ref = FirebaseDatabase.getInstance().getReference("questions")
        onlineData = ArrayList()
        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                for( item in p0.children){
                    var question = item.getValue(questionModel::class.java)
                    Log.i("UsersActivity", "user question: ${question!!.questionText}")
                    onlineData!!.add(question!!)
                }
                onlineData!!.reverse()
                setDataToUI(index, onlineData!!)
            }

        })
    }

    fun selectAnswer(view:View){
        if(index++ < onlineData!!.size - 1){
            setDataToUI(index,onlineData!!)
            buttonsAnimate()
            when((view as TextView).id){
                // When Press First Button
                R.id.a_answer_btn ->{

                }
                // When Press Second Button
                R.id.b_answer_btn -> {

                }
                // When Press Third Button
                R.id.c_answer_btn ->{

                }
                // When Press fourth Button
                R.id.d_answer_btn -> {

                }
            }
        }else{
            question_text_textView.text = "انتهت جميع الاسئلة انتظر وصول اسئلة جديدة من المستخدمين"
            hideOptions()
        }
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

    fun setDataToUI(index:Int, data:ArrayList<questionModel>){
        question_text_textView.text = data[index].questionText
        a_answer_btn.text = data[index].a
        b_answer_btn.text = data[index].b
        c_answer_btn.text = data[index].c
        d_answer_btn.text = data[index].d
    }

    fun hideOptions(){
        a_answer_btn.visibility = View.INVISIBLE
        b_answer_btn.visibility = View.INVISIBLE
        c_answer_btn.visibility = View.INVISIBLE
        d_answer_btn.visibility = View.INVISIBLE
    }
}
