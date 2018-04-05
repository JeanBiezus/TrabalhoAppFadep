package fadep.android.pos.trabalhoapp.WS;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import fadep.android.pos.trabalhoapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainProduto extends AppCompatActivity{

    private String BASE_URL = "http://192.168.2.102:8085/WebMobile-0.0.1-SNAPSHOT/rest/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testandooquevemdoservidorr);
    }

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public void salvarProduto(final PordutoRetrofitModel produtoModel, final List<ImagemProduto> imagemProduto){


        ProdutoWS ws = retrofit.create(ProdutoWS.class);
//        PordutoRetrofitModel p = new PordutoRetrofitModel() ;
//        p.setNome("JEAN");
//        p.setDescricao("JEAN");
//        p.setPreco(23.2);
//        Date data = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
//        p.setDataCadastro(sdf.format(data));
//        p.setDeletado(false);

        final Call<PordutoRetrofitModel> produtos = ws.salvar(produtoModel);

        produtos.enqueue(new Callback<PordutoRetrofitModel>() {
            @Override
            public void onResponse(Call<PordutoRetrofitModel> call, Response<PordutoRetrofitModel> response) {
                if (response.isSuccessful()){
                    ProdutoWS ws = retrofit.create(ProdutoWS.class);

                    Log.i("dss", "ds" + response.body().getId()+response.body().getNome());
                    // salva na tabela imagem as imagens
                    for(ImagemProduto imagemProduto : imagemProduto){
                        imagemProduto.setIdProduto(response.body().getId());

                    }
                    final Call<ImagemProduto> imagemProdutos = ws.salvarImagem(imagemProduto);

                    imagemProdutos.enqueue(new Callback<ImagemProduto>() {
                        @Override
                        public void onResponse(Call<ImagemProduto> call, Response<ImagemProduto> retorno) {
                            if (retorno.isSuccessful()){
                                return;
                            }
                        }

                        @Override
                        public void onFailure(Call<ImagemProduto> call, Throwable t) {

                        }
                    });



                }else{
//                    Toast.makeText(getApplicationContext(),"erro" + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PordutoRetrofitModel> call, Throwable t) {

            }
        });
    }

    public void findAll(){

    //        FAZ O FINDALL NO SERVIDOR
        Gson g = new GsonBuilder().registerTypeAdapter(PordutoRetrofitModel.class, new ProdutoDec() ).create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(g))
                .build();

        ProdutoWS ws = retrofit.create(ProdutoWS.class);
        Call<List<PordutoRetrofitModel>> produtos = ws.findALL();

        produtos.enqueue(new Callback<List<PordutoRetrofitModel>>() {
            @Override
            public void onResponse(Call<List<PordutoRetrofitModel>> call, Response<List<PordutoRetrofitModel>> response) {
                if (response.isSuccessful()){
                    List<PordutoRetrofitModel> prods = response.body();
                    for(PordutoRetrofitModel p : prods){
                        Log.i("NOME", "NOME +"+ p.getNome() + "id"+ p.getId());

                    }
                }else{
                    Toast.makeText(getApplicationContext(),"erro" + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<PordutoRetrofitModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"erro" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}
