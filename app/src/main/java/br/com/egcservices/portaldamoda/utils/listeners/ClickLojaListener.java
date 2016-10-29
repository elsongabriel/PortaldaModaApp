package br.com.egcservices.portaldamoda.utils.listeners;

import br.com.egcservices.portaldamoda.classes.CategoriaLoja;
import br.com.egcservices.portaldamoda.classes.Empresa;

public interface ClickLojaListener {
    void catLojaClick(CategoriaLoja cat);

    void lojaClick(Empresa empresa);
}
