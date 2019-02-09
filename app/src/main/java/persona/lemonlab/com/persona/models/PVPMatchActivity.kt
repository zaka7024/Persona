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
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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
    companion object {
        var uniqueID=""
        var aQuizID=""
        var createdQuiz=false
        var enteringQuiz=true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pvpmatch)
        //init
        initQuizAvailableRV()
        getAvailableQuiz()

        // here user can create oen quiz per activity
        new_quiz_btn.setOnClickListener {
            createQuiz()
        }

        //remove quiz from firebase
        remove_quiz_btn.setOnClickListener {
            deleteQuiz()
        }
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
                    else
                        isConnectedNow()
                }, 4000)
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
        }, 8000)
    }
    override fun onResume() {
        //Those two(snackBarIfNoConnection,connectionChecker) does the following :
        // If the activity is created/resumed and there was no connection => A snack bar would tell the user there is no connection
        // and the function(connectionChecker) does this : if there is no connection, it shows a snack bar(every 8 seconds),
        //  if there was a connection, it'd call itself again after 7 seconds to check for connection again.(while there is a connection, it's called every 4 seconds)
        //if the function snackBarIfNoConnection is called and there was a no connection, it'd keep checking for connection every 4 seconds(isConnectedNow)
        //if isConnectedNow called connectedSnackBar(i.e, a connection was established) a snack bar with an action to recreate the activity is shown.
        //The recycler view is invisible if there is no internet and a message is shown to the user.
        snackBarIfNoConnection()
        connectionChecker()
        enteringQuiz=true
        deleteQuiz()
        super.onResume()
    }
    override fun onBackPressed() {
        if (myQuizId.isEmpty()){
            this.finish()
            return
        }

        val dialog = AlertDialog.Builder(this)

        dialog.setTitle(getString(R.string.back_to_main))

        dialog.setMessage(getString(R.string.confirm))

        dialog.setPositiveButton(getString(R.string.yes), object :DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {
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
        super.onPause()
    }

    override fun onStop() {
        if(!enteringQuiz){
            FirebaseDatabase.getInstance().getReference("pvp/$myQuizId").removeValue()
            enteringQuiz=true
        }
        super.onStop()
    }
    private fun createQuiz(){
        if (myQuizId.isNotEmpty() && !isConnectedToInternet()){
            Toast.makeText(this, getString(R.string.noConnection), Toast.LENGTH_SHORT).show()
            hint_text_view_pvp_activity.visibility = View.VISIBLE
            return
        }
        else if(myQuizId.isNotEmpty() && hint_text_view_pvp_activity.visibility==View.VISIBLE && isConnectedToInternet()){
            Toast.makeText(this, getString(R.string.noConnection), Toast.LENGTH_SHORT).show()
            return
        }
        else if(myQuizId.isNotEmpty()){
            Toast.makeText(this, getString(R.string.quiz_exists), Toast.LENGTH_SHORT).show()
            return
        }

        myQuizId = UUID.randomUUID().toString()
        aQuizID = myQuizId
        createdQuiz = true
        enteringQuiz=false
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

        val ref = FirebaseDatabase.getInstance().getReference("pvp")
        ref.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                adapter.clear()
                if(p0.exists()){
                    for (item in p0.children){
                        progress_pvp_match_activity.visibility = View.GONE
                        hint_text_view_pvp_activity.visibility = View.GONE
                        var quiz_code = item.getValue(code::class.java)
                        adapter.add(QuizItem(quiz_code!!, myQuizId, this@PVPMatchActivity))
                    }
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
    }
}
