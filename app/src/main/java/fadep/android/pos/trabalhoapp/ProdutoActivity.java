package fadep.android.pos.trabalhoapp;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
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
    private Bitmap imagem;

    @BindView(R.id.edtNome) TextView edtNome;
    @BindView(R.id.edtPreco) TextView edtPreco;
    @BindView(R.id.edtDescricao) TextView edtDescricao;
//    @BindView(R.id.imgProduto) ImageView imgPorduto;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private FloatingActionButton btnRemoverImagem;

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

        btnRemoverImagem = findViewById(R.id.btn_remover_imagem);

        if (viewPagerAdapter.getCount() == 0)
            btnRemoverImagem.setVisibility(View.GONE);
    }

    public void abrirCamera(View view) {
        if (viewPagerAdapter.getCount() < 3) {
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
        } else {
            Toast toast = Toast.makeText(this, R.string.maximo_imagens_publicacao, Toast.LENGTH_LONG);
            toast.show();
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

            if (btnRemoverImagem.getVisibility() == View.GONE)
                btnRemoverImagem.setVisibility(View.VISIBLE);
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

//      produto.valor = Double.parseDouble(edtPreco.getText().toString());
        produto.valor = Double.parseDouble(edtPreco.getText().toString());
        produto.descricao = edtDescricao.getText().toString();
        reader.create(produto);
    }

    public void removerImagem(View view) {
        viewPagerAdapter.destroyItem((ViewGroup)findViewById(R.id.viewPager), viewPager.getCurrentItem(), viewPager.getChildAt(viewPager.getCurrentItem()));

        if (viewPagerAdapter.getCount() == 0)
            btnRemoverImagem.setVisibility(View.GONE);
    }
}
