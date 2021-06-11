package br.ufpb.dcx.apps4society.educapimanager.view.ui.contact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import br.ufpb.dcx.apps4society.educapimanager.R

class ContactFragment : Fragment() {

    private lateinit var aboutViewModel: ContactViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        aboutViewModel =
            ViewModelProviders.of(this).get(ContactViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_contact, container, false)
        //val textView: TextView = root.findViewById(R.id.text_gallery)
//        aboutViewModel.text.observe(this, Observer {
//            textView.text = it
//        })
        return root
    }
}