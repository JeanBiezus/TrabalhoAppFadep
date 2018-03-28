package fadep.android.pos.trabalhoapp.sqlite;

/**
 * Created by Jean on 17/03/2018.
 */

public class Feed {

    public int _id;
    public String nome;
    public String descricao;
    public String valor;
    public String imgProduto;
    @Override

    // vai mostrar deste jeito na tela de lista
    public String toString() {
        return "id: " + _id + "\n" + "Titulo: " + nome + "\n" + "DESCRICAO: " + descricao;
    }
}
