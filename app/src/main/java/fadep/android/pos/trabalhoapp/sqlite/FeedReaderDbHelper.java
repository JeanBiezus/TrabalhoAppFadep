package fadep.android.pos.trabalhoapp.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

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
        db.execSQL(FeedContract.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    public void create(Feed feed){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FeedContract.COLUMN_NAME_NOME, feed.nome);
        values.put(FeedContract.COLUMN_NAME_DESCRICAO, feed.descricao);
        values.put(FeedContract.COLUMN_NAME_VALOR, feed.valor);
        values.put(FeedContract.COLUMN_NAME_IMAGEM, feed.imagem1);

        long newRowId = db.insert(FeedContract.TABLE_NAME, null, values);
        db.close();
    }

    public List<Feed> read() {
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                FeedContract._ID,
                FeedContract.COLUMN_NAME_NOME,
                FeedContract.COLUMN_NAME_DESCRICAO,
                FeedContract.COLUMN_NAME_VALOR,
                FeedContract.COLUMN_NAME_IMAGEM,
        };

        String sortOrder =
                FeedContract._ID + " desc";

        Cursor cursor = db.query(
                FeedContract.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );
        List<Feed> feeds = new ArrayList<>();
        while (cursor.moveToNext()) {
            Feed feed = new Feed();
            feed._id = cursor.getInt(cursor.getColumnIndex((FeedContract._ID)));
            feed.nome = cursor.getString(cursor.getColumnIndex((FeedContract.COLUMN_NAME_NOME)));
            feed.descricao = cursor.getString(cursor.getColumnIndex((FeedContract.COLUMN_NAME_DESCRICAO)));
            feed.valor = Double.parseDouble(cursor.getString(cursor.getColumnIndex((FeedContract.COLUMN_NAME_VALOR))));
            feed.imagem1 = cursor.getString(cursor.getColumnIndex((FeedContract.COLUMN_NAME_IMAGEM)));
            feeds.add(feed);

        }
        cursor.close();
        db.close();
        return feeds;
    }

    public int update(Feed feed) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FeedContract.COLUMN_NAME_NOME, feed.nome);
        values.put(FeedContract.COLUMN_NAME_DESCRICAO, feed.descricao);
        values.put(FeedContract.COLUMN_NAME_VALOR, feed.valor);
        values.put(FeedContract.COLUMN_NAME_IMAGEM, feed.imagem1);

        String selection = FeedContract._ID + " = ?";
        String[] selectionArgs = {"" + feed._id};

        int count = db.update(
                    FeedContract.TABLE_NAME,
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

        int deletedRows = db.delete(FeedContract.TABLE_NAME, selection, selectionArgs);

        db.close();
    }
}
