package br.com.egcservices.portaldamoda;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CidadeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cidade);
    }

    public void caruaru(View v) {
        abrirTela("1", getString(R.string.str_caruaru));
    }

    public void santacruz(View v) {
        abrirTela("2", getString(R.string.str_santac));
    }

    public void toritama(View v) {
        abrirTela("3", getString(R.string.str_toritama));
    }

    public void abrirTela(String value, String value2) {
        Intent it = new Intent(CidadeActivity.this, HomeActivity.class);
        it.putExtra("cidadeId", value);
        it.putExtra("cidade", value2);
        startActivity(it);
    }
}