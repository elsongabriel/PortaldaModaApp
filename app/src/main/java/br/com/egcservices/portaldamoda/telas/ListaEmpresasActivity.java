package br.com.egcservices.portaldamoda.telas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import br.com.egcservices.portaldamoda.R;
import br.com.egcservices.portaldamoda.classes.Empresa;
import br.com.egcservices.portaldamoda.classes.Excursao;
import br.com.egcservices.portaldamoda.telas.excursao.DetailExcursaoActivity;
import br.com.egcservices.portaldamoda.telas.excursao.ListaExcursoesFragment;
import br.com.egcservices.portaldamoda.telas.loja.ListaCategoriasActivity;
import br.com.egcservices.portaldamoda.utils.ClickListener;

public class ListaEmpresasActivity extends ActionBarActivity implements ClickListener {

    private static String cidadeId, tipoEmpresa, descEmpresa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_empresas);
        validarIntent();
        if (tipoEmpresa.equals("0")) {
            Intent it = new Intent(this, br.com.egcservices.portaldamoda.telas.FavoritosActivity.class);
            it.putExtra("cidade", cidadeId);
            startActivity(it);//favoritos
        }
        if (tipoEmpresa.equals("1"))
            getSupportFragmentManager().beginTransaction()
                    .replace(android.R.id.content, new br.com.egcservices.portaldamoda.telas.ListaAlimHotFragment()).commit();//alimentação
        else if (tipoEmpresa.equals("2"))
            getSupportFragmentManager().beginTransaction()
                    .replace(android.R.id.content, new ListaExcursoesFragment()).commit();//excursão
        else if (tipoEmpresa.equals("3"))
            getSupportFragmentManager().beginTransaction()
                    .replace(android.R.id.content, new br.com.egcservices.portaldamoda.telas.ListaAlimHotFragment()).commit();//hotel
        else if (tipoEmpresa.equals("4")) {
            Intent it = new Intent(this, ListaCategoriasActivity.class);
            it.putExtra("cidade", cidadeId);
            it.putExtra("tipoempresa", tipoEmpresa);
            it.putExtra("descempresa", descEmpresa);
            startActivity(it);//loja
        } else
            finish();
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
    public void empresaClick(Empresa empresa) {
        validarIntent();
        Intent it2 = new Intent(this, br.com.egcservices.portaldamoda.telas.DetailEmpresaActivity.class);
        it2.putExtra("empresa", empresa);
        startActivity(it2);
    }

    @Override
    public void excursaoClick(Excursao excursao) {
        validarIntent();
        Intent it2 = new Intent(this, DetailExcursaoActivity.class);
        it2.putExtra("excursao", excursao);
        startActivity(it2);
    }
}