package br.ufpb.dcx.apps4society.educapimanager.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.icu.text.StringSearch;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.ufpb.dcx.apps4society.educapimanager.R;
import br.ufpb.dcx.apps4society.educapimanager.control.facade.CreateObjectFacade;
import br.ufpb.dcx.apps4society.educapimanager.control.service.response.LoginResponse;
import br.ufpb.dcx.apps4society.educapimanager.helper.GerenteDeSessao;
import br.ufpb.dcx.apps4society.educapimanager.helper.RetrofitConfig;
import br.ufpb.dcx.apps4society.educapimanager.model.bean.Session;
import br.ufpb.dcx.apps4society.educapimanager.model.dto.UserDTO;
import br.ufpb.dcx.apps4society.educapimanager.model.dto.UserLogin;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private Context context = this;

    private EditText edtTxEmail, edtTxPassword;
    private Button btnLogin;
    //TODO("implementar 'esqueci minha senha', redirecionar para outra tela onde ira poder fazer a troca da senha")
    private UserDTO userAuth;
    private GerenteDeSessao gerenteDeSessao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_login);

        //INICIALIZAÇÃO DOS COMPONENTES
        edtTxEmail = findViewById(R.id.edtTxEmail);
        edtTxPassword = findViewById(R.id.edtTxPassword);

        gerenteDeSessao = new GerenteDeSessao(this);
    }

    public void autenticarUsuario(View view) {

        if (validaEmail() == false) {
            Toast.makeText(LoginActivity.this, "Email faltando o '@'", Toast.LENGTH_LONG).show();

        } else {
            if(verificarCamposDeTexto()) {

                UserLogin userLogin = new UserLogin(edtTxEmail.getText().toString(), edtTxPassword.getText().toString());
                Call call = RetrofitConfig.loginService().login(userLogin);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {


                        if (response.code() == 200) {
                            LoginResponse loginResponse = response.body();
                            gerenteDeSessao.saveAuthToken(loginResponse.getToken());
                            userAuth = new UserDTO(userLogin.getEmail(), userLogin.getPassword());
                            CreateObjectFacade.Companion.getInstance().setTempSession(new Session(userAuth));
                            System.out.println(CreateObjectFacade.Companion.getInstance().getTempSession().getCreator().getEmail());
                            openNavDrawerActivity();
                        } else {
                            Toast.makeText(LoginActivity.this, "E-mail ou senha incorretos! Verifique e tente novamente.", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Falha na requisição. Tente novamente!", Toast.LENGTH_LONG).show();
                    }
                });


    }}}



    private void openNavDrawerActivity(){
        Intent next = new Intent();
        next.setClass(context, NavDrawerActivity.class);
        startActivity(next);
        finish();
    }

    public void openSemCadastroActivity(View view){
        Intent ir = new Intent();
        ir.setClass(context, RegisterActivity.class);
        startActivity(ir);
    }

    private boolean verificarCamposDeTexto(){
        if (edtTxEmail.getText().toString().isEmpty() || edtTxPassword.getText().toString().isEmpty()){
            Toast.makeText(LoginActivity.this, "Insira o seu e-mail e senha!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean validaEmail(){
        boolean error;
        if(edtTxEmail.getText().toString().contains("@")){
            error = true;
        }else {
            error = false;

        }return error;
    }


    private boolean illegalSimbolsEmail() {
        boolean er = false;
        String[] letras = {"!", "#", "$", "%", "&", "*", "/", "+", "=", "?", "|", "~", "^", "]", "[", "{", "}", ","};
        for (String l : letras) {
            if (edtTxEmail.getText().toString().equals(l)) {
                er = true;

            } else {
                er = false;

            }

        }return er;
    }
}




