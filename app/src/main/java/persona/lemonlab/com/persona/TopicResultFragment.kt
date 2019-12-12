package persona.lemonlab.com.persona


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.fragment_topic_result.*
import persona.lemonlab.com.persona.Extensions.playSound
import persona.lemonlab.com.persona.models.DoorModel
import java.util.*


class TopicResultFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_topic_result, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun scrollImageAnimateToTop() {
        scrollImageView.animate().apply {
            translationY(-400f)
            duration = 2400
        }.alpha(0f)

    }

    private fun init() {
        loadAd()
        val args = TopicResultFragmentArgs.fromBundle(arguments!!)

        val menImages = arrayOf(R.drawable.person01, R.drawable.person02, R.drawable.person05)
        val womenImages = arrayOf(R.drawable.person03, R.drawable.person04, R.drawable.person6)

        val data = context!!.getSharedPreferences("app_data", 0)
        val gender = data.getString("userGender", "Male")
        val topicIndex = args.topicIndex
        val userName = data.getString("username", getString(R.string.user))

        val firstValue = args.firstValue
        val secondValue = args.secondValue

        if (Gender.valueOf(gender!!) == Gender.Male)
            topicResultPerson.setImageResource(menImages[Random().nextInt(menImages.size)])
        else
            topicResultPerson.setImageResource(womenImages[Random().nextInt(womenImages.size)])

        homeButton.setOnClickListener {
            // play sound
            context!!.playSound()
            it.findNavController().navigate(TopicResultFragmentDirections.resultToMain())
        }
        // set the value of the main title of this activity with user name
        mainTitleResult.text = getString(R.string.resultForUsername, userName)
        showResult(topicIndex, firstValue, secondValue)
        scrollImageAnimateToTop()

        shareTopicResult.setOnClickListener {
            shareResult()
        }

    }

    private fun showResult(topicIndex: Int, firstValue: Int, secondValue: Int) {
        val model =
                when (topicIndex) {
                    0 -> DoorModel.topic_one_result
                    1 -> DoorModel.topic_two_result
                    2 -> DoorModel.topic_three_result
                    else -> DoorModel.topic_four_result
                }
        when {
            firstValue > secondValue + 7 -> topicResultText.text = model[0]
            secondValue > firstValue + 7 -> topicResultText.text = model[1]
            else -> topicResultText.text = model[2]
        }

    }


    private fun loadAd() =
            adViewResult.loadAd(AdRequest.Builder().build())

    private fun shareResult() {
        context!!.playSound()
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_TEXT, topicResultText.text.toString())
        intent.type = "text/plain"
        startActivity(Intent.createChooser(intent, getString(R.string.shareTo)))
    }

}
