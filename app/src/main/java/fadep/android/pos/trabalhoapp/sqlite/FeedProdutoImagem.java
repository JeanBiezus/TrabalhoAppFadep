package fadep.android.pos.trabalhoapp.sqlite;

import fadep.android.pos.trabalhoapp.Rom.ProdutoImagem;

/**
 * Created by Jean on 17/03/2018.
 */

public class FeedProdutoImagem extends ProdutoImagem{

    @Override

    // vai mostrar deste jeito na tela de lista
    public String toString() {
        return "id: " + _id + "\n" + "imagem: " + imagem + "\n" + "idPublicacao: " + idproduto;
    }
}
