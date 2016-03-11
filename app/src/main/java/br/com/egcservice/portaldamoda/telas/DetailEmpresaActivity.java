package br.com.egcservice.portaldamoda.telas;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import br.com.egcservice.portaldamoda.R;

/**
 * Created by Elson on 21/02/2016.
 */
public class DetailEmpresaActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_empresa);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new DetailEmpresaFragment()).commit();
    }
}
