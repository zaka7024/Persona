package persona.lemonlab.com.persona


import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.android.synthetic.main.fragment_main.*
import persona.lemonlab.com.persona.Extensions.playSound


class MainFragment : Fragment() {

    private var onlineTestsAvailable: Boolean = false
    private var canAddQuestions: Boolean = true


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        checkIfFirstTime()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun checkIfFirstTime() {
        val firstTime = context!!.getSharedPreferences("app_data", 0).getBoolean("firstTime", true)
        if (firstTime)
            view!!.findNavController().navigate(MainFragmentDirections.mainToSplash())
        else
            init()
    }

    private fun init() {
        openPageOne()
        moreApps()
        privacyPolicy()
        changeName()
        openPageOne()
        buttonListeners()

    }

    private fun buttonListeners() {

        pageOne.setOnClickListener {
            openPageOne()
        }

        pageTwo.setOnClickListener {
            openPageTwo()
        }

        rateButton.setOnClickListener {
            context!!.playSound()
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://goo.gl/pm4jXh")
            }
            startActivity(intent)
        }

        shareApp.setOnClickListener {
            context!!.playSound()
            val intent = Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_TEXT, getString(R.string.shareText) + " " + "https://goo.gl/pm4jXh")
                type = "text/plain"
            }
            startActivity(Intent.createChooser(intent, getString(R.string.shareTo)))
        }
        onlineQuiz.setOnClickListener {
            context!!.playSound()
        }

        sendQuestion.setOnClickListener {
            context!!.playSound()
            it.findNavController().navigate(MainFragmentDirections.mainToSend())
        }
        usersQuestions.setOnClickListener {
            context!!.playSound()
            it.findNavController().navigate(MainFragmentDirections.mainToUsers())
        }

        val listOfButtons = listOf<View>(topic_container01, topic_container02, topic_container03, topic_container04)
        for (button in listOfButtons)
            button.setOnClickListener {
                context!!.playSound()
                it.findNavController().navigate(MainFragmentDirections.mainToTest().setTopicIndex(listOfButtons.indexOf(button)))
            }

    }

    private fun openPageTwo() {

        getDataFromRemoteConfig()

        textViewSubTitle.text = getString(R.string.specialTests)

        pageOne.setImageResource(R.drawable.number_one)
        pageTwo.setImageResource(R.drawable.number_two_hover)
        // hide all this views
        topic_container01.visibility = View.INVISIBLE
        topic_title_container01.visibility = View.INVISIBLE
        topic_image01.visibility = View.INVISIBLE
        topic_title01.visibility = View.INVISIBLE

        topic_container02.visibility = View.INVISIBLE
        topic_title_container02.visibility = View.INVISIBLE
        topic_image02.visibility = View.INVISIBLE
        topic_title02.visibility = View.INVISIBLE

        topic_container03.visibility = View.INVISIBLE
        topic_title_container03.visibility = View.INVISIBLE
        topic_image03.visibility = View.INVISIBLE
        topic_title03.visibility = View.INVISIBLE

        topic_container04.visibility = View.INVISIBLE
        topic_title_container04.visibility = View.INVISIBLE
        topic_image04.visibility = View.INVISIBLE
        topic_title04.visibility = View.INVISIBLE

        // show other topic
        usersQuestions.visibility = View.VISIBLE
        if (onlineTestsAvailable) // Remote config
            onlineQuiz.visibility = View.VISIBLE
        changeName.visibility = View.VISIBLE
        privacyPolicyButton.visibility = View.VISIBLE
        moreAppsButton.visibility = View.VISIBLE
    }

    private fun getDataFromRemoteConfig() {
        val config = FirebaseRemoteConfig.getInstance()
        config.setDefaultsAsync(R.xml.remote_config_defaults)
        config.fetch(3600).addOnSuccessListener {
            //When a user has data that is older than 1 hour, update his data. i.e remote changes will be applied after one hour.
            //if you want to see if it works, please change the 3600 to something like 10 seconds(App will get data after 10 seconds if cached data is older than 10 seconds)
            onlineTestsAvailable = config.getBoolean("onlineTests")
            canAddQuestions = config.getBoolean("submitQuestionsFeature")
            config.activate()//if you don't activate fetched data, app will use old data.
            if (!canAddQuestions)
                sendQuestion.visibility = View.GONE
        }

    }

    private fun moreApps() {
        moreAppsButton.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=Lemon+Lab")))
        }
    }

    private fun changeName() {
        changeName.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(context!!).create()
            val dialogView = View.inflate(context, R.layout.edit_text_name, null)


            val newName = dialogView.findViewById(R.id.newName) as EditText
            val buttonOkay = dialogView.findViewById(R.id.buttonSubmit) as Button
            val buttonCancel = dialogView.findViewById(R.id.buttonCancel) as Button

            buttonCancel.setOnClickListener { dialogBuilder.dismiss() }
            buttonOkay.setOnClickListener {
                if (newName.text.length > 2) {
                    context!!.getSharedPreferences("app_data", 0).edit().apply {
                        putString("username", newName.text.toString().trim())
                        apply()
                    }
                    Toast.makeText(context!!, getString(R.string.nameChanged), Toast.LENGTH_SHORT).show()
                } else
                    Toast.makeText(context!!, getString(R.string.nameMustBeLonger), Toast.LENGTH_SHORT).show()


                dialogBuilder.dismiss()
            }

            dialogBuilder.setView(dialogView)
            dialogBuilder.show()
        }
    }

    private fun privacyPolicy() {
        val privacyPolicyText = getString(R.string.privacyPolicyText)
        val privacyPolicyTextAr = getString(R.string.privacyPolicyTextAr)
        privacyPolicyButton.setOnClickListener {
            val dialog = AlertDialog.Builder(context!!)
            dialog.setPositiveButton(getString(R.string.ok)) { firstDialog, _ ->
                firstDialog.dismiss()
            }

            dialog.setNegativeButton(getString(R.string.changeLanguage)) { firstDialog, _ ->
                val anotherDialog = AlertDialog.Builder(context!!)
                anotherDialog.setPositiveButton(getString(R.string.okay)) { secondDialog, _ ->
                    secondDialog.dismiss()
                }
                anotherDialog.setTitle(getString(R.string.privacyPolicy))
                anotherDialog.setMessage(privacyPolicyTextAr)
                anotherDialog.show()
                firstDialog.dismiss()
            }
            dialog.setTitle(getString(R.string.privacy_title))
            dialog.setMessage(privacyPolicyText)
            dialog.show()
        }
    }

    private fun openPageOne() {

        getDataFromRemoteConfig()

        textViewSubTitle.text = getString(R.string.mainFourTests)

        pageOne.setImageResource(R.drawable.number_one_hover)
        pageTwo.setImageResource(R.drawable.number_two)
        // show all this views
        topic_container01.visibility = View.VISIBLE
        topic_title_container01.visibility = View.VISIBLE
        topic_image01.visibility = View.VISIBLE
        topic_title01.visibility = View.VISIBLE

        topic_container02.visibility = View.VISIBLE
        topic_title_container02.visibility = View.VISIBLE
        topic_image02.visibility = View.VISIBLE
        topic_title02.visibility = View.VISIBLE

        topic_container03.visibility = View.VISIBLE
        topic_title_container03.visibility = View.VISIBLE
        topic_image03.visibility = View.VISIBLE
        topic_title03.visibility = View.VISIBLE

        topic_container04.visibility = View.VISIBLE
        topic_title_container04.visibility = View.VISIBLE
        topic_image04.visibility = View.VISIBLE
        topic_title04.visibility = View.VISIBLE

        //hide page two topic
        usersQuestions.visibility = View.INVISIBLE
        onlineQuiz.visibility = View.INVISIBLE
        changeName.visibility = View.INVISIBLE
        privacyPolicyButton.visibility = View.INVISIBLE
        moreAppsButton.visibility = View.INVISIBLE

    }

}
