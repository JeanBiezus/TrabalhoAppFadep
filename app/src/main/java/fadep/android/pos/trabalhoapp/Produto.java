package fadep.android.pos.trabalhoapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jean on 19/03/2018.
 */
public class Produto {
    public int _id;
    public String nome;
    public String descricao;
    public double valor;
    public List<ProdutoImagem> imagens = new ArrayList<>();
    public String localizacao;

    public Produto getProduto() {
        return this;
    }

}
