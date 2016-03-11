package br.com.egcservice.portaldamoda.classes;

import java.io.Serializable;

/**
 * Created by Elson on 19/02/2016.
 */

@SuppressWarnings("serial")
public class CategoriaLoja implements Serializable {
    Integer id;
    String categoria_desc;

    public CategoriaLoja(Integer id, String categoria_desc) {
        this.id = id;
        this.categoria_desc = categoria_desc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoria_desc() {
        return categoria_desc;
    }

    public void setCategoria_desc(String categoria_desc) {
        this.categoria_desc = categoria_desc;
    }

    public CategoriaLoja() {
    }
}
