package fadep.android.pos.trabalhoapp;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Jean on 19/03/2018.
 */
public class Produto {
    public int _id;
    public String nome;
    public String descricao;
    public double valor;
    public String imagem1;
    public String imagem2;
    public String imagem3;
    public String localizacao;

    public Produto getProduto() {
        return this;
    }

}
