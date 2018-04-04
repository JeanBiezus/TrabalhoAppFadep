package fadep.android.pos.trabalhoapp;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.List;

import butterknife.BindView;
import fadep.android.pos.trabalhoapp.sqlite.Feed;
import fadep.android.pos.trabalhoapp.sqlite.FeedReaderDbHelper;

public class ProdutoActivity extends AppCompatActivity {

    List<Feed> produtoRoom;
    private FeedReaderDbHelper reader;
    ListView lista;
    private Bitmap imagem;
    private List<Bitmap> imagens;

    @BindView(R.id.edtNome) TextView edtNome;
    @BindView(R.id.edtPreco) TextView edtPreco;
    @BindView(R.id.edtDescricao) TextView edtDescricao;
//    @BindView(R.id.imgProduto) ImageView imgPorduto;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

        edtPreco = findViewById(R.id.edtPreco);
        edtNome = findViewById(R.id.edtNome);
        edtDescricao = findViewById(R.id.edtDescricao);
        reader = new FeedReaderDbHelper(this);
        viewPager = findViewById(R.id.viewPager);

        viewPagerAdapter = new ViewPagerAdapter(this);

        viewPager.setAdapter(viewPagerAdapter);
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
            imagem = data.getParcelableExtra("data");
            viewPagerAdapter.addImage(imagem);
            LinearLayout buttonsContainer = findViewById(R.id.image_buttons_container);
            buttonsContainer.bringToFront();
        }
    }

    public void publicar(View view) {
        //PEGA A REFERÊNCIA DO IMAGEVIEW DO PERFIL E SETA O BACKGROUND COM A IMAGEM TIRADA
        //imageView.setImageBitmap(thumbnail);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        imagem.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);


        Feed produto = new Feed();
        produto.nome = edtNome.getText().toString();
        produto.imgProduto = encoded.toString();
//        produto.valor = Double.parseDouble(edtPreco.getText().toString());
        produto.valor = Double.parseDouble(edtPreco.getText().toString());
        produto.descricao = edtDescricao.getText().toString();
        reader.create(produto);

    }




}
