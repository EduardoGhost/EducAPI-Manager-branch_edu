package br.ufpb.dcx.apps4society.educapimanager.view.ui.context

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import br.ufpb.dcx.apps4society.educapimanager.R
import br.ufpb.dcx.apps4society.educapimanager.control.service.ContextsService
import br.ufpb.dcx.apps4society.educapimanager.helper.RetrofitConfig
import br.ufpb.dcx.apps4society.educapimanager.model.dto.ContextDTO
import br.ufpb.dcx.apps4society.educapimanager.model.dto.NewContextDTO
import com.bumptech.glide.Glide
import com.bumptech.glide.load.EncodeStrategy
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContextListAdapter(private var contexts: List<ContextDTO>, fragmentContext: android.content.Context) : RecyclerView.Adapter<ContextListAdapter.ViewHolder>() {
    private var TAG : String = "ContextListAdapter"
    private var fragmentContext : android.content.Context = fragmentContext

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_context, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return contexts.size;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.context_name.text = contexts[position].name
        loadImage(contexts[position].imageUrl, holder.context_image)
        holder.id_tx_view.text = "ID: "+contexts[position].id.toString()
        holder.delete.setOnClickListener {exibirConfirmacao(contexts[position].id) }
        holder.bnt_edit_context.setOnClickListener{dialog(contexts[position].id, contexts[position])}
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
            //.error(erroImg)
            .diskCacheStrategy(diskCacheStrategy)
            .into(themeImageLeft)
    }
    
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var context_name : TextView = itemView.findViewById(R.id.context_name)
        var bnt_edit_context : Button = itemView.findViewById(R.id.btn_edit_context)
        var btn_view_challenges_of_this_context : Button = itemView.findViewById(R.id.btn_view_challenges_of_this_context)
        var context_image : ImageView = itemView.findViewById(R.id.context_image)
        var id_tx_view : TextView = itemView.findViewById(R.id.id_tx_view)
        var delete : MaterialButton = itemView.findViewById(R.id.excluir_contexto)

    }

    private fun delete (idContext: Long){
        val call = RetrofitConfig.getAuthRetrofit(fragmentContext).create(ContextsService::class.java).deleteContext(idContext)
        call.enqueue(object: Callback<ContextDTO>{
            override fun onFailure(call: Call<ContextDTO>, t: Throwable) {
                Toast.makeText(fragmentContext,"Não foi possivel estabelecer uma conexão",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ContextDTO>, response: Response<ContextDTO>) {
                if(response.isSuccessful){
                    Toast.makeText(fragmentContext,"Contexto deletado com sucesso",Toast.LENGTH_SHORT).show()
                }else if(response.code() == 401){
                    Toast.makeText(fragmentContext,"Você não tem permissão para excluir esse contexto",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(fragmentContext,"Ocorreu um problema ao tentar deletar o contexto",Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun update (idContext : Long, context: NewContextDTO){
        val call = RetrofitConfig.getAuthRetrofit(fragmentContext).create(ContextsService::class.java).updateContext(idContext,context)

        call.enqueue(object : Callback<ContextDTO>{
            override fun onFailure(call: Call<ContextDTO>, t: Throwable) {
                Toast.makeText(fragmentContext,"Não foi possivel atualizar o contexto",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ContextDTO>, response: Response<ContextDTO>) {
                if(response.isSuccessful){
                    Toast.makeText(fragmentContext,"Contexto atualizado com sucesso",Toast.LENGTH_SHORT).show()
                }else if(response.code() == 401){
                    Toast.makeText(fragmentContext,"Você não pode editar esse contexto",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(fragmentContext,"Ocorreu um problema ao tentar atualizar o contexto",Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun dialog (idContext : Long, context: ContextDTO){
        val a  = AlertDialog.Builder(fragmentContext)
        a.setTitle("Atualizar Contexto")
        val view = View.inflate(fragmentContext,R.layout.alert_dialog_update_context,null)
        a.setView(view)
        val edtName = view.findViewById<TextInputEditText>(R.id.tiet_name)
        val edtImageUrl = view.findViewById<TextInputEditText>(R.id.tiet_url_image)
        val edtVideoUrl = view.findViewById<TextInputEditText>(R.id.tiet_url_video)
        val edtSoundUrl = view.findViewById<TextInputEditText>(R.id.tiet_url_sound)
        val btnConfirm = view.findViewById<Button>(R.id.btn_confirm_update)
        val alert = a.create()

        alert.show()

        btnConfirm.setOnClickListener {
            if(!edtName.text.isNullOrEmpty()) context.name = edtName.text.toString()
            if(!edtImageUrl.text.isNullOrEmpty()) context.imageUrl = edtImageUrl.text.toString()
            if(!edtVideoUrl.text.isNullOrEmpty()) context.videoUrl = edtVideoUrl.text.toString()
            if(!edtSoundUrl.text.isNullOrEmpty()) context.soundUrl = edtSoundUrl.text.toString()

            val newContextDTO = context.toNewContextDto(context);

            update(idContext, newContextDTO)
            alert.dismiss()
        }
    }

    private fun exibirConfirmacao(idContext: Long){
        val alertDialog  = AlertDialog.Builder(fragmentContext)
        alertDialog.setTitle("Confirmação")
        alertDialog.setIcon(null)
        alertDialog.setMessage("Você realmente deseja apagar esse contexto?")
        alertDialog.apply {
            setPositiveButton("Sim", DialogInterface.OnClickListener { dialog, which ->
                delete(idContext)
            })

            setNegativeButton("Não", DialogInterface.OnClickListener { dialog, which ->

            })
        }
        alertDialog.show()
    }
}