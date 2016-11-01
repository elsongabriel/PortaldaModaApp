package br.com.egcservices.portaldamoda.telas.lojas.categorias;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.egcservices.portaldamoda.R;
import br.com.egcservices.portaldamoda.classes.CategoriaLoja;
import br.com.egcservices.portaldamoda.utils.ConexaoHttp;
import br.com.egcservices.portaldamoda.utils.listeners.ClickLojaListener;

public class ListaCatLojasFragment extends ListFragment {

    private static final String EXTRA_CAT = "categorias";
    TextView lblTipoEmp;
    ConexaoHttp conexaoHttp = new ConexaoHttp();

    private static String cidadeId, tipoEmpresa, descEmpresa, categoriaId;
    private static List<CategoriaLoja> mListaCats = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lista_catlojas, container, false);

        if (savedInstanceState != null) {
            mListaCats = (List<CategoriaLoja>) savedInstanceState.getSerializable(EXTRA_CAT);
        } else {
            Intent it = getActivity().getIntent();
            if (it.hasExtra("cidade") && it.hasExtra("tipoempresa")
                    && it.hasExtra("descempresa") && it.hasExtra("categoria")) {
                cidadeId = it.getStringExtra("cidade");
                tipoEmpresa = it.getStringExtra("tipoempresa");
                descEmpresa = it.getStringExtra("descempresa");
                categoriaId = it.getStringExtra("categoria");
            }
            iniciarDownload();
        }
        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(EXTRA_CAT, (Serializable) mListaCats);
    }

    public void iniciarDownload() {
        if (conexaoHttp.temConexao(getActivity()))
            new ListarCategorias().execute();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (getActivity() instanceof ClickLojaListener) {
            ((ClickLojaListener) getActivity()).catLojaClick(mListaCats.get(position));
        }
    }

    class ListarCategorias extends AsyncTask<String, Void, List<CategoriaLoja>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<CategoriaLoja> doInBackground(String... params) {
            try {
                mListaCats = conexaoHttp.listarCategorias();
                return mListaCats;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<CategoriaLoja> result) {
            super.onPostExecute(result);
            if (result != null) {
                refreshList();
            }
        }
    }

    public void refreshList() {
        List<String> emps = new ArrayList<>();
        for (int i = 0; i < mListaCats.size(); i++) {
            emps.add(mListaCats.get(i).getCategoria_desc());
        }
        setListAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, emps));
    }
}