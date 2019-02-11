package persona.lemonlab.com.persona

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import kotlinx.android.synthetic.main.activity_final.*
import persona.lemonlab.com.persona.Extenstions.playSound
import persona.lemonlab.com.persona.models.DoorModel
import java.util.*

class FinalActivity : AppCompatActivity() {

    var men_images = arrayOf(R.drawable.person01,R.drawable.person02,R.drawable.person05)
    var women_images = arrayOf(R.drawable.person03,R.drawable.person04,R.drawable.person6)

    var trav:Int? = null
    var trov:Int? = null

    var username:String? = null

    private lateinit var mInterstitialAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final)

        // init the ad
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-9769401692194876/8396267839"
        mInterstitialAd.loadAd(AdRequest.Builder().build())

        //init the AdView
        val request = AdRequest.Builder().build()
        adViewFinalActivity.loadAd(request)

        // get gender and username then show random image
        val data = getSharedPreferences("app_data",0)
        val gender = data.getString("usergender","male")
        val topicIndex = intent.extras.getInt("topic",0)
        username = data.getString("username", "المستخدم")

        Log.i("quiz","topic index: " + topicIndex.toString())

        if(gender == "male"){
            final_person_imageview.setImageResource(men_images[Random().nextInt(men_images.size)])
        }else{
            final_person_imageview.setImageResource(women_images[Random().nextInt(women_images.size)])
        }

        // return to home
        home_btn.setOnClickListener {
            // play sound
            playSound()
            // show ad
            mInterstitialAd.show()
            this@FinalActivity.finish()
        }

        // get Data from QuestionActivity And Show Final Result
         trav = intent.extras.getInt("trav")
         trov = intent.extras.getInt("trov")

        // show result and Animate Scroll Image
        when (topicIndex) {
            0 -> showResultForTopicOne()
            1 -> showResultForTopicTwo()
            2 -> showResultForTopicThree()
            else -> showResultForTopicFour()
        }
        scrollImageAnimateToTop()
    }

    private fun showResultForTopicOne(){
        // set the value of the main title of this activity with user name
        main_title_finalActivity.text = getString(R.string.resultForUsername, username)

        if(trav!! > trov!!  + 7 ){
            final_result_textview.text = DoorModel.topic_one_result[0]
        }else if(trov!! > trav!!  + 7 ){
            final_result_textview.text = DoorModel.topic_one_result[1]
        }else{
            final_result_textview.text = DoorModel.topic_one_result[2]
        }
    }

    private fun showResultForTopicTwo(){

        // set the value of the main title of this activity with user name
        main_title_finalActivity.text = getString(R.string.resultForUsername, username)

        if(trav!! > trov!!  + 7 ){
            final_result_textview.text = DoorModel.topic_two_result[0]
        }else if(trov!! > trav!!  + 7 ){
            final_result_textview.text = DoorModel.topic_two_result[1]
        }else{
            final_result_textview.text = DoorModel.topic_two_result[2]
        }
    }

    private fun showResultForTopicThree(){
        // set the value of the main title of this activity with user name
        main_title_finalActivity.text = getString(R.string.resultForUsername, username)

        if(trav!! > trov!!  + 7 ){
            final_result_textview.text = DoorModel.topic_three_result[0]
        }else if(trov!! > trav!!  + 7 ){
            final_result_textview.text = DoorModel.topic_three_result[1]
        }else{
            final_result_textview.text = DoorModel.topic_three_result[2]
        }
    }

    private fun showResultForTopicFour(){
        // set the value of the main title of this activity with user name
        main_title_finalActivity.text = getString(R.string.resultForUsername, username)

        if(trav!! > trov!!  + 7 ){
            final_result_textview.text = DoorModel.topic_four_result[0]
        }else if(trov!! > trav!!  + 7 ){
            final_result_textview.text = DoorModel.topic_four_result[1]
        }else{
            final_result_textview.text = DoorModel.topic_four_result[2]
        }
    }

    private fun scrollImageAnimateToTop(){
        scroll_imageView.animate().apply {
            translationY(-400f)
            duration = 2400
        }.alpha(0f)

    }

    fun shareResult(view:View){
        // play sound
        playSound()
        var intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_TEXT, final_result_textview.text.toString())
        intent.type = "text/plain"
        startActivity(Intent.createChooser(intent,"مشاركة الى"))
    }
}
