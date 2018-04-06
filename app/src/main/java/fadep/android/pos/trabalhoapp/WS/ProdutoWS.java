package fadep.android.pos.trabalhoapp.WS;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Jean on 02/04/2018.
 */

public interface ProdutoWS {

    @POST("produto/salvar")
    Call<PordutoRetrofitModel> salvar( @Body PordutoRetrofitModel produto);
//    Call<PordutoRetrofitModel> salvar(@Header("content-type") String contentType, @Body PordutoRetrofitModel produto);
    @POST("produtoimagem/salvar")
    Call<ImagemProduto> salvarImagem( @Body List<ImagemProduto> imagemProduto);

    @GET("produto")
    Call<List<PordutoRetrofitModel>> findALL();

    @GET("produto/todos")
    Call<List<PordutoRetrofitModel>> buscarProdutos();

    @GET("produto/{id}")
    Call<PordutoRetrofitModel> findById(@Path("id") int id);


}
