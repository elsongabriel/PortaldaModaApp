package br.com.egcservices.portaldamoda.telas.lojas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.egcservices.portaldamoda.R;
import br.com.egcservices.portaldamoda.adapters.EmpresasAdapter;
import br.com.egcservices.portaldamoda.classes.Empresa;
import br.com.egcservices.portaldamoda.utils.ConexaoHttp;
import br.com.egcservices.portaldamoda.utils.Mensagem;
import br.com.egcservices.portaldamoda.utils.listeners.ClickLojaListener;

public class ListaLojasFragment extends Fragment implements SearchView.OnQueryTextListener {

    private static final String EXTRA_LOJA = "lojas";
    private static final String EXTRA_QUERY_LOJA = "lojasQuery";
    TextView lblTipoEmp;
    ConexaoHttp conexaoHttp = new ConexaoHttp();
    ProgressDialog pDialog;
    MenuItem mMenuItemSearch;
    SearchView mSearchView;
    ListView listView;
    int queryId = 0;

    private static String cidadeId, tipoEmpresa, descEmpresa, mCatId;
    private static List<Empresa> mListaLojas = new ArrayList<>();
    private static List<Empresa> mListaQuery = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_lojas, container, false);
        listView = (ListView) view.findViewById(R.id.lojas_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (getActivity() instanceof ClickLojaListener) {
                    if (queryId == 0)
                        ((ClickLojaListener) getActivity()).lojaClick(mListaLojas.get(position));
                    else
                        ((ClickLojaListener) getActivity()).lojaClick(mListaQuery.get(position));
                }
            }
        });

        if (savedInstanceState != null) {
            if (queryId == 0)
                mListaLojas = (List<Empresa>) savedInstanceState.getSerializable(EXTRA_LOJA);
            else
                mListaQuery = (List<Empresa>) savedInstanceState.getSerializable(EXTRA_QUERY_LOJA);
        } else {
            Intent it = getActivity().getIntent();
            if (it.hasExtra("cidade") && it.hasExtra("tipoempresa")
                    && it.hasExtra("descempresa") && it.hasExtra("categoria")) {
                cidadeId = it.getStringExtra("cidade");
                tipoEmpresa = it.getStringExtra("tipoempresa");
                descEmpresa = it.getStringExtra("descempresa");
                mCatId = it.getStringExtra("categoria");
            }
            iniciarDownload(mCatId.toString(), cidadeId);
        }

        lblTipoEmp = (TextView) view.findViewById(R.id.lblTipoEmpLoja);
        lblTipoEmp.setText(descEmpresa);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (queryId == 0)
            outState.putSerializable(EXTRA_LOJA, (Serializable) mListaLojas);
        else
            outState.putSerializable(EXTRA_QUERY_LOJA, (Serializable) mListaQuery);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);

        mMenuItemSearch = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(mMenuItemSearch);
        mSearchView.setOnQueryTextListener(this);
    }

    // ---- OnQueryTextListener
    @Override
    public boolean onQueryTextSubmit(String query) {
        mListaQuery = new ArrayList<>();
        if (query.trim() != "") {
            for (int i = 0; i < mListaLojas.size(); i++) {
                Empresa emp = mListaLojas.get(i);
                if (emp.getNome_empresa().toLowerCase().contains(query.toLowerCase())) {
                    mListaQuery.add(emp);
                }
            }

            if (mListaQuery.size() > 0) {
                queryId = 1;
                refreshList(mListaQuery);
            } else {
                queryId = 0;
                Mensagem.exibir(getActivity(), getString(R.string.str_naoencontrado));
            }
        } else {
            refreshList(mListaLojas);
            mSearchView.clearFocus();
            return false;
        }

        mSearchView.clearFocus();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        if (query.trim().equals("")) {
            refreshList(mListaLojas);
            queryId = 0;
            return false;
        }
        return true;
    }

    public void iniciarDownload(String catid, String cid) {
        if (conexaoHttp.temConexao(getActivity()))
            new ListarLojasPorCatTipoTask().execute(catid, cid);
    }

    class ListarLojasPorCatTipoTask extends AsyncTask<String, Void, List<Empresa>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage(getString(R.string.str_carregando));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected List<Empresa> doInBackground(String... params) {
            try {
                mListaLojas = conexaoHttp.listarLojasPorCatCidTip(params[0], params[1]);
                return mListaLojas;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Empresa> result) {
            super.onPostExecute(result);
            if (result != null) {
                refreshList(mListaLojas);
            }
            pDialog.dismiss();
        }
    }

    public void refreshList(List<Empresa> lista) {
        listView.setAdapter(new EmpresasAdapter(getActivity(), lista));
    }
}