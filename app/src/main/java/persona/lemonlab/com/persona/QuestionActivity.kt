package persona.lemonlab.com.persona

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import kotlinx.android.synthetic.main.activity_question.*
import kotlinx.android.synthetic.main.info_dialog.view.*
import persona.lemonlab.com.persona.Extenstions.playSound
import persona.lemonlab.com.persona.models.DoorModel
import persona.lemonlab.com.persona.models.Question
import persona.lemonlab.com.persona.models.questionModel

class QuestionActivity : AppCompatActivity() {

    var index = 0
    var curentTopic:ArrayList<Question>? = null
    var onlineData:ArrayList<questionModel>? = null
    var trav = 0
    var trov = 0

    var dialog:Dialog? = null
    var viewDialgo:View? = null
    var questionType:Int? = null

    private lateinit var mInterstitialAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        //init the adView
        var request = AdRequest.Builder().build()
        adView_pvpActivity.loadAd(request)

        // init the ad
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-9769401692194876/8396267839"
        mInterstitialAd.loadAd(AdRequest.Builder().build())

        viewDialgo = LayoutInflater.from(this@QuestionActivity).inflate(R.layout.info_dialog,null)
        // Animate the buttons
        buttonsAnimate()

        // Get Data From  MainActivity
        // Determine which topic is selected from the value that will came from MainActivity
        questionType = intent.getIntExtra("topic",0)

        if(questionType == 0){
            curentTopic = DoorModel.topic_one
            viewDialgo!!.howto_textView.text = DoorModel.addtionInformationAboutDoors[2]

        }else if(questionType == 1){
            curentTopic = DoorModel.topic_two

        }else if(questionType == 2){
            curentTopic = DoorModel.topic_three
        }else if (questionType == 3){
            curentTopic = DoorModel.topic_four
        }

        // set text to dialog
        if(questionType!! < DoorModel.addtionInformationAboutDoors.size)
            viewDialgo!!.howto_textView.text = DoorModel.addtionInformationAboutDoors[questionType!!]

        // init the question text with their UI
        setDataToUI(index,curentTopic!!)

        // Show HowToUse Dialog
        dialog = Dialog(this@QuestionActivity)

    }

    // init Text With UI
    private fun setDataToUI(index:Int, topic:ArrayList<Question>?){
        if (topic != null){
            question_text_textView.text = topic!![index].QuestionText
            a_answer_btn.text = topic[index]!!.a.keys.toString().replaceFirst('[',' ').replaceFirst(']',' ')
            b_answer_btn.text = topic[index].b.keys.toString().replaceFirst('[',' ').replaceFirst(']',' ')
            c_answer_btn.text = topic[index].c.keys.toString().replaceFirst('[',' ').replaceFirst(']',' ')
            d_answer_btn.text = topic[index].d.keys.toString().replaceFirst('[',' ').replaceFirst(']',' ')
            curent_question_textView.text = (index + 1).toString() + "/"
            total_questions_textView.text = topic.size.toString()
        }
    }
    // Animate the button
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
    // select an answer and move to next answer until to the end of question to show result
    fun selectAnswer(view:View){

        playSound()

        // Check If The index is more than the sise of any topic
        if(index++ < curentTopic!!.size - 1){
            setDataToUI(index,curentTopic!!)
            buttonsAnimate()
            when((view as TextView).id){
                // When Press First Button
                R.id.a_answer_btn ->{
                    if(curentTopic!![index  - 1].a.values.first().second){ // If the Change in Trav
                        trav += (curentTopic!![index  - 1].a.values.first().first).toString().toInt()
                    }else{
                        trov += (curentTopic!![index  - 1].a.values.first().first).toString().toInt()
                    }
                }
                // When Press Second Button
                R.id.b_answer_btn -> {
                    if(curentTopic!![index  - 1].b.values.first().second){ // If the Change in Trav
                        trav += (curentTopic!![index  - 1].b.values.first().first).toString().toInt()
                    }else{
                        trov += (curentTopic!![index  - 1].b.values.first().first).toString().toInt()
                    }
                }
                // When Press Third Button
                R.id.c_answer_btn ->{
                    if(curentTopic!![index  - 1].c.values.first().second){ // If the Change in Trav
                        trav += (curentTopic!![index  - 1].c.values.first().first).toString().toInt()
                    }else{
                        trov += (curentTopic!![index  - 1].c.values.first().first).toString().toInt()
                    }
                }
                // When Press fourth Button
                R.id.d_answer_btn -> {
                    if(curentTopic!![index  - 1].d.values.first().second){ // If the Change in Trav
                        trav += (curentTopic!![index  - 1].d.values.first().first).toString().toInt()
                    }else{
                        trov += (curentTopic!![index  - 1].d.values.first().first).toString().toInt()
                    }
                }
            }
        }else{
            // End Of Questions
            index--
            show_result_btn.visibility = View.VISIBLE
            hideUIshowHeart()
            // Open FinalActivity And Show Result
            show_result_btn.setOnClickListener{
                // play sound
                playSound()
                var intent = Intent(this,FinalActivity::class.java)
                intent.putExtra("trav",trav)
                intent.putExtra("trov",trov)
                intent.putExtra("topic",questionType)
                this@QuestionActivity.finish()
                startActivity(intent)
            }
        }

        // Log Messages
        Log.i("quiz",index.toString())
        Log.i("quiz",curentTopic!![index  - 1].a.keys.toString())
        Log.i("quiz",curentTopic!![index  - 1].a.values.first().second.toString())
        Log.i("quiz","trav: $trav")
        Log.i("quiz","trov: $trov")
    }

    fun hideUIshowHeart(){
        love.visibility = View.VISIBLE
        love.alpha = 1f
        thx.visibility = View.VISIBLE
        question_text_textView.visibility = View.GONE
        a_answer_btn.visibility = View.GONE
        b_answer_btn.visibility = View.GONE
        c_answer_btn.visibility = View.GONE
        d_answer_btn.visibility = View.GONE

        info_imageView.visibility = View.GONE
        curent_question_textView.visibility = View.GONE
        total_questions_textView.visibility = View.GONE

        love.playAnimation()

        show_result_btn.animate().apply {
            alpha(1.0f)
            duration = 800
        }
    }

    fun showDialog(view:View){
        // play sound
        playSound()
        dialog!!.setContentView(viewDialgo)
        dialog!!.show()
    }

    fun closeDialog(view:View){
        dialog!!.dismiss()
        // play sound
        playSound()
    }

     override fun onBackPressed() {
        super.onBackPressed()
         mInterstitialAd.show()
    }
}
