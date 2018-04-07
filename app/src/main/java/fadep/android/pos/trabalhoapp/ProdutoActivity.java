package fadep.android.pos.trabalhoapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.Button;
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
    private int idProduto = 0;

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

        Intent intent = getIntent();
        long idProduto = intent.getLongExtra("idProduto", -1);
        if (idProduto > 0) {
            this.idProduto = (int) idProduto;
            FeedProduto produto = reader.findById(idProduto, null);

            viewPagerAdapter.setImages(convertImagesToBitmap(produto.imagens));
            edtNome.setText(produto.nome);
            edtDescricao.setText(produto.descricao);
            edtPreco.setText(Double.toString(produto.valor));
        }

        btnRemoverImagem = findViewById(R.id.btn_remover_imagem);
        if (viewPagerAdapter.getCount() == 0)
            btnRemoverImagem.setVisibility(View.GONE);

        Button btnRemoverPublicacao = findViewById(R.id.btn_remover_publicacao);
        if (this.idProduto == 0) {
            btnRemoverPublicacao.setVisibility(View.GONE);
        }

        LinearLayout buttonsContainer = findViewById(R.id.image_buttons_container);
        buttonsContainer.bringToFront();

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
            Bitmap imagem = data.getParcelableExtra("data");
            viewPagerAdapter.addImage(imagem);
            LinearLayout buttonsContainer = findViewById(R.id.image_buttons_container);
            buttonsContainer.bringToFront();

            if (btnRemoverImagem.getVisibility() == View.GONE)
                btnRemoverImagem.setVisibility(View.VISIBLE);
        }
    }

    public void salvarRoom(View view) {
        FeedProduto produto = new FeedProduto();
        produto.nome = edtNome.getText().toString();
        produto.valor = Double.parseDouble(edtPreco.getText().toString());
        produto.descricao = edtDescricao.getText().toString();

        final ProdutoActivity pa = this;
        if (idProduto == 0) {
            reader.create(produto, new Observer() {
                @Override
                public void update(Observable observable, Object idproduto) {
                    Log.i("salvar produto", "produto salvo: " + idproduto);

                    for (ProdutoImagem pi : convertImagesToRoom()) {
                        FeedProdutoImagem fpi = new FeedProdutoImagem();
                        fpi.imagem = pi.getImagem();
                        fpi.idproduto = (int) idproduto;
                        reader.create(fpi);
                    }

                    pa.fecharActivity("Salvo com sucesso");;
                }
            });
        } else {
            reader.deletarImagens(idProduto);

            produto._id = idProduto;
            reader.update(produto, new Observer() {
                @Override
                public void update(Observable observable, Object o) {
                    for (ProdutoImagem pi : convertImagesToRoom()) {
                        FeedProdutoImagem fpi = new FeedProdutoImagem();
                        fpi.imagem = pi.getImagem();
                        fpi.idproduto = idProduto;
                        reader.create(fpi);
                    }

                    pa.fecharActivity("Savo com sucesso");
                }
            });
        }
    }

    public void removerImagem(View view) {
        viewPagerAdapter.destroyItem((ViewGroup)findViewById(R.id.viewPager), viewPager.getCurrentItem(), viewPager.getChildAt(viewPager.getCurrentItem()));

        if (viewPagerAdapter.getCount() == 0)
            btnRemoverImagem.setVisibility(View.GONE);
    }


    public void salvarNoServidor(View view){
        if (isNetworkAvailable()) {
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
            produto.setImagens(convertImagesToWS());

            mainProduto.salvarProduto(produto, convertImagesToWS());
            Toast toast = Toast.makeText(getApplicationContext(), "Salvo Com Sucesso", Toast.LENGTH_LONG);
            toast.show();
        } else {
            salvarRoom(view);
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public List<ImagemProduto> convertImagesToWS() {
        List<ImagemProduto> imagens = new ArrayList<>();
        for (Bitmap img: viewPagerAdapter.getImages()) {
            //PEGA A REFERÊNCIA DO IMAGEVIEW DO PERFIL E SETA O BACKGROUND COM A IMAGEM TIRADA
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            img.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();
            String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

            ImagemProduto imagemProduto = new ImagemProduto(encoded);
            imagens.add(imagemProduto);
        }

        return imagens;
    }

    public List<ProdutoImagem> convertImagesToRoom() {
        List<ProdutoImagem> imagens = new ArrayList<>();
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

        return imagens;
    }

    public List<Bitmap> convertImagesToBitmap(List<ProdutoImagem> imagens) {
        List<Bitmap> bitMapImages = new ArrayList<>();
        for (ProdutoImagem produtoImagem: imagens) {
            byte[] decodedString = Base64.decode(produtoImagem.getImagem(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            bitMapImages.add(decodedByte);
        }

        return bitMapImages;
    }

    public void removerRoom(View view) {
        reader.deletarImagens(idProduto);
        reader.delete(idProduto);
        fecharActivity("Removido com sucesso");
    }

    public void fecharActivity(String returnText) {
        Toast.makeText(this, returnText, Toast.LENGTH_LONG).show();
        finish();
    }
}
