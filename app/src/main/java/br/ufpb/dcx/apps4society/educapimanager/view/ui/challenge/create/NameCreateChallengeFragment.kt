package br.ufpb.dcx.apps4society.educapimanager.view.ui.challenge.create

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.ufpb.dcx.apps4society.educapimanager.R
import br.ufpb.dcx.apps4society.educapimanager.control.facade.CreateObjectFacade
import com.google.android.material.textfield.TextInputLayout

class NameCreateChallengeFragment() : Fragment() {
    private var TAG: String = "NameCreateChallengeFragment"
    private lateinit var tvName: TextInputLayout


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_create_challenge_1_name, container, false)
        tvName = root.findViewById(R.id.tilName)
        tvName.editText?.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                CreateObjectFacade.instance.tempChallenge.word = s.toString()
                Log.i(TAG, s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

        })

        tvName.editText?.setText(CreateObjectFacade.instance.tempChallenge.word)
        return root
    }

}