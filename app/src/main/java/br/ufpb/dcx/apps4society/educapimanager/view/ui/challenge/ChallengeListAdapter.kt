package br.ufpb.dcx.apps4society.educapimanager.view.ui.challenge

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import br.ufpb.dcx.apps4society.educapimanager.R
import br.ufpb.dcx.apps4society.educapimanager.control.service.ChallengesService
import br.ufpb.dcx.apps4society.educapimanager.helper.RetrofitConfig
import br.ufpb.dcx.apps4society.educapimanager.model.bean.Challenge
import br.ufpb.dcx.apps4society.educapimanager.model.dto.NewChallengeDTO
import com.bumptech.glide.Glide
import com.bumptech.glide.load.EncodeStrategy
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChallengeListAdapter(private var challenges : List<Challenge>, private var fragmentContext: android.content.Context) : RecyclerView.Adapter<ChallengeListAdapter.ViewHolder>() {
    private var TAG : String = "ChallengeListAdapter"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_challenge, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return challenges.size;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.challenge_name.setText(challenges[position].word)
        loadImage(challenges[position].imageUrl, holder.challenge_image)
        holder.bnt_edit_challenge.setOnClickListener { dialog(challenges[position].id, challenges[position]) }
        holder.excluir_challenge.setOnClickListener {exibirConfirmacao(challenges[position].id)}

    }

    private fun loadImage(imageUrl: String?, themeImageLeft: ImageView) {
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
            erroImg = Integer.parseInt(imageUrl.toString())
        } catch (e: NumberFormatException) {
            erroImg = R.drawable.no_image
        }

        Glide.with(fragmentContext)
            .load(imageUrl)
           // .error(erroImg)
            .diskCacheStrategy(diskCacheStrategy)
            .into(themeImageLeft)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var challenge_name : TextView = itemView.findViewById(R.id.challenge_word)
        var bnt_edit_challenge : Button = itemView.findViewById(R.id.btn_edit_challenge)
        var challenge_image : ImageView = itemView.findViewById(R.id.challenge_image)
        var excluir_challenge : MaterialButton = itemView.findViewById(R.id.excluir_challenge)

    }

    private fun dialog (idContext:Long, challenge: Challenge){
        val a  = AlertDialog.Builder(fragmentContext)
        a.setTitle("Atualizar Desafio")
        val view = View.inflate(fragmentContext,R.layout.alert_dialog_update_challenge,null)
        a.setView(view)
        val edtWord = view.findViewById<TextInputEditText>(R.id.tiet_word)
        val edtImageUrl = view.findViewById<TextInputEditText>(R.id.tiet_url_image)
        val edtVideoUrl = view.findViewById<TextInputEditText>(R.id.tiet_url_video)
        val edtSoundUrl = view.findViewById<TextInputEditText>(R.id.tiet_url_sound)
        val btnConfirm = view.findViewById<Button>(R.id.btn_confirm_update)
        val alert = a.create()
        alert.show()

        btnConfirm.setOnClickListener {
            if(!edtWord.text.isNullOrEmpty()) challenge.word = edtWord.text.toString()
            if(!edtImageUrl.text.isNullOrEmpty()) challenge.imageUrl = edtImageUrl.text.toString()
            if(!edtVideoUrl.text.isNullOrEmpty()) challenge.videoUrl = edtVideoUrl.text.toString()
            if(!edtSoundUrl.text.isNullOrEmpty()) challenge.soundUrl = edtSoundUrl.text.toString()

            val challengeNewDto : NewChallengeDTO = challenge.challengeDTO(challenge)

            update(idContext, challengeNewDto)
            alert.dismiss()
        }

    }

    private fun update (idChallenge:Long, challenge:NewChallengeDTO){
        val call = RetrofitConfig.getAuthRetrofit(fragmentContext).create(ChallengesService::class.java).updateChallenge(idChallenge, challenge)
        call.enqueue(object : Callback<Challenge> {
            override fun onFailure(call: Call<Challenge>, t: Throwable) {
                Toast.makeText(fragmentContext,"Não foi possivel atualizar o Desafio", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Challenge>, response: Response<Challenge>) {
                when {
                    response.isSuccessful -> {
                        Toast.makeText(fragmentContext,"Desafio atualizado com sucesso",Toast.LENGTH_SHORT).show()
                    }
                    response.code() == 401 -> {
                        Toast.makeText(fragmentContext,"Você não pode editar esse desafio",Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Toast.makeText(fragmentContext,"Ocorreu um problema ao tentar atualizar o Desafio",Toast.LENGTH_SHORT).show()
                    }
                }
            }

        })
    }

    private fun delete (id:Long){
        val call = RetrofitConfig.getAuthRetrofit(fragmentContext).create(ChallengesService::class.java).deleteChallenges(id)
        call.enqueue(object : Callback<Void>{
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(fragmentContext,"Não foi possivel deletar o Desafio", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                when {
                    response.isSuccessful -> {
                        Toast.makeText(fragmentContext,"Desafio deletado com sucesso",Toast.LENGTH_SHORT).show()
                    }
                    response.code() == 401 -> {
                        Toast.makeText(fragmentContext,"Você não tem permissão para deletar esse desafio",Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Toast.makeText(fragmentContext,"Ocorreu um problema ao tentar deletar o desafio",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }


    private fun exibirConfirmacao(id:Long){
        val alertDialog  = AlertDialog.Builder(fragmentContext)
        alertDialog.setTitle("Confirmação")
        alertDialog.setIcon(null)
        alertDialog.setMessage("Você realmente deseja apagar esse desafio?")
        alertDialog.apply {
            setPositiveButton("Sim", DialogInterface.OnClickListener { dialog, which ->
                delete(id)
            })

            setNegativeButton("Não", DialogInterface.OnClickListener { dialog, which ->

            })
        }
        alertDialog.show()
    }

}