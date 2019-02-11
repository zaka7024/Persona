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

        // add question to FirebaseDatabase
        send_question_btn.setOnClickListener {
            // play sound
            playSound()

            // check if all fields are empty
            if(question_edittext.text.trim().isEmpty() || choose_edittext_01.text.trim().isEmpty()
                    || choose_edittext_02.text.trim().isEmpty() || choose_edittext_03.text.trim().isEmpty()
                    || choose_edittext_04.text.trim().isEmpty()){//No more questions with empty answers.
                Toast.makeText(this@AddQuestionActivity,getString(R.string.fillAllFields_please),Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // show progressbar
            progressBar.visibility = View.VISIBLE

            // connect to FirebaseDatabase
            val database = FirebaseDatabase.getInstance().getReference("questions")

            val itemID = database.push().key
            val newQuestion = questionModel(itemID.toString(),
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

            database.child(itemID!!).setValue(newQuestion).addOnCompleteListener {
                Toast.makeText(this@AddQuestionActivity,getString(R.string.yourQuestionIsSent),
                        Toast.LENGTH_LONG).show()
                progressBar.visibility = View.INVISIBLE
            }
        }
    }
}
