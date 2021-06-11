package br.ufpb.dcx.apps4society.educapimanager.view.ui.context.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import br.ufpb.dcx.apps4society.educapimanager.R
import br.ufpb.dcx.apps4society.educapimanager.control.facade.CreateObjectFacade
import br.ufpb.dcx.apps4society.educapimanager.control.service.ContextsService
import br.ufpb.dcx.apps4society.educapimanager.helper.RetrofitConfig
import br.ufpb.dcx.apps4society.educapimanager.model.dto.ContextDTO
import br.ufpb.dcx.apps4society.educapimanager.model.dto.NewContextDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SucessCreateContextFragment : Fragment() {
    private var TAG : String = "SucessCreateContextFragment"
    private lateinit var tvSucess : TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_context_create_sucess, container, false)
        tvSucess = root.findViewById(R.id.tvSucess)

        CreateObjectFacade.instance.tempContext.creator = CreateObjectFacade.instance.tempSession.creator

        tvSucess.text = "Contexto '" + CreateObjectFacade.instance.tempContext.name + "' criado."

        saveContext()
        return root
    }
    private fun saveContext(){
        val contextDTO = CreateObjectFacade.instance.tempContext
        val contextNewDTO =
            NewContextDTO()
        contextNewDTO.imageUrl = contextDTO.imageUrl
        contextNewDTO.name = contextDTO.name
        contextNewDTO.soundUrl = contextDTO.soundUrl
        contextNewDTO.videoUrl = contextDTO.videoUrl

        val call : Call<ContextDTO> = RetrofitConfig.getAuthRetrofit(context).create(
            ContextsService::class.java).insertNewContext(contextNewDTO)

        call.enqueue(object: Callback<ContextDTO?> {
            override fun onResponse(call: Call<ContextDTO?>?, response: Response<ContextDTO?>?) {
                if(response?.isSuccessful!!){
                    Toast.makeText(context, "Contexto criado com sucesso!", Toast.LENGTH_SHORT).show()
                    CreateObjectFacade.instance.clearTempContext()
                } else {
                    Toast.makeText(context, "Erro ao criar contexto!", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ContextDTO?>?, t: Throwable?) {
                Toast.makeText(context, "Erro ao se comunicar com o serviço!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}