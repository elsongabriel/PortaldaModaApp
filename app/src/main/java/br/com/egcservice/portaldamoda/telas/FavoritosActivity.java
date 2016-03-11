package br.com.egcservice.portaldamoda.telas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import br.com.egcservice.portaldamoda.R;
import br.com.egcservice.portaldamoda.classes.Empresa;
import br.com.egcservice.portaldamoda.utils.ClickFavListener;

public class FavoritosActivity extends ActionBarActivity implements ClickFavListener {

//    private static String cidadeId;

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