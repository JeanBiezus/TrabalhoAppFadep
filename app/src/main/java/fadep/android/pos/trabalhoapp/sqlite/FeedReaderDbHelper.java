package fadep.android.pos.trabalhoapp.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import fadep.android.pos.trabalhoapp.ProdutoImagem;

/**
 * Created by Jean on 17/03/2018.
 */

public class FeedReaderDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ProdutoReader.db";

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FeedContract.Produto.CREATE_TABLE);
        db.execSQL(FeedContract.ProdutoImagem.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    public void create(FeedProduto feed, Observer callback){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FeedContract.Produto.COLUMN_NAME_NOME, feed.nome);
        values.put(FeedContract.Produto.COLUMN_NAME_DESCRICAO, feed.descricao);
        values.put(FeedContract.Produto.COLUMN_NAME_VALOR, feed.valor);
        long newRowId = db.insert(FeedContract.Produto.TABLE_NAME, null, values);
        callback.update(null, (int)newRowId);
        db.close();
    }

    public void create(FeedProdutoImagem feed){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FeedContract.ProdutoImagem.COLUMN_NAME_ID_PRODUTO, feed.idproduto);
        values.put(FeedContract.ProdutoImagem.COLUMN_NAME_IMAGEM, feed.imagem);

        long newRowId = db.insert(FeedContract.ProdutoImagem.TABLE_NAME, null, values);
        db.close();
    }

    public List<FeedProduto> read() {
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                FeedContract._ID,
                FeedContract.Produto.COLUMN_NAME_NOME,
                FeedContract.Produto.COLUMN_NAME_DESCRICAO,
                FeedContract.Produto.COLUMN_NAME_VALOR,
                FeedContract.ProdutoImagem.COLUMN_NAME_IMAGEM
        };

        String sortOrder =
                FeedContract._ID + " desc";

        Cursor cursor = db.rawQuery("SELECT * FROM produto p INNER JOIN produtoimagem pi ON p._id = pi.idproduto", null);
        List<FeedProduto> feedProdutos = new ArrayList<>();
        while (cursor.moveToNext()) {
            FeedProduto feedProduto = new FeedProduto();
            feedProduto._id = cursor.getInt(cursor.getColumnIndex((FeedContract._ID)));
            feedProduto.nome = cursor.getString(cursor.getColumnIndex((FeedContract.Produto.COLUMN_NAME_NOME)));
            feedProduto.descricao = cursor.getString(cursor.getColumnIndex((FeedContract.Produto.COLUMN_NAME_DESCRICAO)));
            feedProduto.valor = Double.parseDouble(cursor.getString(cursor.getColumnIndex((FeedContract.Produto.COLUMN_NAME_VALOR))));

            ProdutoImagem produtoImagem = new ProdutoImagem(cursor.getString(cursor.getColumnIndex((FeedContract.ProdutoImagem.COLUMN_NAME_IMAGEM))), feedProduto._id);

            feedProduto.imagens.add(produtoImagem);

            feedProdutos.add(feedProduto);

        }
        cursor.close();
        db.close();
        return feedProdutos;
    }

    public int update(FeedProduto feedProduto) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FeedContract.Produto.COLUMN_NAME_NOME, feedProduto.nome);
        values.put(FeedContract.Produto.COLUMN_NAME_DESCRICAO, feedProduto.descricao);
        values.put(FeedContract.Produto.COLUMN_NAME_VALOR, feedProduto.valor);

        String selection = FeedContract._ID + " = ?";
        String[] selectionArgs = {"" + feedProduto._id};

        int count = db.update(
                    FeedContract.Produto.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        db.close();
        return count;
    }

    public void delete(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String selection = FeedContract._ID + " = ?";

        String[] selectionArgs = {"" + id};

        int deletedRows = db.delete(FeedContract.Produto.TABLE_NAME, selection, selectionArgs);

        db.close();
    }
}
