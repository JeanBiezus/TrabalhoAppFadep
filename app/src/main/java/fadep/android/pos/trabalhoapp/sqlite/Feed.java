package fadep.android.pos.trabalhoapp.sqlite;

import fadep.android.pos.trabalhoapp.Produto;

/**
 * Created by Jean on 17/03/2018.
 */

public class Feed  extends Produto{

    @Override

    // vai mostrar deste jeito na tela de lista
    public String toString() {
        return "id: " + _id + "\n" + "Titulo: " + nome + "\n" + "DESCRICAO: " + descricao;
    }
}
