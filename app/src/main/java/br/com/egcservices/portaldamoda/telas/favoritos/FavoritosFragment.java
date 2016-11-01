package br.com.egcservices.portaldamoda.telas.favoritos;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.egcservices.portaldamoda.R;
import br.com.egcservices.portaldamoda.adapters.FavoritoAdapter;
import br.com.egcservices.portaldamoda.classes.Empresa;
import br.com.egcservices.portaldamoda.utils.ConexaoHttp;
import br.com.egcservices.portaldamoda.utils.db.EmpresaDB;
import br.com.egcservices.portaldamoda.utils.listeners.ClickFavListener;

public class FavoritosFragment extends Fragment {

    private static final String EXTRA_FAVS = "favoritos";
    private static final String EXTRA_FAVS_IDS = "favoritos_ids";
    ConexaoHttp conexaoHttp = new ConexaoHttp();
    EmpresaDB empDB;
    ListView listView;

    private static String cidadeId;
    private static List<Empresa> mListaEmpresas;
    private static List<Integer> listaIds;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favoritos, container, false);

        listView = (ListView) view.findViewById(R.id.favoritos_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (getActivity() instanceof ClickFavListener) {
                    empDB = new EmpresaDB(getActivity());
                    Empresa emp = mListaEmpresas.get(position);
                    boolean favorito = empDB.favorito(emp);
                    ((ClickFavListener) getActivity()).clickFav(emp, favorito);
                }
            }
        });

        if (savedInstanceState != null) {
            listaIds = (List<Integer>) savedInstanceState.getSerializable(EXTRA_FAVS_IDS);
            mListaEmpresas = (List<Empresa>) savedInstanceState.getSerializable(EXTRA_FAVS);
        } else {
            listaIds = new ArrayList<>();
            mListaEmpresas = new ArrayList<>();
            Intent it = getActivity().getIntent();
            if (it.hasExtra("cidade"))
                cidadeId = it.getStringExtra("cidade");
            atualizarLista(cidadeId);
        }

        return view;
    }

    void atualizarLista(String cid) {
        EmpresaDB empDB = new EmpresaDB(getActivity());
        listaIds = empDB.listarFavoritoPorCidade(Integer.valueOf(cid));
        if (conexaoHttp.temConexao(getActivity()))
            new ListarFavoritosPorCidTask().execute();
    }

    class ListarFavoritosPorCidTask extends AsyncTask<Void, Void, List<Empresa>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Empresa> doInBackground(Void... params) {
            try {
                mListaEmpresas = conexaoHttp.listarEmpresasFavPorCidade(listaIds, cidadeId);
                return mListaEmpresas;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Empresa> result) {
            super.onPostExecute(result);
            if (result != null) {
                mListaEmpresas = result;
                listView.setAdapter(new FavoritoAdapter(getActivity(), mListaEmpresas));
            }
        }
    }


}
