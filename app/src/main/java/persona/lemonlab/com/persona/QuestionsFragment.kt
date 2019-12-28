package persona.lemonlab.com.persona


import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import kotlinx.android.synthetic.main.fragment_questions.*
import kotlinx.android.synthetic.main.info_dialog.view.*
import persona.lemonlab.com.persona.Extensions.playSound
import persona.lemonlab.com.persona.models.DoorModel
import persona.lemonlab.com.persona.models.Question


class QuestionsFragment : Fragment() {

    private var index = 0
    private var topic: ArrayList<Question>? = null

    private var trav = 0
    private var trov = 0

    private var dialog: Dialog? = null
    private var viewDialog: View? = null
    private var questionType: Int? = null

    private lateinit var choices: ArrayList<AppCompatButton>


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_questions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun init() {
        choices = ArrayList()
        loadAd()
        choices.apply {
            add(a_choice_btn)
            add(b_choice_btn)
            add(c_choice_btn)
            add(d_choice_btn)
        }
        viewDialog = View.inflate(context!!, R.layout.info_dialog, null)

        dialog = Dialog(context!!)

        infoImageView.setOnClickListener {
            context!!.playSound()
            dialog!!.setContentView(viewDialog!!)
            dialog!!.show()
        }
        (viewDialog!!.findViewById(R.id.ok_btn) as AppCompatButton).setOnClickListener {
            dialog!!.dismiss()
        }
        questionType = QuestionsFragmentArgs.fromBundle(arguments!!).topicIndex

        when (questionType) {
            0 -> {
                topic = DoorModel.topic_one
                viewDialog!!.howto_textView.text = DoorModel.infoAboutDoors[2]
            }
            1 -> topic = DoorModel.topic_two
            2 -> topic = DoorModel.topic_three
            3 -> topic = DoorModel.topic_four
        }

        // set text to dialog
        if (questionType!! < DoorModel.infoAboutDoors.size)
            viewDialog!!.howto_textView.text = DoorModel.infoAboutDoors[questionType!!]

        // init the question text with their UI
        setDataToUI(index, topic!!)
        buttonsAnimate()
        for (button in choices)
            button.setOnClickListener {
                selectAnswer(it)
            }
    }

    private fun loadAd() =
            adViewQuestion.loadAd(AdRequest.Builder().build())


    private fun buttonsAnimate() {
        // Reset ScaleX and ScaleY for all Animating Buttons

        for (button in choices) {
            button.scaleX = 0.01f
            button.scaleY = 0.01f
        }
        for (button in choices)
            button.animate().apply {
                scaleY(1.0f)
                scaleX(1.0f)
                duration = 300
            }

    }

    private fun setDataToUI(index: Int, topic: ArrayList<Question>?) {
        if (topic != null) {
            currentQuestionText.text = topic[index].QuestionText
            a_choice_btn.text = topic[index].a.keys.toString().replaceFirst('[', ' ').replaceFirst(']', ' ')
            b_choice_btn.text = topic[index].b.keys.toString().replaceFirst('[', ' ').replaceFirst(']', ' ')
            c_choice_btn.text = topic[index].c.keys.toString().replaceFirst('[', ' ').replaceFirst(']', ' ')
            d_choice_btn.text = topic[index].d.keys.toString().replaceFirst('[', ' ').replaceFirst(']', ' ')
            currentQuestionNumber.text = getString(R.string.index, index + 1)
            questionsCount.text = topic.size.toString()
        }
    }

    private fun selectAnswer(view: View) {

        context!!.playSound()

        // Check If The index is more than the size of any topic
        if (index++ < topic!!.size - 1) {
            setDataToUI(index, topic!!)
            buttonsAnimate()
            when ((view).id) {
                // When Press First Button
                R.id.a_choice_btn -> {
                    if (topic!![index - 1].a.values.first().second)  // If the Change in Trav
                        trav += (topic!![index - 1].a.values.first().first).toString().toInt()
                    else
                        trov += (topic!![index - 1].a.values.first().first).toString().toInt()

                }
                // When Press Second Button
                R.id.b_choice_btn -> {
                    if (topic!![index - 1].b.values.first().second)  // If the Change in Trav
                        trav += (topic!![index - 1].b.values.first().first).toString().toInt()
                    else
                        trov += (topic!![index - 1].b.values.first().first).toString().toInt()

                }
                // When Press Third Button
                R.id.c_choice_btn -> {
                    if (topic!![index - 1].c.values.first().second)  // If the Change in Trav
                        trav += (topic!![index - 1].c.values.first().first).toString().toInt()
                    else
                        trov += (topic!![index - 1].c.values.first().first).toString().toInt()

                }
                // When Press fourth Button
                R.id.d_choice_btn -> {
                    if (topic!![index - 1].d.values.first().second)  // If the Change in Trav
                        trav += (topic!![index - 1].d.values.first().first).toString().toInt()
                    else
                        trov += (topic!![index - 1].d.values.first().first).toString().toInt()

                }
            }
        } else {
            // End Of Questions
            index--
            showResultButton.visibility = View.VISIBLE
            hideUIShowHeart()
            // Open FinalActivity And Show Result
            showResultButton.setOnClickListener {
                context!!.playSound()
                it.findNavController().navigate(QuestionsFragmentDirections.testToResult(trav, trov, questionType!!))
            }
        }


    }

    private fun hideUIShowHeart() {
        love.visibility = View.VISIBLE
        love.alpha = 1f
        thx.visibility = View.VISIBLE
        currentQuestionText.visibility = View.GONE
        for (button in choices)
            button.visibility = View.GONE


        infoImageView.visibility = View.GONE
        currentQuestionNumber.visibility = View.GONE
        questionsCount.visibility = View.GONE

        love.playAnimation()

        showResultButton.animate().apply {
            alpha(1.0f)
            duration = 500
        }
    }


}
