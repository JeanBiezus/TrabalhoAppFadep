package fadep.android.pos.trabalhoapp.sqlite;

import android.provider.BaseColumns;

/**
 * Created by Jean on 17/03/2018.
 */

public class FeedContract implements BaseColumns{

    public static final class Produto {
        static final String TABLE_NAME = "produto";
        static final String COLUMN_NAME_NOME = "nome";
        static final String COLUMN_NAME_VALOR = "valor";
        static final String COLUMN_NAME_DESCRICAO = "descricao";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY," +
                        COLUMN_NAME_NOME + " TEXT," +
                        COLUMN_NAME_DESCRICAO + " TEXT," +
                        COLUMN_NAME_VALOR + " DOUBLE)";

        public static final String DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static final class ProdutoImagem {
        static final String TABLE_NAME = "produtoimagem";
        static final String COLUMN_NAME_IMAGEM = "imagem";
        static final String COLUMN_NAME_ID_PUBLICACAO = "idpublicacao";

        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY," +
                        COLUMN_NAME_IMAGEM + " TEXT," +
                        COLUMN_NAME_ID_PUBLICACAO + " INTEGER)";

        public static final String DROP_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
