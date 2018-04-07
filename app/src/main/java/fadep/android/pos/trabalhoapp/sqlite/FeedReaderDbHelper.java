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

        Cursor cursor = db.rawQuery("SELECT p.* FROM produto p", null);
        List<FeedProduto> feedProdutos = new ArrayList<>();
        while (cursor.moveToNext()) {

            FeedProduto feedProduto = new FeedProduto();
            feedProduto._id = cursor.getInt(cursor.getColumnIndex((FeedContract._ID)));
            feedProduto.nome = cursor.getString(cursor.getColumnIndex((FeedContract.Produto.COLUMN_NAME_NOME)));
            feedProduto.descricao = cursor.getString(cursor.getColumnIndex((FeedContract.Produto.COLUMN_NAME_DESCRICAO)));
            feedProduto.valor = Double.parseDouble(cursor.getString(cursor.getColumnIndex((FeedContract.Produto.COLUMN_NAME_VALOR))));

            String sqlImagem = "SELECT * FROM produtoimagem pi WHERE pi.idproduto = " + feedProduto._id + " ORDER BY _id DESC LIMIT 1";
            Cursor cursorImagem = db.rawQuery(sqlImagem, null);
            cursorImagem.moveToFirst();
            ProdutoImagem produtoImagem = new ProdutoImagem(cursorImagem.getString(cursorImagem.getColumnIndex((FeedContract.ProdutoImagem.COLUMN_NAME_IMAGEM))), feedProduto._id);

            feedProduto.imagens.add(produtoImagem);
            feedProdutos.add(feedProduto);

        }
        cursor.close();
        db.close();
        return feedProdutos;
    }

    public int update(FeedProduto feedProduto, Observer callback) {
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
        callback.update(null, count);
        return count;
    }

    public void delete(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String selection = FeedContract._ID + " = ?";

        String[] selectionArgs = {"" + id};

        int deletedRows = db.delete(FeedContract.Produto.TABLE_NAME, selection, selectionArgs);

        db.close();
    }

    public void deletarImagens(int idProduto) {
        SQLiteDatabase db = getWritableDatabase();
        String selection = FeedContract.ProdutoImagem.COLUMN_NAME_ID_PRODUTO + " = ?";

        String[] selectionArgs = {"" + idProduto};

        int deletedRows = db.delete(FeedContract.ProdutoImagem.TABLE_NAME, selection, selectionArgs);

        db.close();
    }

    public FeedProduto findById(long id, Observer callback){
        SQLiteDatabase db = getReadableDatabase();

        String produtoSql = "SELECT * FROM produto WHERE _id = " + id;
        Cursor cursor = db.rawQuery(produtoSql, null);

        FeedProduto feedProduto = new FeedProduto();
        cursor.moveToFirst();
        feedProduto._id = cursor.getInt(cursor.getColumnIndex((FeedContract._ID)));
        feedProduto.nome = cursor.getString(cursor.getColumnIndex((FeedContract.Produto.COLUMN_NAME_NOME)));
        feedProduto.descricao = cursor.getString(cursor.getColumnIndex((FeedContract.Produto.COLUMN_NAME_DESCRICAO)));
        feedProduto.valor = Double.parseDouble(cursor.getString(cursor.getColumnIndex((FeedContract.Produto.COLUMN_NAME_VALOR))));

        String imagensSql = "SELECT * FROM " + FeedContract.ProdutoImagem.TABLE_NAME +
                                " pi WHERE pi.idproduto = " + feedProduto._id;

        Cursor cursorImagens = db.rawQuery(imagensSql, null);
        while (cursorImagens.moveToNext()) {

            ProdutoImagem produtoImagem = new ProdutoImagem(cursorImagens.getString(cursorImagens.getColumnIndex((FeedContract.ProdutoImagem.COLUMN_NAME_IMAGEM))), feedProduto._id);

            feedProduto.imagens.add(produtoImagem);

        }
        cursor.close();
        cursorImagens.close();
        db.close();
        return feedProduto;
    }
}
