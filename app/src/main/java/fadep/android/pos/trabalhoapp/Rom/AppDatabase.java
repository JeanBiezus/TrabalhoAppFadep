package fadep.android.pos.trabalhoapp.Rom;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by Jean on 19/03/2018.
 */

@Database(entities = {Produto.class, ProdutoImagem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProdutoDAO produtoDAO();
    public abstract ProdutoImagemDAO produtoImagemDAO();
}

