package br.com.egcservices.portaldamoda.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PersistenceHelper extends SQLiteOpenHelper {
    private static String NOME_BANCO = "AgresteTemModasDB";
    private static int VERSAO = 1;

    public PersistenceHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS TabelaEmpresasFavoritos(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "EmpId INTEGER NOT NULL UNIQUE, CidadeId INTEGER NOT NULL)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
