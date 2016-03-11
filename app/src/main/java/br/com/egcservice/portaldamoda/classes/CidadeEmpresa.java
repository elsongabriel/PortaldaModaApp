package br.com.egcservice.portaldamoda.classes;

import java.io.Serializable;

/**
 * Created by Elson on 19/02/2016.
 */

@SuppressWarnings("serial")
public class CidadeEmpresa implements Serializable {
    Integer id;
    String cidade;

    public CidadeEmpresa(Integer id, String cidade) {
        this.id = id;
        this.cidade = cidade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public CidadeEmpresa() {
    }
}
