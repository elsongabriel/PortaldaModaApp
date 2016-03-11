package br.com.egcservice.portaldamoda.telas.loja;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import br.com.egcservice.portaldamoda.R;
import br.com.egcservice.portaldamoda.classes.CategoriaLoja;
import br.com.egcservice.portaldamoda.classes.Empresa;
import br.com.egcservice.portaldamoda.utils.ClickLojaListener;

public class ListaCategoriasActivity extends ActionBarActivity implements ClickLojaListener {

    private static String cidadeId, tipoEmpresa, descEmpresa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_categorias);
        validarIntent();
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new ListaCatLojasFragment()).commit();
    }

    public void validarIntent() {
        Intent it = this.getIntent();
        if (it.hasExtra("cidade") && it.hasExtra("tipoempresa") && it.hasExtra("descempresa")) {
            cidadeId = it.getStringExtra("cidade");
            tipoEmpresa = it.getStringExtra("tipoempresa");
            descEmpresa = it.getStringExtra("descempresa");
        }
    }

    @Override
    public void catLojaClick(CategoriaLoja cat) {
        validarIntent();
        Intent it2 = new Intent(this, ListaLojasActivity.class);
        it2.putExtra("cidade", cidadeId);
        it2.putExtra("tipoempresa", tipoEmpresa);
        it2.putExtra("descempresa", descEmpresa);
        it2.putExtra("categoria", cat);
        startActivity(it2);
    }

    @Override
    public void lojaClick(Empresa empresa) {
        //null
    }
}