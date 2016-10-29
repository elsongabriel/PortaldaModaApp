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

import java.util.ArrayList;
import java.util.List;

import br.com.egcservices.portaldamoda.R;
import br.com.egcservices.portaldamoda.classes.CategoriaLoja;
import br.com.egcservices.portaldamoda.utils.listeners.ClickLojaListener;
import br.com.egcservices.portaldamoda.utils.ConexaoHttp;

public class ListaCatLojasFragment extends ListFragment {

    TextView lblTipoEmp;
    ConexaoHttp conexaoHttp = new ConexaoHttp();

    private static String cidadeId, tipoEmpresa, descEmpresa, categoriaId;
    private static List<CategoriaLoja> mListaCats = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_catlojas, container, false);

//        actCategorias = (AutoCompleteTextView) view.findViewById(R.id.actCategorias);
        lblTipoEmp = (TextView) view.findViewById(R.id.lblTipoEmpCatLoja);
        lblTipoEmp.setText(descEmpresa);
//        actCategorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @SuppressLint("LongLogTag")
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String nomeCat = parent.getItemAtPosition(position).toString();
//                if (getActivity() instanceof ClickLojaListener) {
//                    ((ClickLojaListener) getActivity())
//                            .catLojaClick(validarClick(true, nomeCat, mListaCats));
//                }
//            }
//        });
        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (getActivity() instanceof ClickLojaListener) {
            ((ClickLojaListener) getActivity()).catLojaClick(mListaCats.get(position));
        }
    }

    public void iniciarDownload() {
        if (conexaoHttp.temConexao(getActivity()))
            new ListarCategorias().execute();
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
//        //atualizar autocompletetextview
        for (int i = 0; i < mListaCats.size(); i++) {
            emps.add(mListaCats.get(i).getCategoria_desc());
        }
        setListAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, emps));
//        actCategorias.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, emps));
    }

    public CategoriaLoja validarClick(boolean flag, String cat, List<CategoriaLoja> lista) {
        CategoriaLoja mCatResult;
        if (flag) {
            for (int i = 0; i < lista.size(); i++) {
                mCatResult = lista.get(i);
                if (mCatResult.getCategoria_desc().equals(cat)) {
                    return mCatResult;
                }
            }
        }
        return null;
    }
}