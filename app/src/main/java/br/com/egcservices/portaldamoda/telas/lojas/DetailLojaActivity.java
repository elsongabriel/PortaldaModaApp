package br.com.egcservices.portaldamoda.telas.lojas;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import br.com.egcservices.portaldamoda.R;
import br.com.egcservices.portaldamoda.telas.DetailEmpresaFragment;

/**
 * Created by Elson on 21/02/2016.
 */
public class DetailLojaActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_loja);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new DetailEmpresaFragment()).commit();
    }
}
