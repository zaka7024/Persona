package persona.lemonlab.com.persona

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.slider_item.view.*

/**
 * Created by HP on 29/08/2018.
 */
class splash_slider(val context:Context, val activity:Activity):PagerAdapter() {

    var person = ArrayList<Int>().apply {
        add(R.drawable.person01)
        add(R.drawable.person02)
        add(R.drawable.person03)
    }

    var text = ArrayList<String>().apply {
        add("الشخصيّة هي واحد من العديد من العوامل التي تحدد تصرفاتنا، لأن تصرفاتنا متأثرة بالبيئة المحيطة، وأهدافنا الشخصيّة وأحيانًا تجاربنا")
        add("بغض النّظر عن قوّة الفرضيّة التي وضعناها في تصميم الاختبارات، لا يمكننا تقسيم الناس إلى فئات معيّنة باستخدام معلومات قليلة")
        add("ليس جميع ما نقوله عنك سيجعلك تبتسم، لأن هدفنا الحقيقة، أيضًا، البشر كمخلوقات اجتماعيّة ليس فيها شيء ثابت وصفاتك قد تتغيّر من وقت لآخر")
    }

    override fun getCount(): Int {
        return person.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as ConstraintLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var view = LayoutInflater.from(context).inflate(R.layout.slider_item,container,false)

        // set image and text value

        view.slider_textview.text = text[position]
        view.slider_person_image.setImageResource(person[position])
        view.slider_item_background.setBackgroundColor(context.resources.getColor(R.color.main_color))

        // Go To Information Activity
        view.startButton.setOnClickListener {
            var intent = Intent(context,infoActivity::class.java)
            context.startActivity(intent)
            activity.finish()
        }

        if(position == 2){
            view.startButton.visibility = View.VISIBLE
        }
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }
}