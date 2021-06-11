package br.ufpb.dcx.apps4society.educapimanager.view.ui.challenge.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import br.ufpb.dcx.apps4society.educapimanager.R
import br.ufpb.dcx.apps4society.educapimanager.model.ButtonListener
import br.ufpb.dcx.apps4society.educapimanager.model.LoadImageUrlListener
import br.ufpb.dcx.apps4society.educapimanager.control.facade.CreateObjectFacade
import br.ufpb.dcx.apps4society.educapimanager.view.ui.search.SearchFragment
import br.ufpb.dcx.apps4society.educapimanager.view.ui.url.UrlFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.EncodeStrategy
import com.bumptech.glide.load.engine.DiskCacheStrategy
import java.util.ArrayList

class PhotoCreateChallengeFragment : Fragment(), View.OnClickListener,
    ButtonListener,
    LoadImageUrlListener {

    private lateinit var btnChallengeImageUrl : Button
    //private lateinit var btnChallengeCam : Button
    private lateinit var btnChallengeImageWeb : Button
    private lateinit var challengeImageView : ImageView
    private lateinit var tvChallengePhoto : TextView
    private var buttons : ArrayList<Button> = ArrayList()

    private var TAG : String = "PhotoCreateChallengeFragment"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_create_challenge_2_photo, container, false)
        btnChallengeImageUrl = root.findViewById(R.id.btnChallengeImageUrl)
        btnChallengeImageUrl.setOnClickListener(this)

        //btnChallengeCam = root.findViewById(R.id.btnChallengeCam)
        //btnChallengeCam.setOnClickListener(this)

        btnChallengeImageWeb= root.findViewById(R.id.btnChallengeImageWeb)
        btnChallengeImageWeb.setOnClickListener(this)

        challengeImageView = root.findViewById(R.id.challengeImageView)

        tvChallengePhoto = root.findViewById(R.id.tvChallengePhoto)

        buttons.addAll(listOf(btnChallengeImageUrl, btnChallengeImageWeb))

        tvChallengePhoto.text = "Imagem do desafio " + CreateObjectFacade.instance.tempChallenge.word
        return root
    }

    override fun onResume() {
        super.onResume()
        loadImage()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnChallengeImageUrl -> {
                loadUrlFragment()

            }
            R.id.btnChallengeImageWeb ->{
                loadImageSearchFragment()
            }
            /*
            R.id.btnChallengeCam->{
                Toast.makeText(context,"Opção ainda não disponivel nesta versão", Toast.LENGTH_SHORT).show()}

             */
             }
    }

    private fun loadUrlFragment(){
        val transaction = fragmentManager?.beginTransaction()
        val urlFragment : UrlFragment = UrlFragment(this)
        urlFragment.type = UrlFragment.IMAGE_URL
        transaction?.replace(R.id.frameAuxPhotoFragment, urlFragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }

    private fun loadImageSearchFragment(){
        val transaction = fragmentManager?.beginTransaction()
        val searchFragment = SearchFragment(this,CreateObjectFacade.instance.tempChallenge.word,"challenge")
        transaction?.replace(R.id.frameAuxPhotoFragment, searchFragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }

    override fun getListeners(): List<Button> {
        return buttons
    }

    override fun loadImage() {
        val imageUrl = CreateObjectFacade.instance.tempContext.imageUrl

        val diskCacheStrategy = object : DiskCacheStrategy() {
            override fun isDataCacheable(dataSource: com.bumptech.glide.load.DataSource?): Boolean {
                return true
            }

            override fun isResourceCacheable(isFromAlternateCacheKey: Boolean, dataSource: com.bumptech.glide.load.DataSource?, encodeStrategy: EncodeStrategy?): Boolean {
                return true
            }

            override fun decodeCachedResource(): Boolean {
                return true
            }

            override fun decodeCachedData(): Boolean {
                return true
            }

        }

        var erroImg : Int
        try {
            erroImg = Integer.parseInt(imageUrl)
        } catch (e: NumberFormatException) {
            erroImg = R.drawable.no_image
        }

        Glide.with(this)
            .load(imageUrl)
            .error(erroImg)
            .diskCacheStrategy(diskCacheStrategy)
            .into(challengeImageView)
    }

}