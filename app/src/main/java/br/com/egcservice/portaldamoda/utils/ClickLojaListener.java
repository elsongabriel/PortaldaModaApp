package br.com.egcservice.portaldamoda.utils;

import br.com.egcservice.portaldamoda.classes.CategoriaLoja;
import br.com.egcservice.portaldamoda.classes.Empresa;
import br.com.egcservice.portaldamoda.classes.Excursao;

public interface ClickLojaListener {
    void catLojaClick(CategoriaLoja cat);

    void lojaClick(Empresa empresa);
}
