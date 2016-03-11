package br.com.egcservice.portaldamoda.classes;

import java.io.Serializable;

/**
 * Created by Elson on 19/02/2016.
 */

@SuppressWarnings("serial")
public class Empresa implements Serializable {
    private Integer id, cidade_empresa_id, tipo_empresa_id, plano_empresa_id, categoria_id;
    private String nome_empresa, telefone_emp, endereco_emp, forma_pgto, facebook, instagram, twitter, site,
            nome_responsavel, telefone_responsavel;
    private boolean ativo;

    public Empresa(Integer id, Integer cidade_empresa_id, Integer tipo_empresa_id, Integer plano_empresa_id, Integer categoria_id, String nome_empresa, String telefone_emp, String endereco_emp, String forma_pgto, String facebook, String instagram, String twitter, String site, String nome_responsavel, String telefone_responsavel, boolean ativo) {
        this.id = id;
        this.cidade_empresa_id = cidade_empresa_id;
        this.tipo_empresa_id = tipo_empresa_id;
        this.plano_empresa_id = plano_empresa_id;
        this.categoria_id = categoria_id;
        this.nome_empresa = nome_empresa;
        this.telefone_emp = telefone_emp;
        this.endereco_emp = endereco_emp;
        this.forma_pgto = forma_pgto;
        this.facebook = facebook;
        this.instagram = instagram;
        this.twitter = twitter;
        this.site = site;
        this.nome_responsavel = nome_responsavel;
        this.telefone_responsavel = telefone_responsavel;
        this.ativo = ativo;
    }

    public Empresa(Integer empid, Integer cid) {
        this.id = empid;
        this.cidade_empresa_id = cid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCidade_empresa_id() {
        return cidade_empresa_id;
    }

    public void setCidade_empresa_id(Integer cidade_empresa_id) {
        this.cidade_empresa_id = cidade_empresa_id;
    }

    public Integer getTipo_empresa_id() {
        return tipo_empresa_id;
    }

    public void setTipo_empresa_id(Integer tipo_empresa_id) {
        this.tipo_empresa_id = tipo_empresa_id;
    }

    public Integer getPlano_empresa_id() {
        return plano_empresa_id;
    }

    public void setPlano_empresa_id(Integer plano_empresa_id) {
        this.plano_empresa_id = plano_empresa_id;
    }

    public Integer getCategoria_id() {
        return categoria_id;
    }

    public void setCategoria_id(Integer categoria_id) {
        this.categoria_id = categoria_id;
    }

    public String getNome_empresa() {
        return nome_empresa;
    }

    public void setNome_empresa(String nome_empresa) {
        this.nome_empresa = nome_empresa;
    }

    public String getTelefone_emp() {
        return telefone_emp;
    }

    public void setTelefone_emp(String telefone_emp) {
        this.telefone_emp = telefone_emp;
    }

    public String getEndereco_emp() {
        return endereco_emp;
    }

    public void setEndereco_emp(String endereco_emp) {
        this.endereco_emp = endereco_emp;
    }

    public String getForma_pgto() {
        return forma_pgto;
    }

    public void setForma_pgto(String forma_pgto) {
        this.forma_pgto = forma_pgto;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getNome_responsavel() {
        return nome_responsavel;
    }

    public void setNome_responsavel(String nome_responsavel) {
        this.nome_responsavel = nome_responsavel;
    }

    public String getTelefone_responsavel() {
        return telefone_responsavel;
    }

    public void setTelefone_responsavel(String telefone_responsavel) {
        this.telefone_responsavel = telefone_responsavel;
    }

    public boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }


    public Empresa() {
    }
}
