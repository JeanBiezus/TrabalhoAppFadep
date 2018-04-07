package fadep.android.pos.trabalhoapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import fadep.android.pos.trabalhoapp.WS.MainProduto;
import fadep.android.pos.trabalhoapp.WS.Usuario;

/**
 * Created by jean on 15/03/18.
 */

public class CadastroUsuarioActivity extends AppCompatActivity {

    @BindView(R.id.edtNome) TextView edtNome;
    @BindView(R.id.edtLogin) TextView edtLogin;
    @BindView(R.id.edtsenha) TextView edtSenha;
    String encoded;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_usuario_activity);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        edtNome = findViewById(R.id.edtNome);
        edtLogin = findViewById(R.id.edtLogin);
        edtSenha = findViewById(R.id.edtsenha);


        //MOSTRA O NOME DO APP E O BOTÃO DE VOLTAR NA TOOLBAR
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void abrirCamera(View view) {
        //INSTANCIA O PACKAGEMANAGER PARA PEGAR O CAMINHO DOS ARQUIVOS DO APP
        PackageManager m = getPackageManager();
        String s = getPackageName();
        try {
            PackageInfo p = m.getPackageInfo(s, 0);
            s = p.applicationInfo.dataDir;
        } catch (PackageManager.NameNotFoundException e) {
            Log.w("yourtag", "Error Package name not found ", e);
        }

        //ABRE A CÂMERA
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.withAppendedPath(Uri.parse(s), "profile_img"));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 1);
        }
    }


    //AGUARDA O RETORNO DA IMAGEM
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {

            //PEGA A IMAGEM TIRADA
            Bitmap thumbnail = data.getParcelableExtra("data");


            //PEGA A REFERÊNCIA DO IMAGEVIEW DO PERFIL E SETA O BACKGROUND COM A IMAGEM TIRADA
            ImageView imageView = (ImageView) findViewById(R.id.profile_image);
            //imageView.setImageBitmap(thumbnail);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

            byte[] decodedString = Base64.decode(encoded, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

            imageView.setImageBitmap(decodedByte);
        }
    }

    public void Salvar(View view) {
        MainProduto mainProduto = new MainProduto();
        Date data = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Usuario u = new Usuario();
        u.setDataAlteracao(sdf.format(data));
        u.setDataCadastro(sdf.format(data));
        u.setDeletado(false);
        u.setNome(edtNome.getText().toString());
        u.setLogin(edtLogin.getText().toString());
        u.setSenha(edtSenha.getText().toString());
        u.setFoto(encoded);

        mainProduto.salvarUsuario(u);
        Toast toast = Toast.makeText(getApplicationContext(), "Salvo Com Sucesso", Toast.LENGTH_LONG);
        toast.show();

//        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
//        SharedPreferences.Editor editor = pref.edit();
//        editor.putInt("idUsuarioLogado", 10);
//        editor.putString("salvaUsuario", edtNome.getText().toString());
//        editor.commit();



    }
}
