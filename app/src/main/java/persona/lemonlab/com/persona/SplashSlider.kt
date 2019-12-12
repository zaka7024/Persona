package persona.lemonlab.com.persona

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.viewpager.widget.PagerAdapter
import kotlinx.android.synthetic.main.slider_item.view.*


class SplashSlider(private val context: Context, private val theView: View) : PagerAdapter() {

    private val person = ArrayList<Int>().apply {
        add(R.drawable.person01)
        add(R.drawable.person02)
        add(R.drawable.person03)
    }

    var text = ArrayList<String>().apply {
        add(context.getString(R.string.splash_info_1))
        add(context.getString(R.string.splash_info_2))
        add(context.getString(R.string.splash_info_3))
    }

    override fun getCount(): Int {
        return person.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as ConstraintLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.slider_item, container, false)

        // set image and text value

        view.slider_textview.text = text[position]
        view.slider_person_image.setImageResource(person[position])
        view.slider_item_background.setBackgroundColor(ContextCompat.getColor(context, R.color.main_color))

        // Navigate to next fragment
        view.startButton.setOnClickListener {
            theView.findNavController().navigate(SplashFragmentDirections.splashToInfo())
        }

        if (position == 2) {
            view.startButton.visibility = View.VISIBLE
        }
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }
}