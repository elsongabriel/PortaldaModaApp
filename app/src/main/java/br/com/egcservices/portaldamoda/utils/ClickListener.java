package br.com.egcservices.portaldamoda.utils;

import br.com.egcservices.portaldamoda.classes.Empresa;
import br.com.egcservices.portaldamoda.classes.Excursao;

public interface ClickListener {
    void empresaClick(Empresa empresa);

    void excursaoClick(Excursao excursao);
}
