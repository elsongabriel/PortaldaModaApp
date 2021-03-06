package br.com.egcservices.portaldamoda.telas.excursao;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.egcservices.portaldamoda.R;
import br.com.egcservices.portaldamoda.adapters.ExcursoesAdapter;
import br.com.egcservices.portaldamoda.classes.Excursao;
import br.com.egcservices.portaldamoda.utils.ConexaoHttp;
import br.com.egcservices.portaldamoda.utils.listeners.ClickListener;

public class ListaExcursoesFragment extends Fragment {

    private static final String EXTRA_EXC = "excursao";
    TextView lblTipoEmp;
    ConexaoHttp conexaoHttp = new ConexaoHttp();
    ProgressDialog pDialog;
    ListView listView;

    private static String cidadeId, tipoEmpresa, descEmpresa;
    private static List<Excursao> mListaExcursoes = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_excursoes, container, false);
        listView = (ListView) view.findViewById(R.id.excursoes_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (getActivity() instanceof ClickListener) {
                    ((ClickListener) getActivity()).excursaoClick(mListaExcursoes.get(position));
                }
            }
        });

        if (savedInstanceState != null) {
            mListaExcursoes = (List<Excursao>) savedInstanceState.getSerializable(EXTRA_EXC);
        } else {
            Intent it = getActivity().getIntent();
            if (it.hasExtra("cidade") && it.hasExtra("tipoempresa") && it.hasExtra("descempresa")) {
                cidadeId = it.getStringExtra("cidade");
                tipoEmpresa = it.getStringExtra("tipoempresa");
                descEmpresa = it.getStringExtra("descempresa");
            }
            iniciarDownload(cidadeId, tipoEmpresa);
        }

        lblTipoEmp = (TextView) view.findViewById(R.id.lblTipoEmpExc);
        lblTipoEmp.setText(descEmpresa);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(EXTRA_EXC, (Serializable) mListaExcursoes);
    }

    public void iniciarDownload(String id, String tipo) {
        if (conexaoHttp.temConexao(getActivity()))
            new ListarExcursoesPorCidadeTask().execute(id, tipo);
    }

    class ListarExcursoesPorCidadeTask extends AsyncTask<String, Void, List<Excursao>> {
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
        protected List<Excursao> doInBackground(String... params) {
            try {
                if (params[1].equals("2"))
                    mListaExcursoes = conexaoHttp.listarExcursoesPorCidade(params[0]);
                return mListaExcursoes;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Excursao> result) {
            super.onPostExecute(result);
            if (result != null) {
                refreshList();
            }
            pDialog.dismiss();
        }
    }

    public void refreshList() {
        listView.setAdapter(new ExcursoesAdapter(getActivity(), mListaExcursoes));
    }
}