package br.com.egcservices.portaldamoda.telas;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.egcservices.portaldamoda.R;
import br.com.egcservices.portaldamoda.adapters.EmpresasAdapter;
import br.com.egcservices.portaldamoda.classes.Empresa;
import br.com.egcservices.portaldamoda.utils.ClickListener;
import br.com.egcservices.portaldamoda.utils.ConexaoHttp;

public class ListaAlimHotFragment extends ListFragment {

    AutoCompleteTextView actEmps;
    TextView lblTipoEmp;
    ConexaoHttp conexaoHttp = new ConexaoHttp();
    ProgressDialog pDialog;

    private static String cidadeId, tipoEmpresa, descEmpresa;
    private static List<Empresa> mListaEmpresas = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        actEmps = (AutoCompleteTextView) view.findViewById(R.id.actEmps);
        if (tipoEmpresa.equals("1")) {
            actEmps.setHint("Pesquisar Empresa");
            actEmps.setCompletionHint("Selecione uma Empresa");
        } else if (tipoEmpresa.equals("3")) {
            actEmps.setHint("Pesquisar Hotel");
            actEmps.setCompletionHint("Selecione um Hotel");
        }
        lblTipoEmp = (TextView) view.findViewById(R.id.lblTipoEmp);
        lblTipoEmp.setText(descEmpresa);
        actEmps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nomeEmpresa = parent.getItemAtPosition(position).toString();
                if (getActivity() instanceof ClickListener) {
                    ((ClickListener) getActivity())
                            .empresaClick(validarClick(true, cidadeId, tipoEmpresa, nomeEmpresa, mListaEmpresas));
                }
            }
        });
        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (getActivity() instanceof ClickListener) {
            ((ClickListener) getActivity()).empresaClick(mListaEmpresas.get(position));
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
                refreshList();
            }
            pDialog.dismiss();
        }
    }

    public void refreshList() {
        List<String> emps = new ArrayList<>();
        setListAdapter(new EmpresasAdapter(getActivity(), mListaEmpresas));
        //atualizar autocompletetextview
        for (int i = 0; i < mListaEmpresas.size(); i++) {
            emps.add(mListaEmpresas.get(i).getNome_empresa());
        }
        actEmps.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, emps));
    }

    public Empresa validarClick(boolean flag, String cid, String tipo, String nome, List<Empresa> lista) {
        Empresa mEmpresaResult;
        if (flag) {
            for (int i = 0; i < lista.size(); i++) {
                mEmpresaResult = lista.get(i);
                if (mEmpresaResult.getCidade_empresa_id().equals(Integer.valueOf(cid))
                        && mEmpresaResult.getTipo_empresa_id().equals(Integer.valueOf(tipo))
                        && mEmpresaResult.getNome_empresa().equals(nome)) {
                    return mEmpresaResult;
                }
            }
        }
        return null;
    }
}