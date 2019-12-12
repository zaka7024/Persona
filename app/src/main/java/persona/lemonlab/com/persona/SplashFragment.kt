package persona.lemonlab.com.persona


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.fragment_splash.*


class SplashFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        splash()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun splash() {
        // Make PagerAdapter And Put It Into a ViewPager
        val sliderAdapter = SplashSlider(context!!, view!!)
        sliderView.adapter = sliderAdapter
        // Change on Slide
        addDots(0)
        sliderView.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                addDots(position)
            }

        })


    }

    fun addDots(p: Int) {
        linearLayout.removeAllViews()
        val dots: ArrayList<TextView> = ArrayList<TextView>().apply {
            add(TextView(context))
            add(TextView(context))
            add(TextView(context))
        }

        for (i in dots) {
            i.text = HtmlCompat.fromHtml("&#8226;", HtmlCompat.FROM_HTML_MODE_LEGACY)
            i.textSize = 36f
            i.setTextColor(ContextCompat.getColor(context!!, R.color.white_alpha))
            linearLayout.addView(i)
        }

        dots[p].setTextColor(ContextCompat.getColor(context!!, R.color.white))
    }
}
