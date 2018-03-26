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
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import fadep.android.pos.trabalhoapp.Rom.AppDatabase;
import fadep.android.pos.trabalhoapp.Rom.Produto;
import fadep.android.pos.trabalhoapp.Rom.RoomAsyncTask;

public class ProdutoActivity extends AppCompatActivity {

    @BindView(R.id.edtNome) TextView edtNome;
    @BindView(R.id.edtPreco) TextView edtPreco;
    @BindView(R.id.edtDescricao) TextView edtDescricao;
    @BindView(R.id.imgProduto) ImageView imgPorduto;
    RoomAsyncTask aRoom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);
    }
    private static Produto addProduto (final AppDatabase db, Produto produto) {
        db.produtoDAO().create(produto);
        return produto;
    }

    private static void populateWithTestData (AppDatabase db) {
        Produto produto = new Produto();
//        produto.setNome("");
//        produto.setNome(" ");
//        produto.setValor(0);
        addProduto(db, produto);
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
        aRoom = new RoomAsyncTask();
        aRoom.setEdtNome(edtNome.getText().toString());
        aRoom.setPreco(Double.parseDouble(edtPreco.getText().toString()));
        aRoom.setEdtDescricao(edtDescricao.getText().toString());
        aRoom.execute();

    }
}
