package br.com.egcservices.portaldamoda.telas.excursao;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import br.com.egcservices.portaldamoda.R;
import br.com.egcservices.portaldamoda.telas.excursao.DetailExcursaoFragment;

public class DetailExcursaoActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_excursao);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new DetailExcursaoFragment()).commit();
    }
}