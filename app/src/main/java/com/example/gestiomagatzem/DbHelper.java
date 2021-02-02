package com.example.gestiomagatzem;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Articles";
    public static final String COL_ID = "_id";
    public static final String COL_CODI = "codiArticle";
    public static final String COL_DESCRIPCIO = "descripcio";
    public static final String COL_FAMILIA = "familia";
    public static final String COL_PREU = "preu";
    public static final String COL_ESTOC = "estoc";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DATABASE_NAME + " (" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COL_CODI + " TEXT," +
            COL_DESCRIPCIO + " TEXT," +
            COL_FAMILIA + " TEXT," +
            COL_PREU + " REAL," +
            COL_ESTOC + " REAL)";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
