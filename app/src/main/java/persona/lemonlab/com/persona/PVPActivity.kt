package persona.lemonlab.com.persona

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_pvp.*
import persona.lemonlab.com.persona.models.OnlineQuestion
import persona.lemonlab.com.persona.models.code


class PVPActivity : AppCompatActivity() {

    var hostCode:String = ""
    var hostName:String = ""

    private lateinit var onlineQuestions:MutableList<OnlineQuestion>
    private lateinit var listOfQuestionsTexts:Array<String>
    private lateinit var listOfAnswerTexts:Array<String>
    private lateinit var listOfAnswerEffects:Array<Pair<Int, Int>> //How much does this answer a, b, c, d affect x and y ?
    private var firstQuestion:Boolean = true //Needed to  initialize onlineQuestions,this bool is false after that.
    //Those four are needed to determine the final result for the two users
    private var xValue=0
    private var yValue=0
    private var xValueOther=0
    private var yValueOther=0

    private var quizID = ""//This is empty in guest device. It can be used to identify which user is which.
    //Those to move to the next question and determine the next answers to show.
    private var questionPosition = 0
    private var answerPosition = 0
    private var hostProgressPath =""//Get data from host, to prevent similar host names from causing problems. This is A UUID.randomUUID().toString()
    //Those are needed to check whether the test is finished or not.
    private var hostPosition = 0
    private var guestPosition = 0
    private var removeQuizWhenExit= true
    private var everyoneIsHere = true//This would be a false if someone leaves.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pvp)

        hostCode = intent.extras.getString("PVP_HOST_CODE","")
        host_progress.text = getString(R.string.progress_string,"1")//This shall prevent see null at the beginning of the test
        guest_progress.text = getString(R.string.progress_string,"1")
        test()
        buttonsAnimate()
        checkIfHostAndGuestIsHere()
        getAllQuestionsAndAnswers()//Initialize variables
        handleQuestions()//answers, data and more.
    }

    override fun onBackPressed() {
        Log.i("PVPActivity", "on back -> host name: ${getHostName()}")
        if(removeQuizWhenExit)
            removeQuiz()
        super.onBackPressed()
    }


    private fun isConnectedToInternet(): Boolean { //returns the state of the connection.
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }
    private fun snackBarIfNoConnection(){
        val noConnection = getString(R.string.noConnection) //This is called every second and a half to ensure there is a connection
        val view= coordinatorLayoutPVP as View //See the R.layout.activity_pvp id.
        val snackBar = Snackbar.make(view, noConnection, Snackbar.LENGTH_LONG)
        snackBar.setAction(getString(R.string.okay)){
            try {Handler().postDelayed({snackBarIfNoConnection()}, 1500)
            }catch (e:Exception){}
        }
        snackBar.setActionTextColor(Color.rgb(240, 0 , 0))
        if(!isConnectedToInternet()){
            snackBar.show()
        }
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
        incrementValues()//To get to next Question and show next answers
        getQuestion()//update list
        val listOfAnswerButtons = listOf<Button>(a_answer_btn, b_answer_btn, c_answer_btn, d_answer_btn)
        question_text_textView.text = onlineQuestions[questionPosition].questionText
        for (item in listOfAnswerButtons){
            when(item){ //Update Answers text accordingly.
                a_answer_btn -> item.text = onlineQuestions[questionPosition].firstAnswer.first
                b_answer_btn -> item.text = onlineQuestions[questionPosition].secondAnswer.first
                c_answer_btn -> item.text = onlineQuestions[questionPosition].thirdAnswer.first
                d_answer_btn -> item.text = onlineQuestions[questionPosition].fourthAnswer.first
            }
        }

    }
    private fun updateTextWithoutIncrementation(){ //This replaces the place holder texts with the first question, no need increment any values.
        getQuestion() // A rise starts at zero. Arrays start at zero, too.
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
                if(p0.exists()) //To ensure that the two of them completed the quiz
                    if(p0.value.toString()=="true" && everyoneIsHere)
                        goToResults()//seeResults is a Boolean, if it's true, that means both of them has finished the test.
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
    private fun isEveryoneHere(){
        val reference =  FirebaseDatabase.getInstance().getReference("pvp/$hostCode")
        reference.addValueEventListener(object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                everyoneIsHere = p0.child("hostIsHere").value.toString().toBoolean() && p0.child("guestIsHere").value.toString().toBoolean()
                // This would ensure that if one of the two has left, the other won't add any data to the database(the person who left won't come back)
            }
        })
    }

    private fun getHostData(){
        val reference = FirebaseDatabase.getInstance().getReference("pvp/$hostCode")
        reference.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    try{//try can be removed, but you have to use p0.child("${hostProgressPath}_xValue").exists() instead of if p0.exists()
                        xValueOther = p0.child("${hostProgressPath}_xValue").value.toString().toInt()  //hostProgressPath is the same as quizID.
                        yValueOther = p0.child("${hostProgressPath}_yValue").value.toString().toInt()}catch (e:NumberFormatException){}
                }
                else{
                    reference.removeEventListener(this)
                    reference.child("hostIsHere").addValueEventListener(object:ValueEventListener{
                        override fun onDataChange(p0: DataSnapshot) {
                            if(p0.exists())
                                if(p0.value.toString().toBoolean())
                                    getHostData()
                        }
                        override fun onCancelled(p0: DatabaseError) {}
                    })
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
                        yValueOther = p0.child("${guest.text}_yValue").value.toString().toInt()}catch (e:NumberFormatException){}
                }
                else{
                    reference.removeEventListener(this)
                    reference.child("guestIsHere").addValueEventListener(object:ValueEventListener{
                        override fun onDataChange(p0: DataSnapshot) {
                            if(p0.exists())
                                if(p0.value.toString().toBoolean())
                                    getGuestData()
                        }
                        override fun onCancelled(p0: DatabaseError) {}
                    })
                }
            }
        })
    }
    private fun handleQuestions() {
        val listOfAnswerButtons = listOf<Button>(a_answer_btn, b_answer_btn, c_answer_btn, d_answer_btn)
        getHostName()
        updateTextWithoutIncrementation()
        isEveryoneHere()
        hostProgressUpdater()
        for (item in listOfAnswerButtons) {
            item.setOnClickListener {
                getGuestData()
                getHostData()
                finishedYet()// goes to next question and when the test is completed this shows the "See results" button.
                if(everyoneIsHere)
                    hostProgressUpdater()
                else
                    removeQuiz()
                when (item) {
                    a_answer_btn -> { //Each answer has its own effect on x, y(final results).
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
        snackBarIfNoConnection()
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
    }
    private fun hostProgressUpdater() {
        //since I'm the host, I want to track my guest progress and send them mine
        if (quizID.isNotEmpty()) {
            guest.setTextColor(Color.BLUE)//The other person name color is blue. Always
            host.setTextColor(Color.RED)//user will always see their name in red
            val anotherReference = FirebaseDatabase.getInstance().getReference("pvp/$hostCode")
            val reference = FirebaseDatabase.getInstance().getReference("pvp/$hostCode/progress/${guest.text}_currentProgress")
            addData(anotherReference, quizID, xValue, yValue)// data is sent earlier than the progress. This would ensure
            //that every bit of data is sent before showing the final results(No different results)
            reference.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {
                    getGuestData()//If the progress is changed, then their data(x, y) values are updated so let's get some new data.
                    if(p0.value.toString()=="null")
                        guest_progress.text = getString(R.string.progress_string,"1")//No data yet(Guest hasn't answered any question so their progress is 1/20)
                    else
                        guest_progress.text = getString(R.string.progress_string, p0.value.toString())
                    guestPosition = try {
                        p0.value.toString().toInt()
                    } catch (e: Exception) {
                        questionPosition
                    }
                    host_progress.text = getString(R.string.progress_string, (questionPosition + 1).toString())
                    if (guestPosition >= 20 && questionPosition + 1 >= 20) {
                        anotherReference.child("/seeResults").setValue(true)
                        see_result_btn.visibility = View.VISIBLE
                        see_result_btn.text = getString(R.string.show_result)
                        removeQuizWhenExit = false
                        see_result_btn.setOnClickListener {
                            if(questionPosition>=19 && guestPosition>=19)
                            seeResults()
                        }
                    }
                }
            })
            val ref = FirebaseDatabase.getInstance().getReference("pvp/$hostCode/progress")
            ref.child("/${quizID}_currentProgress").setValue(questionPosition + 1)//Using quizID instead of hostName to handle the received data if there is someone with the same hostName
        } else {
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
                    guest.setTextColor(Color.RED)//user will always see their name in red
                    host.setTextColor(Color.BLUE)//user will always see the other name in blue
                    addData(anotherReference, guest.text.toString(), xValue, yValue) // data is sent earlier than the progress. This would ensure
                                                                                    //that every bit of data is sent before showing the final results(No different results)
                    val reference = FirebaseDatabase.getInstance().getReference(
                            "pvp/$hostCode/progress/${hostProgressPath}_currentProgress")//this is a unique id (created when a host opens a test) to prevent similar names from causing problems.
                            //This unique ID is also used to identify the host. in the host device it's quizID, in the guest device it's hostProgressPath.
                    reference.addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {}
                        override fun onDataChange(p0: DataSnapshot) {
                            getHostData()//If the progress is changed, then their data(x, y) values are updated so let's get some new data.
                            if(p0.value.toString()=="null")
                                host_progress.text = getString(R.string.progress_string,"1")//No data yet(Host hasn't answered any question so their progress is 1/20)
                            else
                                host_progress.text = getString(R.string.progress_string, p0.value.toString()) //This takes an argument, see strings.xml progress_string.
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
                                removeQuizWhenExit=false
                                see_result_btn.setOnClickListener {
                                    if(questionPosition>=19 && hostPosition>=19)
                                        seeResults()
                                }
                            }
                        }
                    })
                }
            }
        })
        val ref = FirebaseDatabase.getInstance().getReference("pvp/$hostCode/progress")
        ref.child("/${guest.text}_currentProgress").setValue(questionPosition+1)
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
            override fun onCancelled(p0: DatabaseError) {}

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
                            if(removeQuizWhenExit)
                                removeQuiz()
                            ref.removeEventListener(this)
                        }

                    }else{ // this device is the guest

                        if(pvp_code.hostIsHere){
                            host.setTextColor(Color.BLUE)
                            guest.setTextColor(Color.RED)
                        }else{
                            host.setTextColor(Color.RED)
                            guest.setTextColor(Color.BLUE)
                            if(removeQuizWhenExit)
                                removeQuiz()
                            ref.removeEventListener(this)
                        }

                    }
                    Log.i("PVPActivity", "host is here (in quiz): ${pvp_code!!.hostIsHere}")
                    Log.i("PVPActivity", "guest is here (in quiz): ${pvp_code!!.guestIsHere}")
                }else if(removeQuizWhenExit){
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

    fun getUserName():String{
        val ref = getSharedPreferences("app_data", 0)
        val name = ref.getString("username", "المضيف")
        Log.i("PVPActivity", "current device user name :$name")
        return name
    }

    private fun hostLeftQuiz(){
        val ref = FirebaseDatabase.getInstance().getReference("pvp/$hostCode")
        ref.child("hostIsHere").setValue(false)
    }

    private fun guestLeftQuiz(){
        val ref = FirebaseDatabase.getInstance().getReference("pvp/$hostCode")
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

    override fun onStop() {
        if(!everyoneIsHere)
            removeQuiz()
        super.onStop()
    }
    fun removeQuiz(){

        if (hostCode.isEmpty())return

        /*val host_left = FirebaseDatabase.getInstance().getReference("pvp/${hostCode}/hostIsHere")
        val guest_left = FirebaseDatabase.getInstance().getReference("pvp/${hostCode}/guestIsHere")
        host_left.setValue(false)
        guest_left.setValue(false)*/
        if(hostPosition<19 && guestPosition<19)
            Toast.makeText(this, getString(R.string.testCancelled), Toast.LENGTH_LONG).show()


        val ref = FirebaseDatabase.getInstance().getReference("pvp/$hostCode")
        ref.removeValue()
        this.finish()
    }
}
