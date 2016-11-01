package br.com.egcservices.portaldamoda.telas.lojas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import br.com.egcservices.portaldamoda.R;
import br.com.egcservices.portaldamoda.classes.CategoriaLoja;
import br.com.egcservices.portaldamoda.classes.Empresa;
import br.com.egcservices.portaldamoda.utils.listeners.ClickLojaListener;

public class ListaLojasActivity extends ActionBarActivity implements ClickLojaListener {

    private static String cidadeId, tipoEmpresa, descEmpresa, mCatId;
    //private static CategoriaLoja mCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_lojas);
        validarIntent();
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new ListaLojasFragment()).commit();
        //mCat, cidadeId, tipoEmpresa, descEmpresa)).commit();
    }

    public void validarIntent() {
        Intent it = this.getIntent();
        if (it.hasExtra("cidade") && it.hasExtra("tipoempresa")
                && it.hasExtra("descempresa") && it.hasExtra("categoria")) {
            cidadeId = it.getStringExtra("cidade");
            tipoEmpresa = it.getStringExtra("tipoempresa");
            descEmpresa = it.getStringExtra("descempresa");
            mCatId = it.getStringExtra("categoria");
            //mCat = (CategoriaLoja) it.getSerializableExtra("categoria");
        }
    }

    @Override
    public void catLojaClick(CategoriaLoja cat) {
        //null
    }

    @Override
    public void lojaClick(Empresa empresa) {
        validarIntent();
        Intent it2 = new Intent(this, DetailLojaActivity.class);
        it2.putExtra("empresa", empresa);
        startActivity(it2);
    }
}