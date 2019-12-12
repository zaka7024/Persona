package persona.lemonlab.com.persona

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.gms.ads.AdRequest
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_send.*
import persona.lemonlab.com.persona.Extensions.playSound
import persona.lemonlab.com.persona.models.QuestionModel


class SendFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_send, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sendQuestion()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun loadAd() =
            adViewSend.loadAd(AdRequest.Builder().build())

    private fun sendQuestion() {
        loadAd()
        cancel.setOnClickListener {
            it.findNavController().navigate(SendFragmentDirections.sendToMain())
        }
        sendNow.setOnClickListener {
            // play sound
            context!!.playSound()

            // check if all fields are empty
            if (questionText.text!!.trim().isEmpty() || choice1.text!!.trim().isEmpty()
                    || choice2.text!!.trim().isEmpty() || choice3.text!!.trim().isEmpty()
                    || choice4.text!!.trim().isEmpty()) {//No more questions with empty answers.
                Toast.makeText(context!!, getString(R.string.fillAllFields_please), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // show progressbar
            progressBar.visibility = View.VISIBLE

            // connect to database
            val database = FirebaseDatabase.getInstance().getReference("questions")

            val itemID = database.push().key
            val newQuestion = QuestionModel(itemID.toString(),
                    questionText.text.toString(),
                    choice1.text.toString(),
                    choice2.text.toString(),
                    choice3.text.toString(),
                    choice4.text.toString())


            // reset fields
            questionText.text!!.clear()
            choice1.text!!.clear()
            choice2.text!!.clear()
            choice3.text!!.clear()
            choice4.text!!.clear()
            database.child(itemID!!).setValue(newQuestion).addOnCompleteListener {
                Toast.makeText(context!!, getString(R.string.yourQuestionIsSent),
                        Toast.LENGTH_LONG).show()
                if (view != null)
                    progressBar.visibility = View.INVISIBLE
            }
        }

    }
}
