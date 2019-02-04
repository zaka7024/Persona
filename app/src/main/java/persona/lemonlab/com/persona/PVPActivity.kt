package persona.lemonlab.com.persona

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_pvp.*
import persona.lemonlab.com.persona.models.OnlineQuestion
import persona.lemonlab.com.persona.models.Question
import persona.lemonlab.com.persona.models.code
import java.lang.Exception
import java.util.*

class PVPActivity : AppCompatActivity() {

    var hostCode:String = ""
    var hostName:String = ""

    private lateinit var onlineQuestions:MutableList<OnlineQuestion>
    private lateinit var listOfQuestionsTexts:Array<String>
    private lateinit var listOfAnswerTexts:Array<String>
    private lateinit var listOfAnswerEffects:Array<Pair<Int, Int>> //How much does this answer a, b, c, d affect x and y ?
    private var firstQuestion:Boolean = true //Needed to  initialize onlineQuestions,this bool is false after that.
    //Those two are needed to determine the final result
    private var xValue=0
    private var yValue=0
    private var xValueOther=0
    private var yValueOther=0

    private var quizID = ""
    //Those to move to the next question and determine the next answers to show.
    private var questionPosition = 0
    private var answerPosition = 0
    private var hostProgressPath =""
    //Those are needed to check whether the test is finished or not.
    private var hostPosition = 0
    private var guestPosition = 0
    private var exitWhenDestroyed= true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pvp)

        hostCode = intent.extras.getString("PVP_HOST_CODE","")

        test()
        buttonsAnimate()
        checkIfHostAndGuestIsHere()
        getAllQuestionsAndAnswers()
        handleQuestions()
    }

    override fun onBackPressed() {
        Log.i("PVPActivity", "on back -> host name: ${getHostName()}")
        if(exitWhenDestroyed)
            removeQuiz()
        else if(quizID.isNotEmpty() && !exitWhenDestroyed){
            val ref = FirebaseDatabase.getInstance().getReference("pvp/$hostCode");ref.removeValue() }
        super.onBackPressed()
    }
    private fun getAllQuestionsAndAnswers(){
        see_result_btn.visibility = View.GONE
        listOfQuestionsTexts = resources.getStringArray(R.array.online_questions)
        listOfAnswerTexts = resources.getStringArray(R.array.online_question_answers)
        //this determines how many points to add to xValue(first) and yValue(second) according to the answer
        listOfAnswerEffects = arrayOf(Pair(3,0),Pair(2,0),Pair(0,2),Pair(0,0),
                Pair(1,0),Pair(0,0),Pair(0,0),Pair(2,0),
                Pair(2,0),Pair(0,2),Pair(0,3),Pair(0,0),
                Pair(0,2),Pair(0,1),Pair(2,0),Pair(0,0),
                Pair(0,0),Pair(2,0),Pair(0,2),Pair(0,0),
                Pair(2,0),Pair(0,3),Pair(0,0),Pair(3,0),
                Pair(2,0),Pair(3,2),Pair(0,2),Pair(0,0),
                Pair(2,0),Pair(0,0),Pair(0,0),Pair(0,3),
                Pair(0,2),Pair(2,0),Pair(1,2),Pair(0,0),
                Pair(0,0),Pair(2,0),Pair(0,0),Pair(0,3),
                Pair(0,0),Pair(0,3),Pair(0,0),Pair(2,0),
                Pair(3,0),Pair(0,0),Pair(0,0),Pair(0,2),
                Pair(2,0),Pair(0,0),Pair(0,0),Pair(0,2),
                Pair(0,3),Pair(0,2),Pair(0,0),Pair(1,0),
                Pair(0,0),Pair(0,0),Pair(0,0),Pair(0,0),
                Pair(0,0),Pair(0,0),Pair(0,3),Pair(2,0),
                Pair(2,0),Pair(0,2),Pair(0,0),Pair(0,0),
                Pair(0,2),Pair(0,2),Pair(0,0),Pair(2,0),
                Pair(0,2),Pair(0,0),Pair(3,0),Pair(0,3),
                Pair(0,0),Pair(0,0),Pair(0,0),Pair(0,0)) //Last Answer has no effect, users currently can skip this
    }
    private fun getQuestion(){
        if(firstQuestion) {
            onlineQuestions = mutableListOf(OnlineQuestion(listOfQuestionsTexts[questionPosition],
                    Pair(listOfAnswerTexts[answerPosition], listOfAnswerEffects[answerPosition]),
                    Pair(listOfAnswerTexts[answerPosition + 1], listOfAnswerEffects[answerPosition + 1]),
                    Pair(listOfAnswerTexts[answerPosition + 2], listOfAnswerEffects[answerPosition + 2]),
                    Pair(listOfAnswerTexts[answerPosition + 3], listOfAnswerEffects[answerPosition + 3])))
            firstQuestion=false
        }else{
        onlineQuestions.add(OnlineQuestion(listOfQuestionsTexts[questionPosition],
                Pair(listOfAnswerTexts[answerPosition], listOfAnswerEffects[answerPosition]),
                Pair(listOfAnswerTexts[answerPosition+1], listOfAnswerEffects[answerPosition+1]),
                Pair(listOfAnswerTexts[answerPosition+2], listOfAnswerEffects[answerPosition+2]),
                Pair(listOfAnswerTexts[answerPosition+3], listOfAnswerEffects[answerPosition+3])))
        }
    }
    private fun incrementValues(){
        questionPosition +=1
        answerPosition +=4
    }
    private fun updateTexts(){
        incrementValues()
        getQuestion()
        val listOfAnswerButtons = listOf<Button>(a_answer_btn, b_answer_btn, c_answer_btn, d_answer_btn)
        question_text_textView.text = onlineQuestions[questionPosition].questionText
        for (item in listOfAnswerButtons){
            when(item){
                a_answer_btn -> item.text = onlineQuestions[questionPosition].firstAnswer.first
                b_answer_btn -> item.text = onlineQuestions[questionPosition].secondAnswer.first
                c_answer_btn -> item.text = onlineQuestions[questionPosition].thirdAnswer.first
                d_answer_btn -> item.text = onlineQuestions[questionPosition].fourthAnswer.first
            }
        }

    }
    private fun updateTextWithoutIncrementation(){ //This replaces the place holder texts with the first question, no need increment any values.
        getQuestion()
        val listOfAnswerButtons = listOf<Button>(a_answer_btn, b_answer_btn, c_answer_btn, d_answer_btn)
        question_text_textView.text = onlineQuestions[questionPosition].questionText
        for (item in listOfAnswerButtons){
            when(item){
                a_answer_btn -> item.text = onlineQuestions[questionPosition].firstAnswer.first
                b_answer_btn -> item.text = onlineQuestions[questionPosition].secondAnswer.first
                c_answer_btn -> item.text = onlineQuestions[questionPosition].thirdAnswer.first
                d_answer_btn -> item.text = onlineQuestions[questionPosition].fourthAnswer.first
            }
        }
    }
    private fun seeResults(){
        fun goToResults(){
            val intent = Intent(this, Results::class.java)
            intent.putExtra("result_x", xValue)
            intent.putExtra("result_y", yValue)
            intent.putExtra("another_x", xValueOther)
            intent.putExtra("another_y", yValueOther)
            intent.putExtra("quiz_ID", quizID)
            intent.putExtra("host_code", hostCode)
            startActivity(intent)
            finish()
        }
        val reference = FirebaseDatabase.getInstance().getReference("pvp/$hostCode/seeResults")
        reference.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists())//To Ensure that both of the two have completed the quiz
                    if(p0.value.toString()=="true")
                        goToResults()
                    else{
                        seeResults()
                        reference.removeEventListener(this)
                    }
            }
        })
    }
    private fun addData(reference:DatabaseReference, path:String, x:Int, y:Int){
        //I guess we have to update data after every answer,
        // so when getting data it'd exist. This prevents an unexpected behavior as one of test takers may get stuck in this activity
        reference.child("${path}_xValue").setValue(x)
        reference.child("${path}_yValue").setValue(y)
    }
    private fun getHostData(){
        val reference = FirebaseDatabase.getInstance().getReference("pvp/$hostCode")
        reference.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    try{
                        //hostProgressPath is the same as quizID.
                        xValueOther = p0.child("${hostProgressPath}_xValue").value.toString().toInt()
                        yValueOther = p0.child("${hostProgressPath}_yValue").value.toString().toInt()}catch (e:Exception){}
                }
                else{
                    reference.removeEventListener(this)
                    getHostData()
                }
            }
        })

    }
    private fun getGuestData(){
        val reference = FirebaseDatabase.getInstance().getReference("pvp/$hostCode")
        reference.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    try{
                        xValueOther = p0.child("${guest.text}_xValue").value.toString().toInt()
                        yValueOther = p0.child("${guest.text}_yValue").value.toString().toInt()}catch (e:Exception){}
                }
                else{
                    reference.removeEventListener(this)
                    getGuestData()
                }
            }
        })
    }
    private fun handleQuestions() {
        val listOfAnswerButtons = listOf<Button>(a_answer_btn, b_answer_btn, c_answer_btn, d_answer_btn)
        getHostName()
        updateTextWithoutIncrementation()
        hostProgressUpdater()
        getGuestData()
        getHostData()
            for (item in listOfAnswerButtons) {
                item.setOnClickListener {
                    finishedYet()// goes to next question and when the test is completed this shows the "See results" button.
                    when (item) {
                        a_answer_btn -> {
                            xValue += onlineQuestions[questionPosition].firstAnswer.second.first
                            yValue += onlineQuestions[questionPosition].firstAnswer.second.second
                        }
                        b_answer_btn -> {
                            xValue += onlineQuestions[questionPosition].secondAnswer.second.first
                            yValue += onlineQuestions[questionPosition].secondAnswer.second.second
                        }
                        c_answer_btn -> {
                            xValue += onlineQuestions[questionPosition].thirdAnswer.second.first
                            yValue += onlineQuestions[questionPosition].thirdAnswer.second.second
                        }
                        d_answer_btn -> {
                            xValue += onlineQuestions[questionPosition].fourthAnswer.second.first
                            yValue += onlineQuestions[questionPosition].fourthAnswer.second.second
                        }
                    }
                }
            }
    }
    private fun finishedYet(){
        if(questionPosition+1>=20){

            seeResults()
            val listOfAnswerButtons = listOf<Button>(a_answer_btn, b_answer_btn, c_answer_btn, d_answer_btn)
            for(item in listOfAnswerButtons)
                item.visibility = View.GONE
            question_text_textView.visibility = View.GONE
            see_result_btn.visibility = View.VISIBLE
            if(quizID.isNotEmpty())
                see_result_btn.text = getString(R.string.other_still_in_quiz, guest.text)
            else
                see_result_btn.text = getString(R.string.other_still_in_quiz, hostName)

        }else{
            updateTexts()
        }
        hostProgressUpdater()
    }
    private fun hostProgressUpdater() {
        //since I'm the host, I want to track my guest progress and send them mine
        if(quizID.isNotEmpty()){
            val anotherReference = FirebaseDatabase.getInstance().getReference("pvp/$hostCode")
            val reference = FirebaseDatabase.getInstance().getReference("pvp/$hostCode/progress/${guest.text}_currentProgress")
            reference.addValueEventListener(object :ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {
                    guest_progress.text = getString(R.string.progress_string, p0.value.toString())
                    guestPosition = try{
                        p0.value.toString().toInt()
                    }catch (e:Exception){
                        questionPosition
                    }
                    host_progress.text = getString(R.string.progress_string, (questionPosition+1).toString())
                    if(guestPosition>=20 && questionPosition+1>=20){
                        anotherReference.child("/seeResults").setValue(true)
                        see_result_btn.visibility = View.VISIBLE
                        see_result_btn.text =getString(R.string.show_result)
                        exitWhenDestroyed=false
                        see_result_btn.setOnClickListener {
                            seeResults()
                        }
                        getGuestData()
                    }
                }
            })
            val ref = FirebaseDatabase.getInstance().getReference("pvp/$hostCode/progress")
            ref.child("/${quizID}_currentProgress").setValue(questionPosition+1)
            addData(anotherReference,quizID, xValue, yValue)
        }else{
            guestProgressUpdater()
        }
    }

    private fun guestProgressUpdater() {
        //I'm the guest so I need to send my progress and track my host's
        val referenceOne = FirebaseDatabase.getInstance().getReference("pvp/$hostCode/host_quiz_ID")
        val anotherReference = FirebaseDatabase.getInstance().getReference("pvp/$hostCode")
        referenceOne.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                hostProgressPath = p0.value.toString()
                if (guest.text.toString() == getUserName()) {
                    val reference = FirebaseDatabase.getInstance().getReference("pvp/$hostCode/progress/${hostProgressPath}_currentProgress")
                    reference.addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {}
                        override fun onDataChange(p0: DataSnapshot) {
                            host_progress.text = getString(R.string.progress_string, p0.value.toString())
                            hostPosition = try{
                                p0.value.toString().toInt()
                            }catch (e:Exception){
                                questionPosition
                            }
                            guest_progress.text = getString(R.string.progress_string, (questionPosition+1).toString())
                            if(hostPosition>=20 && questionPosition+1>=20){
                                anotherReference.child("/seeResults").setValue(true)
                                see_result_btn.visibility = View.VISIBLE
                                see_result_btn.text =getString(R.string.show_result)
                                exitWhenDestroyed=false
                                see_result_btn.setOnClickListener {
                                    seeResults()
                                }
                                getHostData()
                            }
                        }
                    })
                }
            }
        })
        val ref = FirebaseDatabase.getInstance().getReference("pvp/$hostCode/progress")
        ref.child("/${guest.text}_currentProgress").setValue(questionPosition+1)
        addData(anotherReference, guest.text.toString(), xValue, yValue)
    }
    private fun test(){
        host.text = intent.extras.getString("PVP_HOTS_NAME")
        guest.text = intent.extras.getString("PVP_GUEST_NAME")
        try{ //DON'T edit this unless you know what you are doing.
            quizID = intent.extras.getString("Unique_ID")
            if(quizID.isNotEmpty()){
                val ref = FirebaseDatabase.getInstance().getReference("pvp/$hostCode")
                ref.child("/host_quiz_ID").setValue(quizID) //quizID is needed to access the host's progress.
            }
        }catch (e:Exception){ }
    }

    private fun checkIfHostAndGuestIsHere(){
        val ref = FirebaseDatabase.getInstance().getReference("pvp/$hostCode")
        var currentUserName = getUserName()
        ref.addValueEventListener(object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    var pvp_code = p0.getValue(code::class.java)
                    if (pvp_code!!.host_name == currentUserName){ // this device is the host

                        if(pvp_code.guestIsHere){
                            guest.setTextColor(Color.BLUE)
                            host.setTextColor(Color.RED)
                        }else{
                            guest.setTextColor(Color.BLUE)
                            host.setTextColor(Color.RED)
                            if(exitWhenDestroyed)
                                removeQuiz()
                            ref.removeEventListener(this)
                        }

                    }else{ // this device is the guest

                        if(pvp_code.hostIsHere){
                            host.setTextColor(Color.BLUE)
                            host.setTextColor(Color.RED)
                        }else{
                            host.setTextColor(Color.RED)
                            guest.setTextColor(Color.BLUE)
                            if(exitWhenDestroyed)
                                removeQuiz()
                            ref.removeEventListener(this)
                        }

                    }
                    Log.i("PVPActivity", "host is here (in quiz): ${pvp_code!!.hostIsHere}")
                    Log.i("PVPActivity", "guest is here (in quiz): ${pvp_code!!.guestIsHere}")
                }else if(exitWhenDestroyed){
                    removeQuiz()
                }
            }

        })
    }



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

    fun setDataToUI(index:Int, data:ArrayList<Question>){
        // we do not know until this moment how online questions will be
    }

    fun getUserName():String{
        val ref = getSharedPreferences("app_data", 0)
        var name = ref.getString("username", "المضيف")
        Log.i("PVPActivity", "current device user name :${name}")
        return name
    }

     fun hostLeftQuiz(){
        val ref = FirebaseDatabase.getInstance().getReference("pvp/$hostCode/")
        ref.child("hostIsHere").setValue(false)
    }

    fun guestLeftQuiz(){
        val ref = FirebaseDatabase.getInstance().getReference("pvp/$hostCode/")
        ref.child("guestIsHere").setValue(false)
    }

    fun hostBackToQuiz(){
        val ref = FirebaseDatabase.getInstance().getReference("pvp/$hostCode/")
        ref.child("hostIsHere").setValue(true)
    }

    fun guestBackToQuiz(){
        val ref = FirebaseDatabase.getInstance().getReference("pvp/$hostCode/")
        ref.child("guestIsHere").setValue(true)
    }

    private fun getHostName(){
        val ref = FirebaseDatabase.getInstance().getReference("pvp/$hostCode/host_name")
        ref.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    hostName =  p0.getValue(String::class.java)!!
                    ref.removeEventListener(this)
                }
            }

        })
    }

    fun removeQuiz(){

        if (hostCode.isEmpty())return

        /*val host_left = FirebaseDatabase.getInstance().getReference("pvp/${hostCode}/hostIsHere")
        val guest_left = FirebaseDatabase.getInstance().getReference("pvp/${hostCode}/guestIsHere")
        host_left.setValue(false)
        guest_left.setValue(false)*/

        Toast.makeText(this, "لقد غادر شخص ما الاختبار", Toast.LENGTH_LONG).show()

        val ref = FirebaseDatabase.getInstance().getReference("pvp/$hostCode")
        ref.removeValue()
        this.finish()
    }
}
