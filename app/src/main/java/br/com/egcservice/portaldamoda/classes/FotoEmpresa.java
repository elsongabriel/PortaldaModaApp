package br.com.egcservice.portaldamoda.classes;

import java.io.Serializable;

/**
 * Created by Elson on 19/02/2016.
 */
public class FotoEmpresa implements Serializable {
    Integer id, empresa_id;
    String caminho_img, nome_produto, desc_produto, valor_produto;

    public FotoEmpresa(Integer id, Integer empresa_id, String caminho_img, String nome_produto, String desc_produto, String valor_produto) {
        this.id = id;
        this.empresa_id = empresa_id;
        this.caminho_img = caminho_img;
        this.nome_produto = nome_produto;
        this.desc_produto = desc_produto;
        this.valor_produto = valor_produto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmpresa_id() {
        return empresa_id;
    }

    public void setEmpresa_id(Integer empresa_id) {
        this.empresa_id = empresa_id;
    }

    public String getCaminho_img() {
        return caminho_img;
    }

    public void setCaminho_img(String caminho_img) {
        this.caminho_img = caminho_img;
    }

    public String getNome_produto() {
        return nome_produto;
    }

    public void setNome_produto(String nome_produto) {
        this.nome_produto = nome_produto;
    }

    public String getDesc_produto() {
        return desc_produto;
    }

    public void setDesc_produto(String desc_produto) {
        this.desc_produto = desc_produto;
    }

    public String getValor_produto() {
        return valor_produto;
    }

    public void setValor_produto(String valor_produto) {
        this.valor_produto = valor_produto;
    }

    public FotoEmpresa() {
    }
}
