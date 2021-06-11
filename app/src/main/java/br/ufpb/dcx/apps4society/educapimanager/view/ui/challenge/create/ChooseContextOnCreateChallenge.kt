package br.ufpb.dcx.apps4society.educapimanager.view.ui.challenge.create

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import br.ufpb.dcx.apps4society.educapimanager.R
import br.ufpb.dcx.apps4society.educapimanager.control.facade.CreateObjectFacade
import br.ufpb.dcx.apps4society.educapimanager.control.service.ChallengesService
import br.ufpb.dcx.apps4society.educapimanager.control.service.ContextsService
import br.ufpb.dcx.apps4society.educapimanager.helper.RetrofitConfig
import br.ufpb.dcx.apps4society.educapimanager.model.bean.Challenge
import br.ufpb.dcx.apps4society.educapimanager.model.dto.ContextDTO
import br.ufpb.dcx.apps4society.educapimanager.model.dto.NewChallengeDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChooseContextOnCreateChallenge : Fragment() {
    private lateinit var contextos : List<ContextDTO>
    private lateinit var listview: ListView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    
    ): View? {
        getContextsFromService()
         val root = inflater.inflate(R.layout.fragment_nav_choose_context, container, false)

        listview = root.findViewById(R.id.listview_choose)

        val challenge = CreateObjectFacade.instance.tempChallenge
        val newChallengeDTO = challenge.challengeDTO(challenge)

        listview.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            insertChallenge(contextos[position].id, newChallengeDTO)
        }

        return root
    }

    fun getContextsFromService(){
        val call = RetrofitConfig.getAuthRetrofit(context).create(ContextsService::class.java).contextsByUser
        call.enqueue(object : Callback<List<ContextDTO>> {
            override fun onFailure(
                call: Call<List<ContextDTO>>,
                t: Throwable
            ) {
                Toast.makeText(context,"Ocorreu um Erro"+t.message,Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<List<ContextDTO>>, response: Response<List<ContextDTO>>
            ) {

                contextos = response.body()!!
                initializeSpinner(response.body()!!)
                Toast.makeText(context,"Lista recuperada com sucesso",Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun insertChallenge(idContext:Long, newChallengeDTO: NewChallengeDTO ){
        val call = RetrofitConfig.getAuthRetrofit(context).create(ChallengesService::class.java).insertChallenge(idContext, newChallengeDTO)

        call.enqueue(object : Callback<Challenge> {
            override fun onFailure(call: Call<Challenge>, t: Throwable) {
                Toast.makeText(context,"Desafio não cadastrado",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Challenge>, response: Response<Challenge>) {
                if(response.isSuccessful){
                    Toast.makeText(context,"Desafio Cadastrado Com sucesso",Toast.LENGTH_LONG).show()
                    CreateObjectFacade.instance.clearTempChallenge()
                }
                else{
                    Toast.makeText(context,"Algo Deu Errado, Tente Novamente"+response.code(),Toast.LENGTH_LONG).show()
                }
            }

        })
    }

    fun initializeSpinner(contexts: List<ContextDTO>){
        val names = arrayListOf<String>()
        for (c: ContextDTO in contexts){
            names.add(c.name)
        }
        val adapter = context?.let { ArrayAdapter(it,R.layout.sample_text_view_list,R.id.text_view_list,names) }

        listview.adapter = adapter
    }
}
