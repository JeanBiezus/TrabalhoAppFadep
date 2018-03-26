package fadep.android.pos.trabalhoapp.Rom;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by Jean on 19/03/2018.
 */

@Database(entities = {Produto.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProdutoDAO produtoDAO();
    private static AppDatabase INSTANCE ;

    public static AppDatabase getAppDatabase (Context contexto) {
        if ( INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder (contexto.getApplicationContext (), AppDatabase.class, "user-database")
                            // permite consultas no tópico principal.
                            // Não faça isso em um aplicativo real! Veja PersistenceBasicSample para um exemplo.
                            .allowMainThreadQueries ()
                            .build();
        }
        return INSTANCE ;
    }

    public static void destroyInstance () {
        INSTANCE = null;
    }
}

