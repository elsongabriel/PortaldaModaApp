package br.com.egcservice.portaldamoda.telas.loja;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import br.com.egcservice.portaldamoda.R;
import br.com.egcservice.portaldamoda.telas.DetailEmpresaFragment;

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
