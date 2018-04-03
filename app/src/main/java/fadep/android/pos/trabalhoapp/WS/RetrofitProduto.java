package fadep.android.pos.trabalhoapp.WS;

/**
 * Created by Jean on 02/04/2018.
 */

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitProduto {


    public final ProdutoWS produtoWS;

    public RetrofitProduto() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.2.102:8085/WebMobile-0.0.1-SNAPSHOT/rest/produto/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        produtoWS = retrofit.create(ProdutoWS.class);
    }
}
