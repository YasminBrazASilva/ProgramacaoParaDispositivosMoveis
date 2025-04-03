package com.example.biblioteca;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

public class BancoHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "meubanco.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "livros";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITULO = "titulo";
    private static final String COLUMN_AUTOR = "autor";
    private static final String COLUMN_ECONCLUIDO = "eConcluido";
    private static final String COLUMN_CATEGORIA = "categoria";

    public BancoHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITULO + " TEXT, "
                + COLUMN_AUTOR + " TEXT, "
                + COLUMN_ECONCLUIDO + " TEXT, "
                + COLUMN_CATEGORIA + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long inserirLivro(String titulo, String autor, String eConcluido, String categoria)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITULO, titulo);
        values.put(COLUMN_AUTOR, autor);
        values.put(COLUMN_ECONCLUIDO, eConcluido);
        values.put(COLUMN_CATEGORIA, categoria);

        return db.insert(TABLE_NAME, null, values);
    }

    public Cursor listarLivros()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public int atualizarLivro(int id, String titulo, String autor, String eConcluido, String categoria)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITULO, titulo);
        values.put(COLUMN_AUTOR, autor);
        values.put(COLUMN_ECONCLUIDO, eConcluido);
        values.put(COLUMN_CATEGORIA, categoria);

        return db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    public int excluirLivro(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }








}
