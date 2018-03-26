package fadep.android.pos.trabalhoapp.Rom;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Jean on 19/03/2018.
 */
@Entity
public class Produto {
    @PrimaryKey(autoGenerate = true)
    public int _id;
    public String nome;
    public String descricao;
    public double valor;
    public String imgProduto;


}
