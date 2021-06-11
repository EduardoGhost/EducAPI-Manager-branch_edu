package br.ufpb.dcx.apps4society.educapimanager.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import br.ufpb.dcx.apps4society.educapimanager.R;
import br.ufpb.dcx.apps4society.educapimanager.helper.RetrofitConfig;
import br.ufpb.dcx.apps4society.educapimanager.model.bean.User;
import br.ufpb.dcx.apps4society.educapimanager.model.dto.UserDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private Context context = this;
    //TODO("Implementar feedback por cor nos campos abaixo")
    private TextInputLayout tilName,tilEmail,tilPassword;
    private EditText edtName,edtEmail,edtPassword, edtConfPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_register);

        //initialização dos componentes
        tilName = findViewById(R.id.tilNameRegister);
        tilEmail = findViewById(R.id.tilEmailRegister);
        tilPassword = findViewById(R.id.tilPasswordRegister);
        edtName = findViewById(R.id.edtTxNameRegister);
        edtEmail = findViewById(R.id.edtTxEmailRegister);
        edtPassword = findViewById(R.id.edtTxPasswordRegister);
        edtConfPassword = findViewById(R.id.edtTxPasswordConfirm);

        Toolbar register_toolbar = findViewById(R.id.toolbar_register);

        register_toolbar.setNavigationOnClickListener(v -> {
            finish();
        });

    }

    public void cadastrarUsuario(View view){
        if (verificarCamposDeTexto()){
            if (verificarSenhasDigitadas()){
                Call<User> call = RetrofitConfig.userNewService().insertNewUser(
                        new UserDTO(
                                edtName.getText().toString(),
                                edtEmail.getText().toString(),
                                edtPassword.getText().toString()));
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(context,"Cadastrado Com Sucesso",Toast.LENGTH_SHORT).show();
                            Intent ir = new Intent();
                            ir.setClass(context,LoginActivity.class);
                            startActivity(ir);

                        }else if(response.code() == 400){
                            Toast.makeText(context,"Não foi possível cadastrar, verifique se sua senha tem entre 8-12 caracteres e tente novamente",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context,"Algo de errado ocorreu",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(context,"Não Foi possível se comunicar com o sistema",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    private boolean verificarCamposDeTexto(){
        if (edtName.getText().toString().isEmpty() || edtPassword.getText().toString().isEmpty() || edtEmail.getText().toString().isEmpty() || edtConfPassword.getText().toString().isEmpty()){
            Toast.makeText(RegisterActivity.this, "Nenhum dos campos devem estar vazios!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean verificarSenhasDigitadas(){
        if (edtPassword.getText().toString().equals(edtConfPassword.getText().toString())){
            return true;
        }
        Toast.makeText(RegisterActivity.this, "As senhas não correspondem! Por favor, corrigir.", Toast.LENGTH_LONG).show();
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
