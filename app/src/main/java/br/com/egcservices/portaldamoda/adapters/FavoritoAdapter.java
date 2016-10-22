package br.com.egcservices.portaldamoda.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.egcservices.portaldamoda.R;
import br.com.egcservices.portaldamoda.classes.Empresa;

public class FavoritoAdapter extends ArrayAdapter<Empresa> {

    private Empresa mEmpresa;
    private TextView mLblNomeEmpresa;

    public FavoritoAdapter(Context ctx, List<Empresa> empresas) {
        super(ctx, 0, empresas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        mEmpresa = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_lista_empresa, null);
        }
        mLblNomeEmpresa = (TextView) convertView.findViewById(R.id.lblNomeEmpresa);
        mLblNomeEmpresa.setText(mEmpresa.getNome_empresa());
        return convertView;
    }
}
