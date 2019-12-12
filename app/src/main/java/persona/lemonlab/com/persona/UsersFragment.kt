package persona.lemonlab.com.persona


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_users.*
import persona.lemonlab.com.persona.models.QuestionModel


class UsersFragment : Fragment() {

    private lateinit var choices: ArrayList<AppCompatButton>
    private var onlineData: ArrayList<QuestionModel>? = null
    private var index = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun init() {
        loadAd()
        choices = ArrayList()
        choices.apply {
            add(a_choice)
            add(b_choice)
            add(c_choice)
            add(d_choice)
        }
        getData()
        for (button in choices) {
            button.setOnClickListener {
                selectAnswer()
            }
        }
        buttonsAnimate()
    }

    private fun loadAd() =
            adViewUsers.loadAd(AdRequest.Builder().build())

    private fun getData() {
        val ref = FirebaseDatabase.getInstance().getReference("questions")
        onlineData = ArrayList()
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (item in p0.children)
                    onlineData!!.add(item.getValue(QuestionModel::class.java)!!)
                // all questions are here
                if (view != null)
                    usersProgressBar.visibility = View.GONE
                onlineData!!.reverse()
                setDataToUI(index, onlineData!!)
            }

        })
    }

    private fun setDataToUI(index: Int, data: ArrayList<QuestionModel>) {

        if (data[index].toString().trim().isEmpty())
            userQuestionText.text = "المستخدم لم يكتب نص للسؤال"
        else
            userQuestionText.text = data[index].questionText


        clearOptionText(data[index].a, a_choice)
        clearOptionText(data[index].b, b_choice)
        clearOptionText(data[index].c, c_choice)
        clearOptionText(data[index].d, d_choice)

    }

    private fun clearOptionText(text: String, view: TextView) = if (text.trim().isEmpty())
        view.text = getString(R.string.no_choice)
    else
        view.text = text


    private fun hideOptions() {
        for (button in choices)
            button.visibility = View.INVISIBLE
    }

    private fun buttonsAnimate() {
        // Reset ScaleX and ScaleY for all Animating Buttons

        for (button in choices) {
            button.scaleX = 0.01f
            button.scaleY = 0.01f
        }
        for (button in choices) {
            button.animate().apply {
                scaleY(1.0f)
                scaleX(1.0f)
                duration = 300
            }
        }

    }

    private fun selectAnswer() {
        if (index++ < onlineData!!.size - 1) {
            setDataToUI(index, onlineData!!)
            buttonsAnimate()
        } else {
            userQuestionText.text = getString(R.string.no_more_questions)
            hideOptions()
        }
    }

}
