package br.ufpb.dcx.apps4society.educapimanager.view.ui.logout

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.ufpb.dcx.apps4society.educapimanager.R
import br.ufpb.dcx.apps4society.educapimanager.control.facade.CreateObjectFacade
import br.ufpb.dcx.apps4society.educapimanager.view.LoginActivity
import br.ufpb.dcx.apps4society.educapimanager.view.NavDrawerActivity

class LogoutFragment : Fragment() {

    private lateinit var logoutViewModel: LogoutViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        logoutViewModel =
            ViewModelProviders.of(this).get(LogoutViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_logout, container, false)
        val positivebtn = root.findViewById<AppCompatButton>(R.id.positive_button_logout)
        val negativebtn = root.findViewById<AppCompatButton>(R.id.negative_button_logout)

        positivebtn.setOnClickListener {
            val toLoginActivity = Intent()
            context?.let { it1 -> toLoginActivity.setClass(it1,LoginActivity::class.java) }
            CreateObjectFacade.instance.clearTempSession()
            startActivity(toLoginActivity)
            activity?.finish()
            Toast.makeText(context,"Sessão encerrada",Toast.LENGTH_SHORT).show()
        }
        negativebtn.setOnClickListener {
            val toHome = Intent()
            context?.let { it1 -> toHome.setClass(it1,NavDrawerActivity::class.java) }
            startActivity(toHome)
        }

        return root
    }
}