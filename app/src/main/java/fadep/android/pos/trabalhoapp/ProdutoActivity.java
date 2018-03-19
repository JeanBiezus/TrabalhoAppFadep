package fadep.android.pos.trabalhoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fadep.android.pos.trabalhoapp.Rom.AppDatabase;
import fadep.android.pos.trabalhoapp.Rom.Produto;

public class ProdutoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);
    }
    private static Produto addProduto (final AppDatabase db, Produto produto) {
        db.produtoDAO().create(produto);
        return produto;
    }

    private static void populateWithTestData (AppDatabase db) {
        Produto produto = new Produto();
        produto.setNome("");
        produto.setNome(" ");
        produto.setValor(0);
        addProduto(db, produto);
    }
}
