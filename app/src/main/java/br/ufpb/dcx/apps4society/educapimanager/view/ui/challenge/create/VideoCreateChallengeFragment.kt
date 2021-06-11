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
import br.ufpb.dcx.apps4society.educapimanager.model.ButtonListener
import br.ufpb.dcx.apps4society.educapimanager.view.ui.url.UrlFragment

class VideoCreateChallengeFragment : Fragment(), View.OnClickListener , ButtonListener{

    private var TAG : String = "VideoCreateChallengeFragment"
    private lateinit var btnChallengeVideoUrl : Button
    private lateinit var btnChallengeGalery : Button
    private lateinit var btnChallengeRecordVideo : Button
    private lateinit var tvChallengeVideoName : TextView
    private var buttons : java.util.ArrayList<Button> = java.util.ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_create_challenge_3_video, container, false)
        btnChallengeVideoUrl = root.findViewById(R.id.btnChallengeVideoUrl)
        btnChallengeVideoUrl.setOnClickListener(this)

        //btnChallengeGalery = root.findViewById(R.id.btnChallengeGalery)
        //btnChallengeGalery.setOnClickListener(this)

       // btnChallengeRecordVideo = root.findViewById(R.id.btnChallengeRecordVideo)
        //btnChallengeRecordVideo.setOnClickListener(this)

        tvChallengeVideoName = root.findViewById(R.id.tvChallengeVideoName)

        tvChallengeVideoName.text = "Vídeo do desafio " + CreateObjectFacade.instance.tempChallenge.word

        return root
    }

    override fun getListeners(): List<Button> {
        return buttons
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnChallengeVideoUrl -> {
                loadUrlFragment()
            }
            /*
            R.id.btnChallengeRecordVideo -> {
                Toast.makeText(context,"Opção ainda não disponivel nesta versão", Toast.LENGTH_SHORT).show()
            }
            R.id.btnChallengeGalery -> {
                Toast.makeText(context,"Opção ainda não disponivel nesta versão", Toast.LENGTH_SHORT).show()
            }
            */
        }
    }

    private fun loadUrlFragment(){
        val transaction = fragmentManager?.beginTransaction()
        val urlFragment : UrlFragment = UrlFragment(this)
        urlFragment.type = UrlFragment.VIDEO_URL
        transaction?.replace(R.id.frameAuxVideoFragment, urlFragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }
}