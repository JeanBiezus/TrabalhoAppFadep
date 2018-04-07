package fadep.android.pos.trabalhoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import fadep.android.pos.trabalhoapp.WS.AbstractWS;
import fadep.android.pos.trabalhoapp.WS.Login;
import fadep.android.pos.trabalhoapp.WS.MainProduto;
import fadep.android.pos.trabalhoapp.WS.Usuario;
import retrofit2.Call;

/**
 * Created by jean on 14/03/18.
 */

public class LoginActivity extends AppCompatActivity{
    TextView txtLogin;
    TextView  txtSenha;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_login);
        setSupportActionBar(myToolbar);
        txtLogin = findViewById(R.id.edt_login);
        txtSenha = findViewById(R.id.edt_senha);
        //MOSTRA O NOME DO APP E O BOTÃO VOLTAR NO TOOLBAR
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //myToolbar.setTitle("");
    }

    //ABRE A ACTIVITY DE CADASTRO DE USUARIO
    public void novoUsuario(View view) {

        Intent intent = new Intent(this, CadastroUsuarioActivity.class);
        startActivityForResult(intent, 1);
    }

    public void logar(View view){
        Date data = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        MainProduto abs = new MainProduto();
        Login login = new Login();
        login.setLogin(txtLogin.getText().toString());
        login.setSenha(txtSenha.getText().toString());
        login.setDataCadastro(sdf.format(data));
        login.setDataAlteracao(sdf.format(data));
        login.setDeletado(false);
        login.setAdm(false);
        abs.loga(login);
//        Toast toast = Toast.makeText(getApplicationContext(), "Salvo Com Sucesso", Toast.LENGTH_LONG);
//        toast.show();
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivityForResult(intent, 1);
    }
}
