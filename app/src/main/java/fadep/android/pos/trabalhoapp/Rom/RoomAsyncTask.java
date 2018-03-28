package fadep.android.pos.trabalhoapp.Rom;

import android.os.AsyncTask;
import android.widget.ListView;


import java.util.List;



/**
 * Created by Jean on 21/03/2018.
 */

public class RoomAsyncTask extends AsyncTask<Void, Void, Void> {

    List<Produto> produtos;
    ListView lista;
    String edtNome,  edtDescricao, imgProduto;
    Double edtPreco;
    Produto produto;

    public void setEdtNome(String edtNome) {
        this.edtNome = edtNome;
    }
    public void setPreco(Double edtSubTitle) {
        this.edtPreco = edtSubTitle;
    }
    public void setEdtDescricao(String edtDescricao) {
        this.edtDescricao = edtDescricao;
    }
    public void setImgProduto(String imgProduto) {
        this.imgProduto = imgProduto;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        produto = new Produto();
        produto.nome = this.edtNome;
        produto.valor = this.edtPreco;
        produto.descricao = this.edtDescricao;
        produto.imgProduto = this.imgProduto;
    }

    @Override
    protected Void doInBackground(Void... params) {

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
    }
}
