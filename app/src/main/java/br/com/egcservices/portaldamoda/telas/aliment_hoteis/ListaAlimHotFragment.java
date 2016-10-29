package br.com.egcservices.portaldamoda.telas.aliment_hoteis;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.egcservices.portaldamoda.R;
import br.com.egcservices.portaldamoda.adapters.EmpresasAdapter;
import br.com.egcservices.portaldamoda.classes.Empresa;
import br.com.egcservices.portaldamoda.utils.listeners.ClickListener;
import br.com.egcservices.portaldamoda.utils.ConexaoHttp;
import br.com.egcservices.portaldamoda.utils.Mensagem;

public class ListaAlimHotFragment extends ListFragment implements SearchView.OnQueryTextListener {

    TextView lblTipoEmp;
    ConexaoHttp conexaoHttp = new ConexaoHttp();
    ProgressDialog pDialog;
    MenuItem mMenuItemSearch;
    SearchView mSearchView;
    int queryId = 0;

    private static String cidadeId, tipoEmpresa, descEmpresa;
    private static List<Empresa> mListaEmpresas = new ArrayList<>();
    private static List<Empresa> mListaQuery = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Intent it = getActivity().getIntent();
        if (it.hasExtra("cidade") && it.hasExtra("tipoempresa") && it.hasExtra("descempresa")) {
            cidadeId = it.getStringExtra("cidade");
            tipoEmpresa = it.getStringExtra("tipoempresa");
            descEmpresa = it.getStringExtra("descempresa");
        }
        iniciarDownload(cidadeId, tipoEmpresa);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_empresas, container, false);
        lblTipoEmp = (TextView) view.findViewById(R.id.lblTipoEmp);
        lblTipoEmp.setText(descEmpresa);
        return view;
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
            for (int i = 0; i < mListaEmpresas.size(); i++) {
                Empresa emp = mListaEmpresas.get(i);
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
            refreshList(mListaEmpresas);
            mSearchView.clearFocus();
            return false;
        }

        mSearchView.clearFocus();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        if (query.trim().equals("")) {
            refreshList(mListaEmpresas);
            queryId = 0;
            return false;
        }
        return true;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (getActivity() instanceof ClickListener) {
            if (queryId == 0)
                ((ClickListener) getActivity()).empresaClick(mListaEmpresas.get(position));
            else
                ((ClickListener) getActivity()).empresaClick(mListaQuery.get(position));
        }
    }

    public void iniciarDownload(String id, String tipo) {
        if (conexaoHttp.temConexao(getActivity()))
            new ListarEmpresasPorTipoTask().execute(id, tipo);
    }

    class ListarEmpresasPorTipoTask extends AsyncTask<String, Void, List<Empresa>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Carregando...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected List<Empresa> doInBackground(String... params) {
            try {
                mListaEmpresas = conexaoHttp.listarEmpresasPorTipo(params[0], params[1]);
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
                refreshList(mListaEmpresas);
            }
            pDialog.dismiss();
        }
    }

    public void refreshList(List<Empresa> lista) {
        setListAdapter(new EmpresasAdapter(getActivity(), lista));
    }
}