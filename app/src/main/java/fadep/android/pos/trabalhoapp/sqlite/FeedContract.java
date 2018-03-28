package fadep.android.pos.trabalhoapp.sqlite;

import android.provider.BaseColumns;

/**
 * Created by Jean on 17/03/2018.
 */

public class FeedContract implements BaseColumns{

    public static final String TABLE_NAME = "entry";
    public static final String COLUMN_NAME_NOME= "Nome";
    public static final String COLUMN_NAME_VALOR = "Valor";
    public static final String COLUMN_NAME_DESCRICAO = "Descrição";
    public static final String COLUMN_NAME_IMAGEM = "Imagem";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_NOME + " TEXT," +
                    COLUMN_NAME_DESCRICAO + " TEXT," +
                    COLUMN_NAME_IMAGEM + " TEXT," +
                    COLUMN_NAME_VALOR + " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
}
