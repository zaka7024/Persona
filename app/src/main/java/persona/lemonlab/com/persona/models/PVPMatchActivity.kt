package persona.lemonlab.com.persona.models

import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
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
import kotlin.collections.ArrayList

class PVPMatchActivity : AppCompatActivity() {

    var adapter = GroupAdapter<ViewHolder>()
    var myQuizId:String = ""

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
    }

    override fun onBackPressed() {
        var dialog = AlertDialog.Builder(this)

        dialog.setTitle("تأكيد")

        dialog.setMessage("هل تريد الاغلاق و حذف الاختبار؟")

        dialog.setPositiveButton("احذف ثم اغلق", object :DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {
                deleteQuiz()
                this@PVPMatchActivity.finish()
            }

        })

        dialog.setNegativeButton("لا", object :DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {
                return
            }

        })
        dialog.show()
    }

    fun initQuizAvailableRV(){
        quiz_available_rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        quiz_available_rv.adapter = adapter
    }

    fun createQuiz(){

        if(myQuizId.isNotEmpty()) return

        myQuizId = UUID.randomUUID().toString()

        val code = code(myQuizId, false, getUserName(),"", ArrayList(), ArrayList())

        val ref = FirebaseDatabase.getInstance().getReference("pvp/${myQuizId}")
        ref.setValue(code).addOnCompleteListener {
            Log.i("PVPMatchActivity", "add quiz in data base")
        }

        ref.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                var quiz_code = p0.getValue(code::class.java)
                if(quiz_code!!.isUsed){
                    Toast.makeText(this@PVPMatchActivity,"a person accept your quiz, i will start pvp for you, soon", Toast.LENGTH_LONG)
                            .show()
                }
            }

        })
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
                    adapter.add(quiz_item(quiz_code!!, myQuizId, this@PVPMatchActivity))
                }
            }

        })
    }

    fun deleteQuiz(){
        val ref = FirebaseDatabase.getInstance().getReference("pvp/${myQuizId}")
        ref.removeValue().addOnCompleteListener {
            Toast.makeText(this, "لقد تم حذف الختبارك", Toast.LENGTH_SHORT).show()
        }
    }

    fun getUserName():String{
        val ref = getSharedPreferences("app_data", 0)
        return ref.getString("username", "username")
    }
}
