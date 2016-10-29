package br.com.egcservices.portaldamoda;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

public class CidadeActivity extends ActionBarActivity {

    Button mBtnCaruaru, mBtnSanta, mBtnToritama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cidade);

        mBtnCaruaru = (Button) findViewById(R.id.btnCaruaru);
        mBtnSanta = (Button) findViewById(R.id.btnSantaC);
        mBtnToritama = (Button) findViewById(R.id.btnToritama);

        mBtnCaruaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirTela("1", getString(R.string.str_caruaru));
            }
        });

        mBtnSanta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirTela("2", getString(R.string.str_santac));
            }
        });

        mBtnToritama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirTela("3", getString(R.string.str_toritama));
            }
        });
    }

    public void abrirTela(String value, String value2) {
        Intent it = new Intent(CidadeActivity.this, HomeActivity.class);
        it.putExtra("cidadeId", value);
        it.putExtra("cidade", value2);
        startActivity(it);
    }
}