package br.com.egcservice.portaldamoda.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.egcservice.portaldamoda.R;
import br.com.egcservice.portaldamoda.classes.Excursao;

public class ExcursoesAdapter extends ArrayAdapter<Excursao> {

    private Excursao mExcursao;
    private TextView mLblOrigem, mLblDestino;

    public ExcursoesAdapter(Context ctx, List<Excursao> excursoes) {
        super(ctx, 0, excursoes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        mExcursao = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_lista_excursao, null);
        }
        mLblOrigem = (TextView) convertView.findViewById(R.id.lblExcOrigem);
        mLblDestino = (TextView) convertView.findViewById(R.id.lblExcDestino);
        mLblOrigem.setText(mExcursao.getOrigem());
        mLblDestino.setText(mExcursao.getDestino());
        return convertView;
    }
}