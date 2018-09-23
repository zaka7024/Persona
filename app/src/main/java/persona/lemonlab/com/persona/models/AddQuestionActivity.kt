package persona.lemonlab.com.persona.models

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_question.*
import persona.lemonlab.com.persona.Extenstions.playSound
import persona.lemonlab.com.persona.R

class AddQuestionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_question)

        // add question to Firebasedatabase
        send_question_btn.setOnClickListener {
            // play sound
            playSound()

            // show progressbar
            progressBar.visibility = View.VISIBLE

            // check if all fields are empty
            if(question_edittext.text.trim().isEmpty()&& ( choose_edittext_01.text.trim().isEmpty()
                    || choose_edittext_02.text.trim().isEmpty() || choose_edittext_03.text.trim().isEmpty()
                    || choose_edittext_04.text.trim().isEmpty() )){
                Toast.makeText(this@AddQuestionActivity,"الرجاء كتابة السؤال على الاقل",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // connect to Fitebasedatabase
            var database = FirebaseDatabase.getInstance().getReference("questions")

            var item_id = database.push().key
            var new_question:questionModel = questionModel(item_id.toString(),
                    question_edittext.text.toString(),
                    choose_edittext_01.text.toString(),
                    choose_edittext_02.text.toString(),
                    choose_edittext_03.text.toString(),
                    choose_edittext_04.text.toString())


            // reset fields
            question_edittext.setText("")
            choose_edittext_01.setText("")
            choose_edittext_02.setText("")
            choose_edittext_03.setText("")
            choose_edittext_04.setText("")

            database.child(item_id!!).setValue(new_question).addOnCompleteListener {
                Toast.makeText(this@AddQuestionActivity,"لقد تم إرسال سؤالك، شكرًا على المساهمة في تطوير التطبيق",
                        Toast.LENGTH_LONG).show()
                progressBar.visibility = View.INVISIBLE
            }
        }
    }
}
