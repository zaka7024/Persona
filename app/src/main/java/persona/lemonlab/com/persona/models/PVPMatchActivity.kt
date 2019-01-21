package persona.lemonlab.com.persona.models

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_pvpmatch.*
import persona.lemonlab.com.persona.R
import persona.lemonlab.com.persona.items.quiz_item
import java.util.*

class PVPMatchActivity : AppCompatActivity() {

    var adapter = GroupAdapter<ViewHolder>()
    var myQuizId:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pvpmatch)

        //init
        initQuizAvailableRV()
        getAvailableQuiz()

        new_quiz_btn.setOnClickListener {
            startQuiz()
        }
    }

    fun initQuizAvailableRV(){
        quiz_available_rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        quiz_available_rv.adapter = adapter
    }

    fun startQuiz(){

        if(myQuizId.isNotEmpty()) return

        myQuizId = UUID.randomUUID().toString()

        val code = code(myQuizId, false, "zaka")

        val ref = FirebaseDatabase.getInstance().getReference("pvp/${myQuizId}")
        ref.setValue(code).addOnCompleteListener {
            Log.i("PVPMatchActivity", "add quiz in data base")
        }
    }

    fun getAvailableQuiz(){
        val ref = FirebaseDatabase.getInstance().getReference("pvp")
        ref.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                adapter.clear()

                for (item in p0.children){
                    var quiz_code = item.getValue(code::class.java)
                    adapter.add(quiz_item(quiz_code!!))
                }
            }

        })
    }
}
