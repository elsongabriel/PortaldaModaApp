package br.com.egcservices.portaldamoda.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.egcservices.portaldamoda.R;
import br.com.egcservices.portaldamoda.classes.Empresa;
import br.com.egcservices.portaldamoda.classes.FotoEmpresa;

public class DetailFotosAdapter extends ArrayAdapter<FotoEmpresa> {

    private FotoEmpresa mFotoEmpresa;
    private TextView mLblNomeEmpresa, mLblNomeProd, mLblValorProd, mLblDescProduto;
    private ImageView imgFotoDetail;
    public Empresa mEmpresa;

    public DetailFotosAdapter(Context ctx, List<FotoEmpresa> fotos, Empresa empresa) {
        super(ctx, 0, fotos);
        mEmpresa = empresa;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        mFotoEmpresa = getItem(position);
        String diretorio = "http://egcservices.com.br/webservices/portaldamoda/fotos/";

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_detail_fotos, null);
        }

        mLblNomeEmpresa = (TextView) convertView.findViewById(R.id.lblDescEmpDetail);
        mLblNomeProd = (TextView) convertView.findViewById(R.id.lblNomeProduto);
        mLblValorProd = (TextView) convertView.findViewById(R.id.lblValorProduto);
        mLblDescProduto = (TextView) convertView.findViewById(R.id.lblDescProduto);
        imgFotoDetail = (ImageView) convertView.findViewById(R.id.imgViewFotoDetail);

        mLblNomeEmpresa.setText(mEmpresa.getNome_empresa());
        mLblNomeProd.setText(mFotoEmpresa.getNome_produto());
        mLblValorProd.setText("R$ " + String.valueOf(mFotoEmpresa.getValor_produto()));
        mLblDescProduto.setText(mFotoEmpresa.getDesc_produto());
        if (mFotoEmpresa.getCaminho_img().trim().equals("")) {
            Picasso.with(parent.getContext()).load(diretorio + "sem_foto.jpg").into(imgFotoDetail);
        } else {
            Picasso.with(parent.getContext()).load(diretorio + mFotoEmpresa.getCaminho_img()).into(imgFotoDetail);
        }
        return convertView;
    }
}
