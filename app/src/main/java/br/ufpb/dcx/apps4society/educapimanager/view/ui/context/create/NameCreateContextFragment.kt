package br.ufpb.dcx.apps4society.educapimanager.view.ui.context.create

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import br.ufpb.dcx.apps4society.educapimanager.R
import br.ufpb.dcx.apps4society.educapimanager.control.facade.CreateObjectFacade
import com.google.android.material.textfield.TextInputLayout

class NameCreateContextFragment : Fragment() {

    private var TAG : String = "NameCreateContextFragment"
    private lateinit var tvName : TextInputLayout
    //TODO("Implementar o Aviso de obrigatoriedade do nome.   OBS: xml está comentado")
    private lateinit var tvWarning: TextView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_create_context_1_name, container, false)
        Log.i(TAG, ""+root)
        tvName = root.findViewById(R.id.tilName)
        tvName.editText?.addTextChangedListener(object : TextWatcher{
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                CreateObjectFacade.instance.tempContext.name = s.toString()
                Log.i(TAG, s.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }



        })

        tvName.editText?.setText(CreateObjectFacade.instance.tempContext.name)

        return root
    }
}