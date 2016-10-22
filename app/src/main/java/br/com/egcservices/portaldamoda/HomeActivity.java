package br.com.egcservices.portaldamoda;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import br.com.egcservices.portaldamoda.telas.ListaEmpresasActivity;

public class HomeActivity extends ActionBarActivity {

    Button mBtnFav, mBtnAliment, mBtnExcurs, mBtnHotel, mBtnLoja;
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
        mBtnFav = (Button) findViewById(R.id.btnFavoritos);
        mBtnAliment = (Button) findViewById(R.id.btnAliment);
        mBtnExcurs = (Button) findViewById(R.id.btnExcursao);
        mBtnHotel = (Button) findViewById(R.id.btnHotel);
        mBtnLoja = (Button) findViewById(R.id.btnLoja);

        mBtnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirTela(cidadeId, "0", "Favoritos");
            }
        });

        mBtnAliment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirTela(cidadeId, "1", "Alimentação");
            }
        });

        mBtnExcurs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirTela(cidadeId, "2", "Excursões");
            }
        });

        mBtnHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirTela(cidadeId, "3", "Hoteis");
            }
        });

        mBtnLoja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirTela(cidadeId, "4", "Lojas");
            }
        });
    }

    public void abrirTela(String value, String value2, String value3) {
        Intent it = new Intent(HomeActivity.this, ListaEmpresasActivity.class);
        it.putExtra("cidade", value);
        it.putExtra("tipoempresa", value2);
        it.putExtra("descempresa", value3);
        startActivity(it);
    }
}