package br.com.egcservices.portaldamoda.telas.favoritos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import br.com.egcservices.portaldamoda.R;
import br.com.egcservices.portaldamoda.classes.Empresa;
import br.com.egcservices.portaldamoda.telas.DetailEmpresaActivity;
import br.com.egcservices.portaldamoda.utils.listeners.ClickFavListener;

public class FavoritosActivity extends ActionBarActivity implements ClickFavListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new FavoritosFragment()).commit();
    }

    @Override
    public void clickFav(Empresa empresa, boolean favorito) {
        Intent it2 = new Intent(this, DetailEmpresaActivity.class);
        it2.putExtra("empresa", empresa);
        startActivity(it2);
    }
}