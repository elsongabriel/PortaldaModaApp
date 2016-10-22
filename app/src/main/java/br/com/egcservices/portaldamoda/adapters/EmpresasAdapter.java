package br.com.egcservices.portaldamoda.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.egcservices.portaldamoda.R;
import br.com.egcservices.portaldamoda.classes.Empresa;

public class EmpresasAdapter extends ArrayAdapter<Empresa> {

    private Empresa mEmpresa;
    private TextView mLblNomeEmpresa;

    public EmpresasAdapter(Context ctx, List<Empresa> empresas) {
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
        if (mEmpresa.getPlano_empresa_id().equals(2)) {
            convertView.setBackgroundColor(Color.rgb(211, 211, 211));
        } else if (mEmpresa.getPlano_empresa_id().equals(3)) {
            convertView.setBackgroundColor(Color.rgb(255, 215, 0));
        } else {
            convertView.setBackgroundColor(Color.rgb(248, 248, 255));
        }
        return convertView;
    }
}
