package fadep.android.pos.trabalhoapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jean on 27/03/18.
 */

public class ProdutoListaAdapter extends BaseAdapter {
    private final List<Produto> produtos;
    private final Activity act;

    public ProdutoListaAdapter(List<Produto> produtos, Activity act) {
        this.produtos = produtos;
        this.act = act;
    }

    @Override
    public int getCount() {
        return produtos.size();
    }

    @Override
    public Produto getItem(int i) {
        return produtos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return produtos.get(i)._id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = act.getLayoutInflater()
                .inflate(R.layout.item_lista, viewGroup, false);

        Produto produto = produtos.get(i);

        //PEGA A REFERENCIA DOS TEXTVIEWS
        TextView txtTitulo = v.findViewById(R.id.txt_titulo_produto_lista);
        TextView txtDescricao = v.findViewById(R.id.txt_descricao_produto_lista);
        TextView txtValor = v.findViewById(R.id.txt_preco_produto_lista);
        TextView txtLocal = v.findViewById(R.id.txt_local_produto_lista);
        ImageView imgProduto = v.findViewById(R.id.imagem_produto_lista);

        //PASSA OS DADOS DO PRODUTO PARA AS RESPECTIVAS TEXTVIEWS
        txtTitulo.setText(produto.nome);
        txtDescricao.setText(produto.descricao);
        txtValor.setText("R$ " + Double.toString(produto.valor));
        txtLocal.setText(produto.localizacao);

//        byte[] decodedString = Base64.decode(produto.imgProduto, Base64.DEFAULT);
//        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

//        imgProduto.setImageBitmap(decodedByte);

        return v;
    }
}
