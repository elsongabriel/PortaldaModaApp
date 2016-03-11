package br.com.egcservice.portaldamoda.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.egcservice.portaldamoda.classes.Empresa;

public class EmpresaDB {

    private PersistenceHelper mHelper;
    private SQLiteDatabase dataBase = null;
    private static EmpresaDB instance;

    public static EmpresaDB getInstance(Context context) {
        if (instance == null)
            instance = new EmpresaDB(context);
        return instance;
    }

    public EmpresaDB(Context context) {
        mHelper = new PersistenceHelper(context);
    }

    //DADOS DA TABELA_EMPRESA
    public static final String TABELA_FAVORITO = "TabelaEmpresasFavoritos";
//    public static final String COLUNA_ID = "Id";
//    public static final String COLUNA_EMP_ID = "EmpId";
//    public static final String COLUNA_EMP_CIDADE = "CidadeId";
//    public static final String COLUNA_EMP_TIPO = "Tipo";
//    public static final String COLUNA_EMP_PLANO = "Plano";
//    public static final String COLUNA_EMP_CATEGORIA = "Categoria";
//    public static final String COLUNA_EMP_NOMEEMP = "NomeEmpresa";
//    public static final String COLUNA_EMP_TELEMP = "TelEmpresa";
//    public static final String COLUNA_EMP_ENDEMP = "EndEmpresa";
//    public static final String COLUNA_EMP_FORMA = "FormaPgto";
//    public static final String COLUNA_EMP_FACE = "Face";
//    public static final String COLUNA_EMP_INSTA = "Insta";
//    public static final String COLUNA_EMP_TWITER = "Twitter";
//    public static final String COLUNA_EMP_SITE = "Site";
//    public static final String COLUNA_EMP_NOMERESP = "NomeResp";
//    public static final String COLUNA_EMP_TELFRESP = "TelResp";

    public void inserirFavorito(Empresa empresa) {
        dataBase = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("EmpId", empresa.getId());
        values.put("CidadeId", empresa.getCidade_empresa_id());
        dataBase.insert(TABELA_FAVORITO, null, values);
        dataBase.close();
    }

    public void excluirFavorito(Empresa empresa) {
        dataBase = mHelper.getWritableDatabase();
        dataBase.delete(TABELA_FAVORITO, "EmpId = ?",
                new String[]{empresa.getId().toString()});
        dataBase.close();
    }

    public List<Integer> listarFavoritoPorCidade(Integer cid) {
        dataBase = mHelper.getReadableDatabase();
        Cursor cursor = dataBase.rawQuery(
                "SELECT EmpId FROM TabelaEmpresasFavoritos WHERE CidadeId = ?",
                new String[]{String.valueOf(cid)}
        );
        List<Integer> empresasIds = new ArrayList<>();
        if (cursor == null)
            return empresasIds;
        else {
            if (cursor.moveToFirst()) {
                do {
                    //int indexID = cursor.getColumnIndex(COLUNA_ID);
                    int indexEmpresaId = cursor.getColumnIndex("EmpId");
                    //int indexCidadeId = cursor.getColumnIndex("CidadeId");
                    //String id = cursor.getString(indexID);
                    Integer empid = Integer.valueOf(cursor.getString(indexEmpresaId));
                    //Integer cidid = Integer.valueOf(cursor.getString(indexCidadeId));
                    // Empresa emp = new Empresa(empid, cidid);
                    empresasIds.add(empid);
                } while (cursor.moveToNext());
            }
            cursor.close();
            dataBase.close();
        }
        return empresasIds;
    }

    public boolean favorito(Empresa empresa) {
        dataBase = mHelper.getReadableDatabase();
        String query = "SELECT * FROM TabelaEmpresasFavoritos WHERE EmpId =" + empresa.getId()
                + " AND CidadeId = " + empresa.getCidade_empresa_id();
        Cursor cursor = dataBase.rawQuery(query, null);
        int count = cursor.getCount();
        cursor.close();
        dataBase.close();
        return count > 0;
    }
}
