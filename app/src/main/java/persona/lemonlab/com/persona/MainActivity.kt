package persona.lemonlab.com.persona

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.info_dialog.view.*
import persona.lemonlab.com.persona.Extenstions.playSound
import persona.lemonlab.com.persona.models.AddQuestionActivity
import persona.lemonlab.com.persona.models.PVPMatchActivity


class MainActivity : AppCompatActivity() {

    var dialog:Dialog? = null
    var viewDialog:View? = null
    private var onlineTestsAvailable:Boolean = true
    private var canAddQuestions:Boolean = true
    var privacy = "Privacy Policy of Lemon Lab\n" +
            "\n" +
            "Lemon Lab offers, which provides the SERVICE.\n" +
            "\n" +
            "This page is used to inform lemonlab offers visitors regarding our policies with the collection, use, and disclosure of Personal Information if anyone decided to use our Service, the Lemon Lab.\n" +
            "\n" +
            "If you choose to use our Service, then you agree to the collection and use of information in relation with this policy. The Personal Information that we collect are used for providing and improving the Service. We will not use or share your information with anyone except as described in this Privacy Policy.\n" +
            "\n" +
            "The terms used in this Privacy Policy have the same meanings as in our Terms and Conditions, which is accessible at Lemonlab, unless otherwise defined in this Privacy Policy.\n" +
            "\n" +
            "Information Collection and Use\n" +
            "\n" +
            "For a better experience while using our Service, we may require you to provide us with certain personally identifiable information, including but not limited to your name, phone number, and postal address. The information that we collect will be used to contact or identify you.\n" +
            "\n" +
            "Log Data\n" +
            "\n" +
            "We want to inform you that whenever you visit our Service, we collect information that your browser sends to us that is called Log Data. This Log Data may include information such as your computer’s Internet Protocol (\"IP\") address, browser version, pages of our Service that you visit, the time and date of your visit, the time spent on those pages, and other statistics.\n" +
            "\n" +
            "Cookies\n" +
            "\n" +
            "Cookies are files with small amount of data that is commonly used an anonymous unique identifier. These are sent to your browser from the website that you visit and are stored on your computer’s hard drive.\n" +
            "\n" +
            "Our apps uses these \"cookies\" to collection information and to improve our Service. You have the option to either accept or refuse these cookies, and know when a cookie is being sent to your computer. If you choose to refuse our cookies, you may not be able to use some portions of our Service.\n" +
            "\n" +
            "Service Providers\n" +
            "\n" +
            "We may employ third-party companies and individuals due to the following reasons:\n" +
            "\n" +
            "To facilitate our Service;\n" +
            "To provide the Service on our behalf; \n" +
            "To perform Service -related services; \n" +
            "or \n" +
            "To assist us in analyzing how our Service is used.\n" +
            "\n" +
            "We want to inform our Service users that these third parties have access to your Personal Information. The reason is to perform the tasks assigned to them on our behalf. However, they are obligated not to disclose or use the information for any other purpose.\n" +
            "\n" +
            "Security\n" +
            "\n" +
            "We value your trust in providing us your Personal Information, thus we are striving to use commercially acceptable means of protecting it. But remember that no method of transmission over the internet, or method of electronic storage is 100% secure and reliable, and we cannot guarantee its absolute security.\n" +
            "\n" +
            "Links to Other Sites\n" +
            "\n" +
            "Our Service may contain links to other sites. If you click on a third-party link, you will be directed to that site. Note that these external sites are not operated by us. Therefore, we strongly advise you to review the Privacy Policy of these websites. We have no control over, and assume no responsibility for the content, privacy policies, or practices of any third-party sites or services.\n" +
            "\n" +
            "Children’s Privacy\n" +
            "\n" +
            "Our Services do not address anyone under the age of 6. We do not knowingly collect personal identifiable information from children under 6. In the case we discover that a child under 6 has provided us with personal information, we immediately delete this from our servers. If you are a parent or guardian and you are aware that your child has provided us with personal information, please contact us so that we will be able to do necessary actions.\n" +
            "\n" +
            "Changes to This Privacy Policy\n" +
            "\n" +
            "We may update our Privacy Policy from time to time. Thus, we advise you to review this page periodically for any changes. We will notify you of any changes by posting the new Privacy Policy on this page. These changes are effective immediately, after they are posted on this page.\n" +
            "\n" +
            "Contact Us\n" +
            "\n" +
            "If you have any questions or suggestions about our Privacy Policy, do not hesitate to contact us at zaka7024@gmail.com.\n"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getDataFromRemoteConfig()//This is the top priority.

        val first_time = getSharedPreferences("app_data",0).getBoolean("first_time",true)

        if(first_time){
            var intent = Intent(this,splashActivity::class.java)
            startActivity(intent)
        }

        //init
        //by default open page one
        openPageOne()
        privacyPolicy()//privacy Policy Button
        changeName()//Changes user name with shared preferences.
        moreApps()//Goes to Google play dev profile
        isUserComingFromResults()//if coming from results, page two is shown to simplify going back to the lobby(pvp match)

        page_one_btn.setOnClickListener {
            openPageOne()
        }

        page_two_btn.setOnClickListener {
            openPageTwo()
        }

        // set content view for dialog
        viewDialog = LayoutInflater.from(this).inflate(R.layout.info_dialog,null)
        dialog = Dialog(this)
        // When Click on a Topic 01
        //Send Key to QuestionActivity to determine which topic will loaded
        topic_container01.setOnClickListener {
            playSound()
            val intent = Intent(this,QuestionActivity::class.java)
            intent.putExtra("topic",0)
            startActivity(intent)
        }

        topic_container02.setOnClickListener {
            playSound()
            val intent = Intent(this,QuestionActivity::class.java)
            intent.putExtra("topic",1)
            startActivity(intent)
        }

        topic_container03.setOnClickListener {
            playSound()
            val intent = Intent(this,QuestionActivity::class.java)
            intent.putExtra("topic",2)
            startActivity(intent)
        }

        topic_container04.setOnClickListener {
            playSound()
            val intent = Intent(this,QuestionActivity::class.java)
            intent.putExtra("topic",3)
            startActivity(intent)
        }

        user_activity_btn.setOnClickListener {// this is a user question topic
            playSound()
            val intent = Intent(this,UsersActivity::class.java)
            startActivity(intent)
        }

        rate_btn.setOnClickListener {
            playSound()
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("market://details?id=com.lemonlab.persona")
            startActivity(intent);
        }

        share_btn_ActivityMain.setOnClickListener {
            playSound()
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_TEXT,getString(R.string.shareText) + " " +"https://goo.gl/pm4jXh")
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent,"مشاركة الى"))
        }

        // open new activity(add question)
        add_question_btn.setOnClickListener {
            playSound()
            val intent = Intent(this,AddQuestionActivity::class.java)
            startActivity(intent)
        }

        viewDialog!!.privacy_policy.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            dialog.setMessage(privacy)
            dialog.setPositiveButton("موافق",DialogInterface.OnClickListener({
                dialog, which ->  dialog.dismiss()
            }))

            dialog.show()
        }

        // for test

        online_quiz_btn.setOnClickListener {
            val intent = Intent(this, PVPMatchActivity::class.java)
            startActivity(intent)
        }
    }

    fun showDialog(view: View){
        playSound()
        viewDialog!!.howto_textView.text = "التطبيق من تطوير فريق مختبر الليمون\n" +
                "إبراهيم محمد العتوم\n" +
                "عبدالرحمن الناصير\n" +
                "احمد زكريا"
        dialog!!.setContentView(viewDialog)
        dialog!!.show()
    }

    fun closeDialog(view: View){
        playSound()
        dialog!!.dismiss()
    }

    private fun openPageOne(){

        main_text.text = getString(R.string.mainFourTests)

        page_one_btn.setImageResource(R.drawable.number_one_hover)
        page_two_btn.setImageResource(R.drawable.number_two)
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
        user_activity_btn.visibility = View.INVISIBLE
        online_quiz_btn.visibility = View.INVISIBLE
        change_name_btn.visibility = View.INVISIBLE
        privacy_policy_btn.visibility = View.INVISIBLE
        more_apps_btn.visibility = View.INVISIBLE

    }

    private fun openPageTwo(){

        main_text.text =  getString(R.string.specialTests)

        page_one_btn.setImageResource(R.drawable.number_one)
        page_two_btn.setImageResource(R.drawable.number_two_hover)
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
        user_activity_btn.visibility = View.VISIBLE
        if(onlineTestsAvailable)//Remote config
            online_quiz_btn.visibility = View.VISIBLE
        change_name_btn.visibility = View.VISIBLE
        privacy_policy_btn.visibility = View.VISIBLE
        more_apps_btn.visibility = View.VISIBLE
    }
    private fun privacyPolicy(){
        val privacyPolicyText =getString(R.string.privacyPolicyText)
        val privacyPolicyTextAr =getString(R.string.privacyPolicyTextAr)
        privacy_policy_btn.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            dialog.setPositiveButton(getString(R.string.ok)) { firstDialog, _ ->
                firstDialog.dismiss()
            }

            dialog.setNegativeButton(getString(R.string.changeLanguage)) {
                firstDialog, _ ->
                val anotherDialog = AlertDialog.Builder(this)
                anotherDialog.setPositiveButton(getString(R.string.okay)) { secondDialog, _ ->
                    secondDialog.dismiss()
                }
                anotherDialog.setTitle(getString(R.string.privacyPolicy))
                anotherDialog.setMessage(privacyPolicyTextAr)
                anotherDialog.show()
                firstDialog.dismiss()
            }
            dialog.setTitle("Privacy Policy and Terms of Service")
            dialog.setMessage(privacyPolicyText)
            dialog.show()
        }
    }
    private fun changeName(){
        change_name_btn.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this).create()
            val dialogView = layoutInflater.inflate(R.layout.edit_text_name, null)

            val newName = dialogView.findViewById<EditText>(R.id.newName) as EditText
            val buttonOkay = dialogView.findViewById<Button>(R.id.buttonSubmit) as Button
            val buttonCancel = dialogView.findViewById<Button>(R.id.buttonCancel) as Button

            buttonCancel.setOnClickListener { dialogBuilder.dismiss() }
            buttonOkay.setOnClickListener {
                if(newName.text.length>2){
                    getSharedPreferences("app_data", 0).edit().apply{
                        putString("username", newName.text.toString().trim())
                        apply()
                }
                    Toast.makeText(this, getString(R.string.nameChanged), Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, getString(R.string.nameMustBeLonger), Toast.LENGTH_SHORT).show()
                }

                dialogBuilder.dismiss()
            }

            dialogBuilder.setView(dialogView)
            dialogBuilder.show()
        }  }
    private fun moreApps(){
        more_apps_btn.setOnClickListener {
        val lemonLabDevPage = Uri.parse("https://play.google.com/store/apps/developer?id=Lemon+Lab")
        val lemonLab = Intent(Intent.ACTION_VIEW, lemonLabDevPage)
        startActivity(lemonLab)
        }
    }
    private fun getDataFromRemoteConfig(){
        val config= FirebaseRemoteConfig.getInstance()
        config.fetch(3600).addOnSuccessListener {
            //When a user has data that is older than 1 hour, update his data. i.e remote changes will be applied after one hour.
            //if you want to see if it works, please change the 3600 to something like 10 seconds(App will get data after 10 seconds if cached data is older than 10 seconds)
            onlineTestsAvailable = config.getBoolean("onlineTests")
            canAddQuestions = config.getBoolean("submitQuestionsFeature")
            config.activateFetched()//if you don't activate fetched data, app will use old data.
            if(!canAddQuestions)
                add_question_btn.visibility = View.GONE
        }

    }
    private fun isUserComingFromResults(){ //if they are coming from results activity, page two should be displayed.
        try{
            val goToPageTwo = intent.extras.getBoolean("goToPageTwo", false)
            if(goToPageTwo)
                openPageTwo()
        }catch (exception:NullPointerException){}
    }
}

