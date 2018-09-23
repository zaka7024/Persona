package persona.lemonlab.com.persona

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.text.Html
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.slider_item.*

class splashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        var first_time = getSharedPreferences("app_data",0).getBoolean("first_time",true)

        if(first_time){
            // Make PagerAdapter And Put It Into a ViewPager
            var sliderAdapter = splash_slider(this,this)
            sliderView.adapter = sliderAdapter
            // Change on Slide
            addDots(0)
            sliderView.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
                override fun onPageScrollStateChanged(state: Int) {
                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                }

                override fun onPageSelected(position: Int) {
                    addDots(position)
                }

            })
        }else{
            var intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            this.finish()
        }


    }

    // Make 3 TextView and put it into linearLayout
    fun addDots(p:Int){
        linearLayout.removeAllViews()
        var dots:ArrayList<TextView> =  ArrayList<TextView>().apply {
            add(TextView(this@splashActivity))
            add(TextView(this@splashActivity))
            add(TextView(this@splashActivity))
        }

        for(i in dots){
            i.setText(Html.fromHtml("&#8226;"))
            i.setTextSize(35f)
            i.setTextColor(this@splashActivity.resources.getColor(R.color.white_alpha))

            linearLayout.addView(i)
        }

        dots[p].setTextColor(this@splashActivity.resources.getColor(R.color.white))
    }
}
