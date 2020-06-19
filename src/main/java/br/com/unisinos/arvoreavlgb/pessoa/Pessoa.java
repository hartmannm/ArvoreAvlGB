package br.com.unisinos.arvoreavlgb.pessoa;

import java.util.Date;

/**
 * Entidade Pessoa
 *
 * @author Marcelo Augusto Gava
 * @author Mauricio Hartmann
 */
public class Pessoa {

    /** CPF */
    private Long cpf;
    /** RG */
    private Long rg;
    /** Nome */
    private String nome;
    /** Data de nascimento */
    private Date dataNascimento;
    /** Nome da cidade de nascimento */
    private String cidadeNascimento;

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    public Long getRg() {
        return rg;
    }

    public void setRg(Long rg) {
        this.rg = rg;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCidadeNascimento() {
        return cidadeNascimento;
    }

    public void setCidadeNascimento(String cidadeNascimento) {
        this.cidadeNascimento = cidadeNascimento;
    }

}
