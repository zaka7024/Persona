package persona.lemonlab.com.persona.models

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_pvpmatch.*
import persona.lemonlab.com.persona.PVPActivity
import persona.lemonlab.com.persona.R
import persona.lemonlab.com.persona.items.QuizItem
import java.util.*
import kotlin.collections.ArrayList

class PVPMatchActivity : AppCompatActivity() {

    var adapter = GroupAdapter<ViewHolder>()
    var myQuizId:String = ""
    var myQuiz:code? = null
    private var randomUserName="" //used to count users, it's random.
    private var userCounterAvailable=false
    companion object {
        var uniqueID=""
        var aQuizID=""
        var createdQuiz=false
        var enteringQuiz=true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pvpmatch)
        userCounterIsAvailable()
        //init
        initQuizAvailableRV()
        getAvailableQuiz()
        coloredPlaceholderText()//Colors two part of the default text shown when there is no tests && handles clicks on hint_text_view_pvp_activity
        // here user can create oen quiz per activity
        new_quiz_btn.setOnClickListener {
            createQuiz()
        }

        //remove quiz from firebase
        remove_quiz_btn.setOnClickListener {
            deleteQuiz()
        }
    }
    private fun userCounterIsAvailable(){
        val config= FirebaseRemoteConfig.getInstance()
        userCounterAvailable = config.getBoolean("userCounter")
        config.fetch(3600).addOnSuccessListener {//When a user has data that is older than 1 hour, update his data. i.e remote changes will be applied after one hour.
            //if you want to see if it works, please change the 3600 to something like 10 seconds(App will get data after 10 seconds if cached data is older than 10 seconds)
            userCounterAvailable = config.getBoolean("userCounter")
            config.activateFetched()//if you don't activate fetched data, app will use old data.

        }
    }
    private fun coloredPlaceholderText(){
        val builder = SpannableStringBuilder()
        val partOne = getString(R.string.no_quizzes_available)
        val partTwo = getString(R.string.yourPersonalityToTheirs)
        val coloredStringOne = SpannableString(partOne)
        val coloredStringTwo = SpannableString(partTwo)
        coloredStringOne.setSpan(ForegroundColorSpan(Color.BLUE), 0, 4, 0)
        coloredStringTwo.setSpan(ForegroundColorSpan(Color.RED), 0, 7, 0)
        builder.append(coloredStringOne, " ", coloredStringTwo)
        hint_text_view_pvp_activity.text = builder
        hint_text_view_pvp_activity.visibility = View.VISIBLE
        quiz_available_rv.visibility = View.INVISIBLE
        hint_text_view_pvp_activity.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_TEXT,getString(R.string.shareText) + " " +"https://goo.gl/pm4jXh")
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent,"مشاركة الى"))
        }
    }
    private fun usersCounter(){
        var usersCount = 0
        randomUserName = getUserName()+UUID.randomUUID().toString()
        val reference= FirebaseDatabase.getInstance().getReference("current_users")

        reference.child(randomUserName.substring(0, 15)).setValue(randomUserName.substring(0, 20))

        reference.addValueEventListener(object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists() && p0.hasChildren()){
                    usersCount = p0.childrenCount.toInt()-1
                    if(usersCount>=1)
                        new_quiz_btn.text = getString(R.string.NewTestAndUsersCounter, usersCount)
                    else
                        new_quiz_btn.text =getString(R.string.new_host)
                }
            }

        })
    }
    private fun removeMe(){
        if(userCounterAvailable && randomUserName.isNotBlank()){
        val reference= FirebaseDatabase.getInstance().getReference("current_users")
        //Removes user from database if they leave this activity
        reference.child(randomUserName.substring(0, 15)).removeValue()}
    }

    override fun onDestroy() {
        removeMe()
        super.onDestroy()
    }
    private fun isConnectedToInternet(): Boolean {//This returns the status of the connection(true/false)
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }
    private fun snackBarIfNoConnection(){
        if(!isConnectedToInternet()){ //If the activity is recreated, this won't show a snack bar if connection is available.
            hint_text_view_pvp_activity.text = getString(R.string.noConnectionTitle)
            quiz_available_rv.visibility = View.INVISIBLE
            hint_text_view_pvp_activity.visibility = View.VISIBLE
            new_quiz_btn.text =getString(R.string.new_host)
            val view= coordinatorLayout as View
            fun connectedSnackBar(){
                val connectedNow = getString(R.string.connectedNow)
                val anotherSnackBar = Snackbar.make(view, connectedNow, Snackbar.LENGTH_INDEFINITE)
                anotherSnackBar.setAction(getString(R.string.restart)){
                    startActivity(Intent(this, PVPMatchActivity::class.java))
                    finish()// recreate() can be used instead of those two lines but this gives some animation.
                }
                anotherSnackBar.setActionTextColor(Color.rgb(0, 140 , 0))
                anotherSnackBar.show()
            }
            val noConnection = getString(R.string.noConnection)
            val snackBar = Snackbar.make(view, noConnection, Snackbar.LENGTH_INDEFINITE)
            snackBar.setAction(getString(R.string.tryAgain)){
                if(!isConnectedToInternet()) // This keeps showing this snack bar until there is a connection
                    snackBarIfNoConnection()
                else
                    connectedSnackBar()
            }
            fun isConnectedNow(){
                Handler().postDelayed({
                    if(isConnectedToInternet())
                        connectedSnackBar()
                    else if(!isConnectedToInternet())
                        isConnectedNow()
                }, 7000)
            }
            snackBar.setActionTextColor(Color.rgb(240, 0 , 0))
            if(!isConnectedToInternet()){
                isConnectedNow()
                snackBar.show()
            }}
    }
    private fun connectionChecker(){
        Handler().postDelayed({
            if(!isConnectedToInternet())
                snackBarIfNoConnection()
            else
                Handler().postDelayed({connectionChecker()}, 4000)
        }, 7000)
    }
    override fun onResume() {
        //Those two(snackBarIfNoConnection,connectionChecker) does the following :
        // If the activity is created/resumed and there was no connection => A snack bar would tell the user there is no connection
        // and the function(connectionChecker) does this : if there is no connection, it shows a snack bar(every 8 seconds),
        //  if there was a connection, it'd call itself again after 7 seconds to check for connection again.(while there is a connection, it's called every 4 seconds)
        //if the function snackBarIfNoConnection is called and there was a no connection, it'd keep checking for connection every 7 seconds(isConnectedNow)
        //if isConnectedNow called connectedSnackBar(i.e, a connection was established) a snack bar with an action to recreate the activity is shown.
        //The recycler view is invisible if there is no internet and a message is shown to the user.
        snackBarIfNoConnection()
        connectionChecker()
        if(userCounterAvailable)//This is linked with remote config. Default value is false
            usersCounter()
        enteringQuiz=true
        deleteQuiz()
        super.onResume()
    }
    override fun onBackPressed() {
        if (myQuizId.isEmpty()){
            removeMe()
            this.finish()
            return
        }

        val dialog = AlertDialog.Builder(this)

        dialog.setTitle(getString(R.string.back_to_main))

        dialog.setMessage(getString(R.string.confirm))

        dialog.setPositiveButton(getString(R.string.yes), object :DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {
                removeMe()
                deleteQuiz()
                this@PVPMatchActivity.finish()
            }

        })

        dialog.setNegativeButton(getString(R.string.no), object :DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {
                return
            }

        })
        dialog.show()
    }

    private fun initQuizAvailableRV(){
        quiz_available_rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        quiz_available_rv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        quiz_available_rv.adapter = adapter
    }

    override fun onPause() {
        if(!enteringQuiz){
            FirebaseDatabase.getInstance().getReference("pvp/$myQuizId").removeValue()
            enteringQuiz=true
        }
        removeMe()
        super.onPause()
    }

    override fun onStop() {
        removeMe()
        if(!enteringQuiz){
            FirebaseDatabase.getInstance().getReference("pvp/$myQuizId").removeValue()
            enteringQuiz=true
        }
        super.onStop()
    }
    private fun createQuiz(){
        quiz_available_rv.visibility = View.VISIBLE
        if (myQuizId.isEmpty() && !isConnectedToInternet() || myQuizId.length>1 && !isConnectedToInternet()){
            Toast.makeText(this, getString(R.string.noConnection), Toast.LENGTH_SHORT).show()
            hint_text_view_pvp_activity.visibility = View.VISIBLE
            return
        }
        else if(myQuizId.isNotEmpty()){
            Toast.makeText(this, getString(R.string.quiz_exists), Toast.LENGTH_SHORT).show()
            return
        }

        myQuizId = UUID.randomUUID().toString()
        aQuizID = myQuizId
        createdQuiz = true
        enteringQuiz = false
        uniqueID = UUID.randomUUID().toString()//to identify the host, this is needed

        val code = code(myQuizId, false, getUserName(),"",true,
                true, ArrayList(), ArrayList())

        val ref = FirebaseDatabase.getInstance().getReference("pvp/$myQuizId")
        ref.setValue(code).addOnCompleteListener {
            Log.i("PVPMatchActivity", "add quiz in data base")
        }

        ref.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    myQuiz = p0.getValue(code::class.java)
                    if(myQuiz!!.isUsed){
                        ref.removeEventListener(this)
                        startOnlineQuizForHost()
                    }
                }
            }

        })
    }

    private fun getAvailableQuiz(){

        progress_pvp_match_activity.visibility = View.VISIBLE
        hint_text_view_pvp_activity.visibility = View.GONE
        quiz_available_rv.visibility = View.VISIBLE

        val ref = FirebaseDatabase.getInstance().getReference("pvp")
        ref.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                adapter.clear()
                if(p0.exists()){
                    val listOfTests = mutableListOf<QuizItem>()
                    progress_pvp_match_activity.visibility = View.GONE
                    hint_text_view_pvp_activity.visibility = View.GONE
                    quiz_available_rv.visibility = View.VISIBLE
                    for (item in p0.children){
                        val quizCode = item.getValue(code::class.java)
                        if(!listOfTests.contains(QuizItem(quizCode!!, myQuizId, this@PVPMatchActivity)) &&
                                quizCode.host_name.isNotBlank())
                            listOfTests.add(QuizItem(quizCode, myQuizId, this@PVPMatchActivity))
                    }
                    for (test in listOfTests)
                        adapter.add(test)
                }else{
                    hint_text_view_pvp_activity.visibility = View.VISIBLE
                    progress_pvp_match_activity.visibility = View.GONE
                }

                //ref.removeEventListener(this)
            }

        })
    }

    fun deleteQuiz(){
        if(myQuizId.isEmpty()) return
        val ref = FirebaseDatabase.getInstance().getReference("pvp/$myQuizId/")
        ref.removeValue()
        hint_text_view_pvp_activity.visibility = View.VISIBLE
        quiz_available_rv.visibility = View.INVISIBLE
        createdQuiz = false
        enteringQuiz = true
        myQuizId = ""
    }

    private fun getUserName():String{
        val ref = getSharedPreferences("app_data", 0)
        return ref.getString("username", "username")
    }

    fun startOnlineQuizForHost(){
        enteringQuiz=true
        val intent = Intent(this, PVPActivity::class.java)
        intent.putExtra("PVP_HOTS_NAME", myQuiz!!.host_name)
        intent.putExtra("PVP_GUEST_NAME", myQuiz!!.guest_name)
        intent.putExtra("PVP_HOST_CODE", myQuizId)
        intent.putExtra("Unique_ID", uniqueID)

        val ref = FirebaseDatabase.getInstance().getReference("pvp/$myQuizId/hostIsHere")
        ref.setValue(true).addOnCompleteListener {
            Log.i("PVPMatchActivity", "host is here: true")
        }

        startActivity(intent)
        finish()
    }
}
