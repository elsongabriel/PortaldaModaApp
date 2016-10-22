package br.com.egcservices.portaldamoda.telas.excursao;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.egcservices.portaldamoda.R;
import br.com.egcservices.portaldamoda.classes.Excursao;

public class DetailExcursaoFragment extends Fragment {

    TextView lblOrigem, lblDestino, lblDataHora, lblResponsavel, lblTelResp, lblForma, lblValor;
    ImageView imgViewIcon;
    Button btnLigar;

    public Excursao mExcursao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Intent it = getActivity().getIntent();
        if (it.hasExtra("excursao")) {
            mExcursao = (Excursao) it.getSerializableExtra("excursao");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_excursao, container, false);

        lblOrigem = (TextView) view.findViewById(R.id.lblOrigemExc);
        lblDestino = (TextView) view.findViewById(R.id.lblDestinoExc);
        lblDataHora = (TextView) view.findViewById(R.id.lblDataHoraExc);
        lblResponsavel = (TextView) view.findViewById(R.id.lblRespExc);
        lblTelResp = (TextView) view.findViewById(R.id.lblTelRespExc);
        lblValor = (TextView) view.findViewById(R.id.lblValorExc);
        lblForma = (TextView) view.findViewById(R.id.lblFormaPgtoExc);
        imgViewIcon = (ImageView) view.findViewById(R.id.imgViewIconDetailExc);
        btnLigar = (Button) view.findViewById(R.id.btnLigarDetailExc);
        btnLigar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //metodo para realizar ligação
                Uri uri = Uri.parse("tel:" + mExcursao.getTelefone_responsavel());
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intent);
            }
        });
        lblOrigem.setText("Saída: " + mExcursao.getOrigem());
        lblDestino.setText("Destino: " + mExcursao.getDestino());
        lblDataHora.setText("Data e Hora: " + mExcursao.getData_hora());
        lblResponsavel.setText("Responsável: " + mExcursao.getResponsavel());
        lblTelResp.setText("Tel. Responsável: " + mExcursao.getTelefone_responsavel());
        lblValor.setText("Valor: R$" + mExcursao.getValor());
        lblForma.setText("Forma de pagamento: " + mExcursao.getForma_pgto());
        return view;
    }
}