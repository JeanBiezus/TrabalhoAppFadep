package fadep.android.pos.trabalhoapp.Rom;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Jean on 19/03/2018.
 */
@Entity
public class Produto {
    @PrimaryKey
    public int _id;
    public String nome;
    public String descricao;
    public double valor;

    public int get_id() {
        return _id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getValor() {
        return valor;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
