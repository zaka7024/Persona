package persona.lemonlab.com.persona.items

import android.app.Activity
import android.content.Intent
import android.support.v7.view.menu.MenuView
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.quiz_item.view.*
import persona.lemonlab.com.persona.PVPActivity
import persona.lemonlab.com.persona.R
import persona.lemonlab.com.persona.models.code

class quiz_item(var code_item:code,var currentDeviceCode:String, var activity:Activity):Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.quiz_item
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.quiz_name_text_view.text = code_item.host_name

        if (code_item.isUsed){
            viewHolder.itemView.quiz_available_text_view.text = "غير متاح"
        }

        viewHolder.itemView.setOnClickListener {

            if(code_item.value == currentDeviceCode){
                return@setOnClickListener
            }

            var id = code_item.value
            val ref = FirebaseDatabase.getInstance().getReference("pvp/${id}")
            var ac_code:code? = null
            ref.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    ac_code = p0.getValue(code::class.java)!!
                    if (!(ac_code!!.isUsed)){ // if quiz available or not
                        ac_code!!.isUsed = true
                        ref.setValue(ac_code).addOnCompleteListener {
                            Log.i("PVPMatchActivity", "accept code, pvp will start")
                            viewHolder.itemView.quiz_available_text_view.text = "غير متاح"
                            startOnlineQuiz()
                        }
                    }else{
                        Log.i("PVPMatchActivity", "the quiz is already used")
                    }
                }

            })
        }
    }

    fun startOnlineQuiz(){
        var intent = Intent(activity, PVPActivity::class.java)
        intent.putExtra("PVP_HOTS_NAME", code_item.guest_name)
        intent.putExtra("PVP_GUEST_NAME", getUserName())
        activity.startActivity(intent)
    }

    fun getUserName():String{
        val ref = activity.getSharedPreferences("app_data", 0)
        return ref.getString("username", "الضيف")
    }
}