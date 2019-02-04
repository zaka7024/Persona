package persona.lemonlab.com.persona.models

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
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
import persona.lemonlab.com.persona.items.quiz_item
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

    override fun onResume() {
        myQuizId = ""
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

    fun initQuizAvailableRV(){
        quiz_available_rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        quiz_available_rv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        quiz_available_rv.adapter = adapter
    }

    fun createQuiz(){

        if(myQuizId.isNotEmpty()){
            Toast.makeText(this, getString(R.string.quiz_exists), Toast.LENGTH_SHORT).show()
            return
        }

        myQuizId = UUID.randomUUID().toString()
        aQuizID = myQuizId
        createdQuiz = true
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

    fun getAvailableQuiz(){

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
                        adapter.add(quiz_item(quiz_code!!, myQuizId, this@PVPMatchActivity))
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
        myQuizId = ""
    }

    fun getUserName():String{
        val ref = getSharedPreferences("app_data", 0)
        return ref.getString("username", "username")
    }

    fun startOnlineQuizForHost(){
        var intent = Intent(this, PVPActivity::class.java)
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
