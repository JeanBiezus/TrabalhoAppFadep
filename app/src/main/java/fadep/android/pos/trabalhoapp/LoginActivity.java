package fadep.android.pos.trabalhoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import fadep.android.pos.trabalhoapp.WS.MainProduto;

/**
 * Created by jean on 14/03/18.
 */

public class LoginActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_login);
        setSupportActionBar(myToolbar);

        //MOSTRA O NOME DO APP E O BOTÃO VOLTAR NO TOOLBAR
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        myToolbar.setTitle("");
    }

    //ABRE A ACTIVITY DE CADASTRO DE USUARIO
    public void novoUsuario(View view) {
        Intent intent = new Intent(this, MainProduto.class);
        startActivityForResult(intent, 1);
    }
}
