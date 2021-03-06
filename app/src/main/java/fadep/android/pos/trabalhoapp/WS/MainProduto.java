package fadep.android.pos.trabalhoapp.WS;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Observer;

import fadep.android.pos.trabalhoapp.CadastroUsuarioActivity;
import fadep.android.pos.trabalhoapp.LoginActivity;
import fadep.android.pos.trabalhoapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainProduto extends AppCompatActivity{

    private String BASE_URL = "http://192.168.2.113:8085/WebMobile-0.0.1-SNAPSHOT/rest/";
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


        AbstractWS ws = retrofit.create(AbstractWS.class);
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
                    AbstractWS ws = retrofit.create(AbstractWS.class);

                    Log.i("dss", "ds" + response.body().getId()+response.body().getNome());
                    // salva na tabela imagem as imagens
                    for(ImagemProduto imagemProduto : imagemProduto){
                        imagemProduto.setIdProduto(response.body().getId());

                    }
                    Log.i("TAG", "onResponse: ENVIANDO IMAGENS");
                    //final Call<ImagemProduto> imagemProdutos = ws.salvarImagem(imagemProduto);

//                    imagemProdutos.enqueue(new Callback<ImagemProduto>() {
//                        @Override
//                        public void onResponse(Call<ImagemProduto> call, Response<ImagemProduto> retorno) {
//                            if (retorno.isSuccessful()){
//                                Log.i("TAG", "onResponse: SALVOU IMAGENS");
//                                return;
//                            } else {
//                                Log.i("TAG", "onResponse: ERRO AO SALVAR IMAGENS " + retorno.message());
//
//                            }
//                        }
//
//
//                        @Override
//                        public void onFailure(Call<ImagemProduto> call, Throwable t) {
//                            Log.i("ERROR", "onFailure: " + t.getMessage());
//                        }
//                    });



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

        AbstractWS ws = retrofit.create(AbstractWS.class);
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

    public void buscarProdutos(final Observer callback){

        //        FAZ O FIND NO SERVIDOR
        Gson g = new GsonBuilder().registerTypeAdapter(PordutoRetrofitModel.class, new ProdutoDec() ).setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(g))
                .build();

        AbstractWS ws = retrofit.create(AbstractWS.class);
        Call<List<PordutoRetrofitModel>> produtos = ws.buscarProdutos();

        produtos.enqueue(new Callback<List<PordutoRetrofitModel>>() {
            @Override
            public void onResponse(Call<List<PordutoRetrofitModel>> call, Response<List<PordutoRetrofitModel>> response) {
                if (response.isSuccessful()){
                    List<PordutoRetrofitModel> prods = response.body();
                    for(PordutoRetrofitModel p : prods){
                        Log.i("NOME", "NOME +"+ p.getNome() + "id"+ p.getId());

                    }

                    callback.update(null, response.body());

                }else{
//                    Toast.makeText(getApplicationContext(),"erro" + response.code(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<PordutoRetrofitModel>> call, Throwable t) {
//                Toast.makeText(getApplicationContext(),"erro" + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.i("ERROR", "onFailure: " + t.getMessage());
            }
        });

    }

    public void salvarUsuario(final Usuario usuario){
        AbstractWS ws = retrofit.create(AbstractWS.class);
        final Call<Usuario> u = ws.salvarUsuario(usuario);
       u.enqueue(new Callback<Usuario>() {
           @Override
           public void onResponse(Call<Usuario> call, Response<Usuario> response) {
               if (response.isSuccessful()) {
               Log.e("SALVOU USUARo", " "+ response.body().getNome() + response.body().getId());

               }
           }

           @Override
           public void onFailure(Call<Usuario> call, Throwable t) {

           }
       });
    }
    public void loga(Login login){
        AbstractWS ws = retrofit.create(AbstractWS.class);
       final Call<Login> l = ws.logar(login);
       l.enqueue(new Callback<Login>() {
           @Override
           public void onResponse(Call<Login> call, Response<Login> response) {
               Log.e("PRINTOOOO", " PRINTOOOO"+ response.body());
               if (response.isSuccessful()) {
                   Log.e("PRINTOOOO", " PRINTOOOO"+ response.body());

//                   callback.update(null, response.body());
                   return;
               }
           }

           @Override
           public void onFailure(Call<Login> call, Throwable t) {
               Log.e("PRINTOOOO", " FFFFFFF00");
           }
       });
    }
}
