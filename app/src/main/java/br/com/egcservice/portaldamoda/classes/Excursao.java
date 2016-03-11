package br.com.egcservice.portaldamoda.classes;

import java.io.Serializable;

/**
 * Created by Elson on 19/02/2016.
 */

@SuppressWarnings("serial")
public class Excursao implements Serializable {
    private Integer id;
    private String origem, destino, data_hora, responsavel, telefone_responsavel, valor, forma_pgto;
    private boolean ativo;

    public Excursao(Integer id, String origem, String destino, String data_hora, String responsavel, String telefone_responsavel, String valor, String forma_pgto, boolean ativo) {
        this.id = id;
        this.origem = origem;
        this.destino = destino;
        this.data_hora = data_hora;
        this.responsavel = responsavel;
        this.telefone_responsavel = telefone_responsavel;
        this.valor = valor;
        this.forma_pgto = forma_pgto;
        this.ativo = ativo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getData_hora() {
        return data_hora;
    }

    public void setData_hora(String data_hora) {
        this.data_hora = data_hora;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getTelefone_responsavel() {
        return telefone_responsavel;
    }

    public void setTelefone_responsavel(String telefone_responsavel) {
        this.telefone_responsavel = telefone_responsavel;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getForma_pgto() {
        return forma_pgto;
    }

    public void setForma_pgto(String forma_pgto) {
        this.forma_pgto = forma_pgto;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Excursao() {
    }
}
