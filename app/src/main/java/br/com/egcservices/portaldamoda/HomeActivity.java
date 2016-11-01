package br.com.egcservices.portaldamoda;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

import br.com.egcservices.portaldamoda.telas.ListaEmpresasActivity;

public class HomeActivity extends ActionBarActivity {

    TextView mLblCidade;
    private static String cidade, cidadeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent it = getIntent();
        if (it.hasExtra("cidadeId") && it.hasExtra("cidade")) {
            cidadeId = it.getStringExtra("cidadeId");
            cidade = it.getStringExtra("cidade");
        }
        mLblCidade = (TextView) findViewById(R.id.lblCidadeInfo);
        mLblCidade.setText(cidade);
    }

    public void favoritos(View v) {
        abrirTela(cidadeId, "0", getString(R.string.str_favoritos));
    }

    public void alimentacao(View v) {
        abrirTela(cidadeId, "1", getString(R.string.str_alimentacao));
    }

    public void excursao(View v) {
        abrirTela(cidadeId, "2", getString(R.string.str_excursoes));
    }

    public void hotel(View v) {
        abrirTela(cidadeId, "3", getString(R.string.str_hoteis));
    }

    public void loja(View v) {
        abrirTela(cidadeId, "4", getString(R.string.str_lojas));
    }

    public void abrirTela(String value, String value2, String value3) {
        Intent it = new Intent(HomeActivity.this, ListaEmpresasActivity.class);
        it.putExtra("cidade", value);
        it.putExtra("tipoempresa", value2);
        it.putExtra("descempresa", value3);
        startActivity(it);
    }
}