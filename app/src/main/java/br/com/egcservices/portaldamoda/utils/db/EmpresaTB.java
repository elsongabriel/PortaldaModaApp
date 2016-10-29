package br.com.egcservices.portaldamoda.utils.db;

import android.provider.BaseColumns;

public interface EmpresaTB extends BaseColumns {
    // Nome da tabela no banco de dados
    String TABLE_NAME = "EmpresasFavorito";

    // Colunas do banco de dados
    String COL_ID = "ID";
    String COL_EMP_ID = "EmpId";
    String COL_CID_ID = "CidId";
}
