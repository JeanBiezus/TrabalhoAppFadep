package fadep.android.pos.trabalhoapp.Rom;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Jean on 19/03/2018.
 */
@Entity(tableName = "produtoimagem")
public class ProdutoImagem {
    @PrimaryKey(autoGenerate = true)
    public int _id;
    public String imagem;
    public int idproduto;
}
