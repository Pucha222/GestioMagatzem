package com.example.gestiomagatzem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ArticlesDataSource {

    private DbHelper dbHelper;
    private SQLiteDatabase dbW, dbR;

    public ArticlesDataSource(Context context) {
        dbHelper = new DbHelper(context);
        dbW = dbHelper.getWritableDatabase();
        dbR = dbHelper.getReadableDatabase();
    }

    public Cursor getArticles() {
        String query = "SELECT * FROM Articles";
        return dbR.rawQuery(query, null);
    }

    public long inserirArticle(String codiArticle, String descripcio, String familia, float estoc, float preu) {
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.COL_CODI, codiArticle);
        cv.put(dbHelper.COL_DESCRIPCIO, descripcio);
        cv.put(dbHelper.COL_FAMILIA, familia);
        cv.put(dbHelper.COL_ESTOC, estoc);
        cv.put(dbHelper.COL_PREU, preu);

        return dbW.insert("Articles", null, cv);

    }

    public Cursor getArticleById(long id) {
        String query = "SELECT * FROM Articles WHERE _id = ?";
        String[] args = new String[] {String.valueOf(id)};
        return dbR.rawQuery(query, args);
    }

    public void modificarArticle(String codiArticle, String descripcio, String familia, float estoc, float preu) {
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.COL_DESCRIPCIO, descripcio);
        cv.put(dbHelper.COL_FAMILIA, familia);
        cv.put(dbHelper.COL_ESTOC, estoc);
        cv.put(dbHelper.COL_PREU, preu);

        String[] args = new String[] {String.valueOf(codiArticle)};
        dbW.update("Articles", cv, dbHelper.COL_CODI + " = ?", args);
    }

    public void esborrarArticle(String codi) {
        String[] args = new String[] {String.valueOf(codi)};
        dbW.delete("Articles", dbHelper.COL_CODI + " = ?", args);
    }

    public Boolean checkCodi(String codi) {
        String query = "SELECT * FROM Articles WHERE codiArticle = ?";
        String[] args = new String[] {codi};
        Cursor c = dbR.rawQuery(query, args);
        return c.moveToFirst();
    }

    public Cursor buscarArticles(String search) {
        String query = "SELECT * FROM Articles WHERE descripcio LIKE ?";
        String[] args = new String[] {String.valueOf(search)};
        return dbR.rawQuery(query, args);
    }

}
