package br.com.egcservice.portaldamoda.telas;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.egcservice.portaldamoda.R;
import br.com.egcservice.portaldamoda.adapters.DetailFotosAdapter;
import br.com.egcservice.portaldamoda.classes.Empresa;
import br.com.egcservice.portaldamoda.classes.FotoEmpresa;
import br.com.egcservice.portaldamoda.utils.ClickFavListener;
import br.com.egcservice.portaldamoda.utils.ConexaoHttp;
import br.com.egcservice.portaldamoda.utils.EmpresaDB;
import br.com.egcservice.portaldamoda.utils.Mensagem;

public class DetailEmpresaFragment extends ListFragment {

    TextView lblEmp, lblEnd, lblTel;
    ImageView imgViewIcon;
    Button btnLigar;
    private MenuItem mMenuItemFavorito;

    ConexaoHttp conexaoHttp = new ConexaoHttp();
    public Empresa mEmpresa;
    private static List<FotoEmpresa> mListaFotosEmpresa = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Intent it = getActivity().getIntent();
        if (it.hasExtra("empresa")) {
            mEmpresa = (Empresa) it.getSerializableExtra("empresa");
            if (mEmpresa.getPlano_empresa_id() > 1) {
                //planos pagos: 2 ou 3
                iniciarDownloadFotos(mEmpresa.getId().toString());
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_detail_empresa, menu);
        mMenuItemFavorito = menu.findItem(R.id.action_favorito);
        mudarMenuFavorito();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorito:
                tratarFavorito();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void mudarMenuFavorito() {
        EmpresaDB empDB = new EmpresaDB(getActivity());
        boolean favorito = empDB.favorito(mEmpresa);
        if (favorito) {
            mMenuItemFavorito.setTitle("Remover dos Favoritos");
            mMenuItemFavorito.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
        } else {
            mMenuItemFavorito.setTitle("Adicionar aos Favoritos");
            mMenuItemFavorito.setIcon(R.mipmap.ic_action_fav);
        }
    }

    private void tratarFavorito() {
        EmpresaDB empDB = new EmpresaDB(getActivity());
        boolean favorito = empDB.favorito(mEmpresa);
        String mensagem;

        if (favorito) {
            empDB.excluirFavorito(mEmpresa);
            mensagem = "Removido dos Favoritos";
        } else {
            empDB.inserirFavorito(mEmpresa);
            mensagem = "Adicionado aos Favoritos";
        }
        Mensagem.exibir(getActivity(), mensagem);

        if (getActivity() instanceof ClickFavListener) {
            ClickFavListener listener = (ClickFavListener) getActivity();
            listener.clickFav(mEmpresa, !favorito);
        }
        mudarMenuFavorito();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_empresa, container, false);

        lblEmp = (TextView) view.findViewById(R.id.lblDescricaoEmp);
        lblEnd = (TextView) view.findViewById(R.id.lblEndDetailEmp);
        lblTel = (TextView) view.findViewById(R.id.lblTelDetailEmp);
        imgViewIcon = (ImageView) view.findViewById(R.id.imgViewFotoDetail);
        btnLigar = (Button) view.findViewById(R.id.btnLigarDetailEmp);
        btnLigar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //metodo para realizar ligação
                Uri uri = Uri.parse("tel:" + mEmpresa.getTelefone_emp());
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intent);
            }
        });
        lblEmp.setText(mEmpresa.getNome_empresa());
        lblEnd.setText(mEmpresa.getEndereco_emp());
        lblTel.setText(mEmpresa.getTelefone_emp());
        return view;
    }

    public void iniciarDownloadFotos(String empresaId) {
        if (conexaoHttp.temConexao(getActivity()))
            new ListarFotosEmpresasTask().execute(empresaId);
    }

    class ListarFotosEmpresasTask extends AsyncTask<String, Void, List<FotoEmpresa>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<FotoEmpresa> doInBackground(String... params) {
            try {
                mListaFotosEmpresa = conexaoHttp.listarFotosPorEmpresas(params[0]);
                return mListaFotosEmpresa;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<FotoEmpresa> result) {
            super.onPostExecute(result);
            if (result != null) {
                setListAdapter(new DetailFotosAdapter(getActivity(), result, mEmpresa));
            }
        }
    }
}