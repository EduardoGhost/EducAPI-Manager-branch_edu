package br.ufpb.dcx.apps4society.educapimanager.adapter;

import android.content.Context;
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

import java.util.List;

import br.ufpb.dcx.apps4society.educapimanager.R;
import br.ufpb.dcx.apps4society.educapimanager.control.facade.CreateObjectFacade;
import br.ufpb.dcx.apps4society.educapimanager.control.service.ChallengesService;
import br.ufpb.dcx.apps4society.educapimanager.helper.RetrofitConfig;
import br.ufpb.dcx.apps4society.educapimanager.model.bean.Challenge;
import br.ufpb.dcx.apps4society.educapimanager.model.dto.ChallengeDTO;
import br.ufpb.dcx.apps4society.educapimanager.model.dto.NewChallengeDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChallengeAdapter extends RecyclerView.Adapter<ChallengeAdapter.ItemViewHolder> {
    private Context context;
    private List<Challenge> challenges;

    public ChallengeAdapter(android.content.Context context, List<Challenge> challenges) {
        this.context = context;
        this.challenges = challenges;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_challenge, parent, false);
        return new ChallengeAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Challenge challenge = challenges.get(position);

        if (challenge != null) {

            Glide.with(context)
                    .load(challenge.getImageUrl())
                    .into(holder.imageChallenge);

            holder.challengeWord.setText(challenge.getWord());
            holder.deleteChallenge.setOnClickListener(v -> exibirConfirmacaoDelete(challenge.getId(), position));
            holder.editChallenge.setOnClickListener(v -> dialogUpdate(challenge.getId(), challenge.challengeDTO(challenge), position));

        }

        if (!challenge.getCreator().getEmail().equals(CreateObjectFacade.Companion.getInstance().getTempSession().getCreator().getEmail())){

            holder.editChallenge.setFocusable(false);
            holder.editChallenge.setClickable(false);
            holder.editChallenge.setVisibility(View.INVISIBLE);

            holder.deleteChallenge.setClickable(false);
            holder.deleteChallenge.setFocusable(false);
            holder.deleteChallenge.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return challenges.size();
    }

    public void exibirConfirmacaoDelete(Long idChallenge, int position) {

        AlertDialog.Builder mensagem = new AlertDialog.Builder(context);
        mensagem.setTitle("Confirmação");
        mensagem.setIcon(null);
        mensagem.setMessage("Você realmente deseja apagar esse desafio?");

        mensagem.setPositiveButton("Sim", (dialog, which) -> delete(idChallenge, position));

        mensagem.setNegativeButton("Não", (dialog, which) -> {

        });

        mensagem.show();
    }

    public void dialogUpdate(Long idContext, NewChallengeDTO newChallengeDTO, int position){
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Atualizar Desafio");
        View view = View.inflate(context,R.layout.alert_dialog_update_challenge,null);
        dialog.setView(view);

        TextInputEditText edtName = view.findViewById(R.id.tiet_word);
        edtName.setText(challenges.get(position).getWord());

        TextInputEditText edtImageUrl = view.findViewById(R.id.tiet_url_image);
        edtImageUrl.setText(challenges.get(position).getImageUrl());

        TextInputEditText edtVideoUrl = view.findViewById(R.id.tiet_url_video);
        edtVideoUrl.setText(challenges.get(position).getVideoUrl());

        TextInputEditText edtSoundUrl = view.findViewById(R.id.tiet_url_sound);
        edtSoundUrl.setText(challenges.get(position).getSoundUrl());

        MaterialButton btnConfirm = view.findViewById(R.id.btn_confirm_update);

        AlertDialog alertDialog = dialog.create();
        alertDialog.show();

        btnConfirm.setOnClickListener(v -> {
            newChallengeDTO.setWord(edtName.getText().toString());
            newChallengeDTO.setImageUrl(edtImageUrl.getText().toString());
            newChallengeDTO.setVideoUrl(edtVideoUrl.getText().toString());
            newChallengeDTO.setSoundUrl(edtSoundUrl.getText().toString());

            update(idContext, newChallengeDTO, position);
            alertDialog.dismiss();
        });
    }

    private void update(Long idChallenge, NewChallengeDTO newChallengeDTO, int position) {
        Call call = RetrofitConfig.getAuthRetrofit(context).create(ChallengesService.class).updateChallenge(idChallenge, newChallengeDTO);
        call.enqueue(new Callback<ChallengeDTO>() {
            @Override
            public void onResponse(Call<ChallengeDTO> call, Response<ChallengeDTO> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context,"Desafio atualizado com sucesso",Toast.LENGTH_SHORT).show();
                    challenges.get(position).setWord(newChallengeDTO.getWord());
                    challenges.get(position).setImageUrl(newChallengeDTO.getImageUrl());
                    challenges.get(position).setVideoUrl(newChallengeDTO.getVideoUrl());
                    challenges.get(position).setSoundUrl(newChallengeDTO.getSoundUrl());
                    notifyItemChanged(position);
                }else if(response.code() == 401){
                    Toast.makeText(context,"Você não tem permissão para editar esse desafio",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context,"Ocorreu um problema ao tentar editar o desafio",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChallengeDTO> call, Throwable t) {
                Toast.makeText(context,"Não foi possivel estabelecer uma conexão",Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void delete(Long idChallenge, int position) {
        Call call = RetrofitConfig.getAuthRetrofit(context).create(ChallengesService.class).deleteChallenges(idChallenge);
        call.enqueue(new Callback<ChallengeDTO>() {
            @Override
            public void onResponse(Call<ChallengeDTO> call, Response<ChallengeDTO> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context,"Desafio deletado com sucesso",Toast.LENGTH_SHORT).show();
                    challenges.remove(position);
                    notifyItemRemoved(position);
                }else if(response.code() == 401){
                    Toast.makeText(context,"Você não tem permissão para excluir esse desafio",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context,"Ocorreu um problema ao tentar deletar o desafio",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChallengeDTO> call, Throwable t) {
                Toast.makeText(context,"Não foi possivel estabelecer uma conexão",Toast.LENGTH_SHORT).show();

            }
        });
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView imageChallenge;
        TextView challengeWord;
        MaterialButton editChallenge, deleteChallenge;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            imageChallenge = itemView.findViewById(R.id.challenge_image);
            challengeWord = itemView.findViewById(R.id.challenge_word);
            editChallenge = itemView.findViewById(R.id.btn_edit_challenge);
            deleteChallenge = itemView.findViewById(R.id.excluir_challenge);
        }
    }
}


