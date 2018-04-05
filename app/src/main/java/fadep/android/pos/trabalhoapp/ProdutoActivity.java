package fadep.android.pos.trabalhoapp;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import butterknife.BindView;
import fadep.android.pos.trabalhoapp.sqlite.FeedProduto;
import fadep.android.pos.trabalhoapp.sqlite.FeedProdutoImagem;
import fadep.android.pos.trabalhoapp.WS.ImagemProduto;
import fadep.android.pos.trabalhoapp.WS.MainProduto;
import fadep.android.pos.trabalhoapp.WS.PordutoRetrofitModel;
import fadep.android.pos.trabalhoapp.sqlite.FeedReaderDbHelper;

public class ProdutoActivity extends AppCompatActivity {

    List<FeedProduto> produtoRoom;
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
        FeedProduto produto = new FeedProduto();
        produto.nome = edtNome.getText().toString();

        final List<ProdutoImagem> imagens = new ArrayList<>();
        for (Bitmap img: viewPagerAdapter.getImages()) {
            //PEGA A REFERÊNCIA DO IMAGEVIEW DO PERFIL E SETA O BACKGROUND COM A IMAGEM TIRADA
            //imageView.setImageBitmap(thumbnail);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            img.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

            ProdutoImagem produtoImagem = new ProdutoImagem(encoded, -1);
            imagens.add(produtoImagem);
        }

//      produto.valor = Double.parseDouble(edtPreco.getText().toString());
        produto.valor = Double.parseDouble(edtPreco.getText().toString());
        produto.descricao = edtDescricao.getText().toString();
        reader.create(produto, new Observer() {
            @Override
            public void update(Observable observable, Object idproduto) {
                Log.i("salvar produto", "produto salvo: " + idproduto);

                for (ProdutoImagem pi: imagens) {
                    FeedProdutoImagem fpi = new FeedProdutoImagem();
                    fpi.imagem = pi.getImagem();
                    fpi.idproduto = (int) idproduto;
                    reader.create(fpi);
                }
            }
        });
    }

    public void removerImagem(View view) {
        viewPagerAdapter.destroyItem((ViewGroup)findViewById(R.id.viewPager), viewPager.getCurrentItem(), viewPager.getChildAt(viewPager.getCurrentItem()));

        if (viewPagerAdapter.getCount() == 0)
            btnRemoverImagem.setVisibility(View.GONE);
    }


    public void salvarNoServidor(View view){
        MainProduto mainProduto = new MainProduto();
        PordutoRetrofitModel produto = new PordutoRetrofitModel();
        produto.setPreco(Double.parseDouble(edtPreco.getText().toString()));
        produto.setNome(edtNome.getText().toString());
        produto.setDescricao(edtDescricao.getText().toString());
        Date data = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        produto.setDataCadastro(sdf.format(data));
        produto.setDataAlteracao(sdf.format(data));
        produto.setDeletado(false);

        ImagemProduto imagemProduto = new ImagemProduto();
        imagemProduto.setDataCadastro(sdf.format(data));
        imagemProduto.setDataAlteracao(sdf.format(data));
        imagemProduto.setDeletado(false);
        imagemProduto.setImagem("888");

        mainProduto.salvarProduto(produto, imagemProduto);
        Toast toast = Toast.makeText( getApplicationContext(), "Salvo Com Sucesso",Toast.LENGTH_LONG);
        toast.show();

    }



}
