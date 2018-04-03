package fadep.android.pos.trabalhoapp.WS;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by Jean on 02/04/2018.
 */

public interface ProdutoWS {

    @POST("salvar")
    Call<PordutoRetrofitModel> salvar(@Header("content-type") String contentType, @Body PordutoRetrofitModel produto);

    @GET
    void findAll(Callback<PordutoRetrofitModel> sss);


}
