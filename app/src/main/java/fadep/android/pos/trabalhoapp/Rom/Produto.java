package fadep.android.pos.trabalhoapp.Rom;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

/**
 * Created by Jean on 19/03/2018.
 */
@Entity(tableName = "produto")
public class Produto {
    @PrimaryKey(autoGenerate = true)
    public int _id;
    public String nome;
    public String descricao;
    public double valor;
}
