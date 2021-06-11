package br.ufpb.dcx.apps4society.educapimanager.view.ui.challenge.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import br.ufpb.dcx.apps4society.educapimanager.R
import br.ufpb.dcx.apps4society.educapimanager.control.facade.CreateObjectFacade
import javax.annotation.meta.When

class AudioCreateChallengeFragment : Fragment(), View.OnClickListener {
    private var TAG : String = "AudioCreateChallengeFragment"
    private lateinit var tvRecordAudioChallengeName : TextView
    private lateinit var record : Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_create_challenge_4_audio, container, false)
        tvRecordAudioChallengeName = root.findViewById(R.id.tvRecordAudioChallengeName)
        tvRecordAudioChallengeName.text = "Grave a pronúncia de " + CreateObjectFacade.instance.tempChallenge.word
        record = root.findViewById(R.id.btnRecordAudioChallenge)
        record.setOnClickListener(this)
        return root
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnRecordAudioChallenge ->{
                Toast.makeText(context,"Opção ainda não disponivel nesta versão", Toast.LENGTH_SHORT).show()
            }
        }
    }
}