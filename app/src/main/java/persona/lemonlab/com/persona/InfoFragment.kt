package persona.lemonlab.com.persona


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_info.*
import persona.lemonlab.com.persona.Extensions.playSound

enum class Gender { Male, Female }


class InfoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        input()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun input() {
        saveButton.setOnClickListener {
            // play sound
            context!!.playSound()
            if (userNameText.text!!.length < 3)
                Toast.makeText(context!!, getString(R.string.nameMustBeLonger), Toast.LENGTH_SHORT).show()
            else if (userNameText.text.toString().trim().isNotEmpty() && (maleCheck.isChecked || femaleCheck.isChecked)) {

                val gender = if (maleCheck.isChecked) {
                    Gender.Male
                } else {
                    Gender.Female
                }

                context!!.getSharedPreferences("app_data", 0).edit().apply {
                    putBoolean("firstTime", false)
                    putString("username", userNameText.text.toString())
                    putString("userGender", gender.toString())
                    apply()
                }
                it.findNavController().navigate(InfoFragmentDirections.infoToMain())
            } else
                Toast.makeText(context, getString(R.string.fill_fields), Toast.LENGTH_SHORT).show()

        }

    }

}
