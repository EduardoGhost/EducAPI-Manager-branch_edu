package br.ufpb.dcx.apps4society.educapimanager.view.ui.context.create

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
import java.util.*

class PhotoCreateContextFragment : Fragment(), View.OnClickListener,
    ButtonListener,
    LoadImageUrlListener {

    private var TAG : String = "PhotoCreateContextFragment"
    private lateinit var btnImageUrl : Button
    //private lateinit var btnOpenCam : Button
    private lateinit var btnImageWeb : Button
    private lateinit var contextImage : ImageView
    private lateinit var tvContextImageName : TextView
    private var buttons : ArrayList<Button> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_create_context_2_photo, container, false)
        btnImageUrl = root.findViewById(R.id.btnImageUrl)
        btnImageUrl.setOnClickListener(this)
        //btnOpenCam = root.findViewById(R.id.btnOpenCam)
        //btnOpenCam.setOnClickListener(this)
        btnImageWeb = root.findViewById(R.id.btnImageWeb)
        btnImageWeb.setOnClickListener(this)
        contextImage = root.findViewById(R.id.context_image_view)
        tvContextImageName = root.findViewById(R.id.tvContextImageName)
        tvContextImageName.text = "Imagem do contexto " + CreateObjectFacade.instance.tempContext.name

        buttons.addAll(listOf(btnImageUrl, btnImageWeb))

        return root
    }

    override fun onResume() {
        super.onResume()
        loadImage()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnImageUrl -> {
                loadUrlFragment()

            }
            R.id.btnImageWeb -> {
                loadImageSearchFragment()
            }
            /*
            R.id.btnOpenCam ->{
                Toast.makeText(context,"Opção ainda não disponivel nesta versão",Toast.LENGTH_SHORT).show()
            }
             */
        }
    }

    private fun loadUrlFragment(){
        val transaction = fragmentManager?.beginTransaction()
        val urlFragment  = UrlFragment(this)
        urlFragment.type = UrlFragment.IMAGE_URL
        transaction?.replace(R.id.frameAuxPhotoFragment, urlFragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }
    private fun loadImageSearchFragment(){
        val transaction = fragmentManager?.beginTransaction()
        val searchFragment = SearchFragment(this, CreateObjectFacade.instance.tempContext.name,"context")
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
            .into(contextImage)
    }

}