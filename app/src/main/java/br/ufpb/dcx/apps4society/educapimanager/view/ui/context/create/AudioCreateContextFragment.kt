package br.ufpb.dcx.apps4society.educapimanager.view.ui.context.create

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import br.ufpb.dcx.apps4society.educapimanager.R
import br.ufpb.dcx.apps4society.educapimanager.control.facade.CreateObjectFacade

class AudioCreateContextFragment : Fragment(), View.OnClickListener {

    private var TAG : String = "AudioCreateContextFragment"
    private lateinit var tvRecordAudioContextName : TextView
    private lateinit var record : Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_create_context_4_audio, container, false)
        tvRecordAudioContextName = root.findViewById(R.id.tvRecordAudioContextName)
        tvRecordAudioContextName.text = "Grave a pronúncia de " + CreateObjectFacade.instance.tempContext.name
        record = root.findViewById(R.id.RecordAudioContext)
        record.setOnClickListener(this)
        return root
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.RecordAudioContext ->{
                Toast.makeText(context,"Opção ainda não disponivel nesta versão", Toast.LENGTH_SHORT).show()
            }
        }
    }

}