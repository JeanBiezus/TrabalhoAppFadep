package fadep.android.pos.trabalhoapp;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import fadep.android.pos.trabalhoapp.Rom.AppDatabase;
import fadep.android.pos.trabalhoapp.Rom.ControlLifeCycleApp;
import fadep.android.pos.trabalhoapp.Rom.ProdutoDAO;
import fadep.android.pos.trabalhoapp.sqlite.Feed;
import fadep.android.pos.trabalhoapp.sqlite.FeedReaderDbHelper;

public class ProdutoActivity extends AppCompatActivity {

    List<Feed> produtoRoom;
    private FeedReaderDbHelper reader;
    ListView lista;

    @BindView(R.id.edtNome) TextView edtNome;
    @BindView(R.id.edtPreco) TextView edtPreco;
    @BindView(R.id.edtDescricao) TextView edtDescricao;
//    @BindView(R.id.imgProduto) ImageView imgPorduto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

        edtPreco = findViewById(R.id.edtPreco);
        edtNome = findViewById(R.id.edtNome);
        edtDescricao = findViewById(R.id.edtDescricao);
        reader = new FeedReaderDbHelper(this);
        lista = findViewById(R.id.lista);

        produtoRoom = reader.read();
        ArrayAdapter<Produto> listaAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, produtoRoom);
        lista.setAdapter(listaAdapter);


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

    public void publicar(View view) {
        Feed produto = new Feed();
        produto.nome = edtNome.getText().toString();
//        produto.valor = Double.parseDouble(edtPreco.getText().toString());
        produto.valor = edtPreco.getText().toString();
        produto.descricao = edtDescricao.getText().toString();
        reader.create(produto);

    }
}
