package br.ufpb.dcx.apps4society.educapimanager.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.ufpb.dcx.apps4society.educapimanager.view.ui.challenge.ChallengesByContextActivity;
import br.ufpb.dcx.apps4society.educapimanager.R;
import br.ufpb.dcx.apps4society.educapimanager.control.facade.CreateObjectFacade;
import br.ufpb.dcx.apps4society.educapimanager.control.service.ContextsService;
import br.ufpb.dcx.apps4society.educapimanager.helper.RetrofitConfig;
import br.ufpb.dcx.apps4society.educapimanager.model.bean.Challenge;
import br.ufpb.dcx.apps4society.educapimanager.model.dto.ContextDTO;
import br.ufpb.dcx.apps4society.educapimanager.model.dto.NewContextDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContextAdapter extends RecyclerView.Adapter<ContextAdapter.ItemViewHolder> {

    private Context context;
    private List<br.ufpb.dcx.apps4society.educapimanager.model.bean.Context> contexts;


    public ContextAdapter(Context context, List<br.ufpb.dcx.apps4society.educapimanager.model.bean.Context> contexts) {
        this.context = context;
        this.contexts = contexts;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_context, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        br.ufpb.dcx.apps4society.educapimanager.model.bean.Context contexto = contexts.get(position);

        if (contexto != null) {

            Glide.with(context)
                    .load(contexto.getImageUrl())
                    .into(holder.imageContext);

            holder.showChallenges.setOnClickListener(v -> {
                if (contexto.getChallenges().size() > 0){
                    Intent intent = new Intent(context, ChallengesByContextActivity.class);
                    intent.putExtra("list", (Serializable) new ArrayList<Challenge>(contexto.getChallenges()));
                    context.startActivity(intent);
                }else {
                    Toast.makeText(context, "Esse contexto não possui desafios cadastrados!", Toast.LENGTH_LONG).show();
                }

            });

            holder.contextName.setText(contexto.getName());
            holder.contextId.setText("ID: " + String.format(contexto.getId().toString()));
            holder.deleteContext.setOnClickListener(v -> exibirConfirmacaoDelete(contexto.getId(), position));
            holder.editContext.setOnClickListener(v -> dialogUpdate(contexto.getId(), contexto.toNewContextDto(contexto), position));

        }

        if (!contexto.getCreator().getEmail().equals(CreateObjectFacade.Companion.getInstance().getTempSession().getCreator().getEmail())){

            System.out.println(contexto.getCreator().getId());
            holder.editContext.setFocusable(false);
            holder.editContext.setClickable(false);
            holder.editContext.setVisibility(View.INVISIBLE);

            holder.deleteContext.setClickable(false);
            holder.deleteContext.setFocusable(false);
            holder.deleteContext.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return contexts.size();
    }

    public void exibirConfirmacaoDelete(Long idContext, int position) {

        AlertDialog.Builder mensagem = new AlertDialog.Builder(context);
        mensagem.setTitle("Confirmação");
        mensagem.setIcon(null);
        mensagem.setMessage("Você realmente deseja apagar esse contexto?");

        mensagem.setPositiveButton("Sim", (dialog, which) -> delete(idContext, position));

        mensagem.setNegativeButton("Não", (dialog, which) -> {

        });

        mensagem.show();
    }

    public void dialogUpdate(Long idContext, NewContextDTO newContextDTO, int position){
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Atualizar Contexto");
        View view = View.inflate(context,R.layout.alert_dialog_update_context,null);
        dialog.setView(view);

        TextInputEditText edtName = view.findViewById(R.id.tiet_name);
        edtName.setText(contexts.get(position).getName());

        TextInputEditText edtImageUrl = view.findViewById(R.id.tiet_url_image);
        edtImageUrl.setText(contexts.get(position).getImageUrl());

        TextInputEditText edtVideoUrl = view.findViewById(R.id.tiet_url_video);
        edtVideoUrl.setText(contexts.get(position).getVideoUrl());

        TextInputEditText edtSoundUrl = view.findViewById(R.id.tiet_url_sound);
        edtSoundUrl.setText(contexts.get(position).getSoundUrl());

        MaterialButton btnConfirm = view.findViewById(R.id.btn_confirm_update);

        AlertDialog alertDialog = dialog.create();
        alertDialog.show();

        btnConfirm.setOnClickListener(v -> {
            newContextDTO.setName(edtName.getText().toString());
            newContextDTO.setImageUrl(edtImageUrl.getText().toString());
            newContextDTO.setVideoUrl(edtVideoUrl.getText().toString());
            newContextDTO.setSoundUrl(edtSoundUrl.getText().toString());

            update(idContext, newContextDTO, position);
            alertDialog.dismiss();
        });
    }

    private void update(Long idContext, NewContextDTO newContextDTO, int position) {
        Call call = RetrofitConfig.getAuthRetrofit(context).create(ContextsService.class).updateContext(idContext, newContextDTO);
        call.enqueue(new Callback<ContextDTO>() {
            @Override
            public void onResponse(Call<ContextDTO> call, Response<ContextDTO> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context,"Contexto atualizado com sucesso",Toast.LENGTH_SHORT).show();
                    contexts.get(position).setName(newContextDTO.getName());
                    contexts.get(position).setImageUrl(newContextDTO.getImageUrl());
                    contexts.get(position).setVideoUrl(newContextDTO.getVideoUrl());
                    contexts.get(position).setSoundUrl(newContextDTO.getSoundUrl());
                    notifyItemChanged(position);
                }else if(response.code() == 401){
                    Toast.makeText(context,"Você não tem permissão para editar esse contexto",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context,"Ocorreu um problema ao tentar editar o contexto",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ContextDTO> call, Throwable t) {
                Toast.makeText(context,"Não foi possivel estabelecer uma conexão",Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void delete(Long idContext, int position) {
        Call call = RetrofitConfig.getAuthRetrofit(context).create(ContextsService.class).deleteContext(idContext);
        call.enqueue(new Callback<ContextDTO>() {
            @Override
            public void onResponse(Call<ContextDTO> call, Response<ContextDTO> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context,"Contexto deletado com sucesso",Toast.LENGTH_SHORT).show();
                    contexts.remove(position);
                    notifyItemRemoved(position);
                }else if(response.code() == 401){
                    Toast.makeText(context,"Você não tem permissão para excluir esse contexto",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context,"Ocorreu um problema ao tentar deletar o contexto",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ContextDTO> call, Throwable t) {
                Toast.makeText(context,"Não foi possivel estabelecer uma conexão",Toast.LENGTH_SHORT).show();

            }
        });
    }


    static class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView imageContext;
        TextView contextName, contextId;
        MaterialButton editContext, deleteContext, showChallenges;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            imageContext = itemView.findViewById(R.id.context_image);
            contextName = itemView.findViewById(R.id.context_name);
            contextId = itemView.findViewById(R.id.id_tx_view);
            editContext = itemView.findViewById(R.id.btn_edit_context);
            deleteContext = itemView.findViewById(R.id.excluir_contexto);
            showChallenges = itemView.findViewById(R.id.btn_view_challenges_of_this_context);
        }
    }

}
