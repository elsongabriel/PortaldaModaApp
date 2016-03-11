package br.com.egcservice.portaldamoda.utils;

import br.com.egcservice.portaldamoda.classes.Empresa;
import br.com.egcservice.portaldamoda.classes.Excursao;

public interface ClickListener {
    void empresaClick(Empresa empresa);

    void excursaoClick(Excursao excursao);
}
