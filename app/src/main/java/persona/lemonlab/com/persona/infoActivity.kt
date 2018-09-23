package persona.lemonlab.com.persona

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_info.*
import persona.lemonlab.com.persona.Extenstions.playSound


class infoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        // Save the username and gender in shared preferences and send it to Main activity
        save_btn.setOnClickListener {
            // play sound
            playSound()
            if(username_edittext.text.toString().trim().isNotEmpty() &&(radio_male2.isChecked == true || radio_female2.isChecked
                    == true)){

                var intent = Intent(this,MainActivity::class.java)

                intent.putExtra("user_name",username_edittext.text.toString())
                var gender = if(radio_male2.isChecked){
                    "male"
                }else{
                    "female"
                }

                var data = getSharedPreferences("app_data",0).edit().apply{
                    putBoolean("first_time",false)
                    putString("username",username_edittext.text.toString())
                    putString("usergender",gender)
                    commit()
                }


                intent.putExtra("user_gendr",gender)
                startActivity(intent)
                this.finish()
            }
            else{
                Toast.makeText(this,"الرجاء ملء جميع الحقول",Toast.LENGTH_SHORT).show()
            }

        }
    }
}
