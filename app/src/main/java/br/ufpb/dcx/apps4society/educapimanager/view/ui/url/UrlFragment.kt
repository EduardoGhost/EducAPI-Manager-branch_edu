package br.ufpb.dcx.apps4society.educapimanager.view.ui.url

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import br.ufpb.dcx.apps4society.educapimanager.R
import br.ufpb.dcx.apps4society.educapimanager.model.ButtonListener
import br.ufpb.dcx.apps4society.educapimanager.model.LoadImageUrlListener
import br.ufpb.dcx.apps4society.educapimanager.control.facade.CreateObjectFacade
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_create_context.*

class UrlFragment(fragment : Fragment) : Fragment(), View.OnClickListener{

    private var TAG : String = "UrlFragment"
    private lateinit var confirmBtn : Button
    private lateinit var urlTextInput : TextInputLayout
    private var fragment : Fragment = fragment
    lateinit var type : String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.get_url_input_fragment, container, false)
        confirmBtn = root.findViewById(R.id.btnConfirmUrl)
        confirmBtn.setOnClickListener(this)

        urlTextInput = root.findViewById(R.id.tilUrl)

        lockTouchScreem()
        setInputConfigs()

        return root
    }

    fun setInputConfigs(){
        when(type){
            IMAGE_URL -> {
                urlTextInput.editText?.setText(CreateObjectFacade.instance.tempContext.imageUrl)
                urlTextInput.editText?.addTextChangedListener(object : TextWatcher{
                    override fun afterTextChanged(s: Editable?) {}

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        CreateObjectFacade.instance.tempContext.imageUrl = urlTextInput.editText?.text.toString()

                    }

                })
            }

            VIDEO_URL -> {
                urlTextInput.editText?.setText(CreateObjectFacade.instance.tempContext.videoUrl)
                urlTextInput.editText?.addTextChangedListener(object : TextWatcher{
                    override fun afterTextChanged(s: Editable?) {}

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        CreateObjectFacade.instance.tempContext.videoUrl = urlTextInput.editText?.text.toString()
                    }

                })

            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnConfirmUrl -> {
                this.fragmentManager?.popBackStack()
                activeTouchScreem()
                if (type.equals(IMAGE_URL)) (fragment as LoadImageUrlListener).loadImage()
            }
        }
    }

    fun lockTouchScreem(){
        changeClikableStatus(false)
    }

    fun activeTouchScreem(){
        changeClikableStatus(true)
    }

    fun changeClikableStatus(status : Boolean){
        for (b : Button in (fragment as ButtonListener).getListeners()){
            b.isClickable = status
        }
        fragment.activity?.btnNext?.isClickable = status
    }

    companion object{
        var IMAGE_URL = "image"
        var VIDEO_URL = "video"
    }
}