package br.com.egcservices.portaldamoda.utils;

import br.com.egcservices.portaldamoda.classes.CategoriaLoja;
import br.com.egcservices.portaldamoda.classes.Empresa;
import br.com.egcservices.portaldamoda.classes.Excursao;

public interface ClickLojaListener {
    void catLojaClick(CategoriaLoja cat);

    void lojaClick(Empresa empresa);
}
