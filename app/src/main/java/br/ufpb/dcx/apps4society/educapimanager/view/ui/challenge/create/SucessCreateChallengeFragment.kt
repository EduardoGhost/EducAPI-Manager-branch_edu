package br.ufpb.dcx.apps4society.educapimanager.view.ui.challenge.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import br.ufpb.dcx.apps4society.educapimanager.R
import br.ufpb.dcx.apps4society.educapimanager.control.facade.CreateObjectFacade

class SucessCreateChallengeFragment : Fragment() {
    private var TAG : String = "SucessCreateChallengeFragment"
    private lateinit var tvSucess : TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_challenge_create_sucess, container, false)
        CreateObjectFacade.instance.tempChallenge.creator = CreateObjectFacade.instance.tempSession.creator;
        tvSucess = root.findViewById(R.id.tvStatusChallengeCreation)
        tvSucess.text = "Desafio '" + CreateObjectFacade.instance.tempChallenge.word + "' criado."
        return root
    }
}