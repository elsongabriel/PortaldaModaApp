package br.com.egcservices.portaldamoda.utils.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PersistenceHelper extends SQLiteOpenHelper {
    private static String NOME_BANCO = "PortalDaModaDB";
    private static int VERSAO = 1;

    public PersistenceHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS " + EmpresaTB.TABLE_NAME + " (" +
                        EmpresaTB.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        EmpresaTB.COL_EMP_ID + " INTEGER NOT NULL UNIQUE, " +
                        EmpresaTB.COL_CID_ID + " INTEGER NOT NULL)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
