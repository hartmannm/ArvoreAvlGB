/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.unisinos.arvoreavlgb.arvore.gui.facade;

import br.com.unisinos.arvoreavlgb.arvore.ArvoreAvl;
import br.com.unisinos.arvoreavlgb.arvore.utils.DateUtils;
import br.com.unisinos.arvoreavlgb.arvore.utils.GuiUtils;
import br.com.unisinos.arvoreavlgb.pessoa.Pessoa;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;
import javax.swing.ButtonGroup;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Classe responsável pela execução de lógicas do componente de consulta de
 * pessoas
 *
 * @author Marcelo Augusto Gava
 * @author Mauricio Hartmann
 */
public class ArvoreConsultaFormFacade {

    /** Cabeçalho tabela - Coluna CPF */
    private static final String COLUNA_CPF = "CPF";
    /** Cabeçalho tabela - Coluna RG */
    private static final String COLUNA_RG = "RG";
    /** Cabeçalho tabela - Coluna Nome */
    private static final String COLUNA_NOME = "Nome";
    /** Cabeçalho tabela - Coluna Data de nascimento */
    private static final String COLUNA_DATA_NASC = "Data de Nasc.";
    /** Cabeçalho tabela - Coluna Cidade */
    private static final String COLUNA_CIDADE = "Cidade";
    /** Mensagem de erro - Sem dados */
    private static final String MENSAGEM_SEM_DADOS = "Nenhum resultado encontrado!!";
    /** Comprimento do CPF */
    private static final int CPF_LENGTH = 11;

    /**
     * Popula as árvores de acordo com a lista informada
     *
     * @param listaPessoas Lista de pessoas
     * @param arvoreCpf Árvore CPF
     * @param arvoreNome Árvore Nome
     * @param arvoreDataNascimento Árvore Data de nascimento
     */
    public void populaArvores(List<Pessoa> listaPessoas, ArvoreAvl<Long> arvoreCpf,
            ArvoreAvl<String> arvoreNome, ArvoreAvl<Date> arvoreDataNascimento) {
        // Percorre a lista de pessoas e popula as árvores
        listaPessoas.forEach((Pessoa pessoa) -> {
            Integer indice = listaPessoas.indexOf(pessoa);
            arvoreCpf.inserir(pessoa.getCpf(), indice, pessoa.getCpf());
            arvoreNome.inserir(pessoa.getNome(), indice, pessoa.getCpf());
            arvoreDataNascimento.inserir(pessoa.getDataNascimento(), indice, pessoa.getCpf());
        });
    }

    /**
     * Retorna se o valor do campo CPF é válido
     *
     * @param valor Valor do campo CPF
     * @return boolean
     */
    public boolean isValorCampoCpfValido(String valor) {
        return valor != null && !valor.trim().isEmpty() && valor.length() == CPF_LENGTH;
    }

    /**
     * Retorna se o valor do campo nome é válido
     *
     * @param nome Valor do campo nome
     * @return boolean
     */
    public boolean isValorCampoNomeValido(String nome) {
        return nome != null && !nome.trim().isEmpty();
    }

    /**
     * Realiza a busca por CPF
     *
     * @param cpf CPF
     * @param arvore Árvore para busca
     * @param tabela Tabela para exibição dos dados
     * @param listaPessoas Lista com as pessoas para exibição
     */
    public void buscaPorCpf(Long cpf, ArvoreAvl arvore, JTable tabela, List<Pessoa> listaPessoas) {
        Integer index = arvore.buscaPorCpf(cpf);
        // Se foi encontrado algo
        if (index != null) {
            this.setDataTabelaPessoas(tabela, listaPessoas, Arrays.asList(index));
        } else {
            this.resetaTabela(tabela);
            GuiUtils.exibeAlerta(null, MENSAGEM_SEM_DADOS);
        }
    }

    /**
     * Realiza a busca por nome
     *
     * @param nome Nome
     * @param arvore Árvore para busca
     * @param tabela Tabela para exibição dos dados
     * @param listaPessoas Lista com as pessoas para exibição
     */
    public void buscaPorNome(String nome, ArvoreAvl arvore, JTable tabela, List<Pessoa> listaPessoas) {
        List<Integer> listaIndex = arvore.buscaPorNome(nome);
        // Se foi encontrado algo
        if (!listaIndex.isEmpty()) {
            this.setDataTabelaPessoas(tabela, listaPessoas, listaIndex);
        } else {
            this.resetaTabela(tabela);
            GuiUtils.exibeAlerta(null, MENSAGEM_SEM_DADOS);
        }
    }

    /**
     * Realiza a busca por data de nascimento
     *
     * @param dataInicial Data inicial para pesquisa
     * @param dataFinal Data final para pesquisa
     * @param arvore Árvore para busca
     * @param tabela Tabela para exibição dos dados
     * @param listaPessoas Lista com as pessoas para exibição
     */
    public void buscaPorDataNascimento(Date dataInicial, Date dataFinal, ArvoreAvl arvore,
            JTable tabela, List<Pessoa> listaPessoas) {
        List<Integer> listaIndex = arvore.buscaPorData(dataInicial, dataFinal);
        // Se algo foi encontrado
        if (!listaIndex.isEmpty()) {
            this.setDataTabelaPessoas(tabela, listaPessoas, listaIndex);
        } else {
            this.resetaTabela(tabela);
            GuiUtils.exibeAlerta(null, MENSAGEM_SEM_DADOS);
        }
    }

    /**
     * Retorna se algum checkbox de um grupo está selecionado
     *
     * @param checkboxGroup Grupo de checkbox
     * @return boolean
     */
    public boolean isCheckboxSelected(ButtonGroup checkboxGroup) {
        return checkboxGroup.getSelection() != null;
    }

    /**
     * Insere dados na tabela de pessoas
     *
     * @param tabela Tabela
     * @param listaPessoas Lista de pessoas a ser exibida
     * @param filtroIndices Lista com indices para filtro
     */
    public void setDataTabelaPessoas(JTable tabela, List<Pessoa> listaPessoas, List<Integer> filtroIndices) {
        // Filtra os dados da lista de acordo com os indices
        List<Pessoa> listaFiltrada = filtroIndices.stream()
                .map(index -> listaPessoas.get(index))
                .collect(Collectors.toList());
        setDataTabelaPessoas(tabela, listaFiltrada);
    }

    /**
     * Insere dados na tabela de pessoas
     *
     * @param tabela Tabela
     * @param listaPessoas Lista de pessoas a ser exibida
     */
    public void setDataTabelaPessoas(JTable tabela, List<Pessoa> listaPessoas) {
        DefaultTableModel tableModel = getNewPessoaTableModel();
        tabela.setModel(tableModel);
        setDadosLinhas(listaPessoas, tableModel);
    }

    /**
     * Reseta a tabela de pessoas
     *
     * @param tabela Tabela
     */
    public void resetaTabela(JTable tabela) {
        tabela.setModel(getNewPessoaTableModel());
    }

    /**
     * Retorna o model da tabela de pessoas
     *
     * @return DefaultTableModel
     */
    private DefaultTableModel getNewPessoaTableModel() {
        DefaultTableModel tableModel = new DefaultTableModel(0, 0);
        tableModel.setColumnIdentifiers(getCabecalhosTabelaPessoas());
        return tableModel;
    }

    /**
     * Retorna os cabeçalhos das colunas da tabela de pessoas
     *
     * @return String[]
     */
    private String[] getCabecalhosTabelaPessoas() {
        return new String[]{COLUNA_CPF, COLUNA_RG, COLUNA_NOME, COLUNA_DATA_NASC, COLUNA_CIDADE};
    }

    /**
     * Insere dados nas linhas da tabela de pessoas
     *
     * @param listaPessoas Lista de pessoas
     * @param tableModel Model da tabela
     */
    private void setDadosLinhas(List<Pessoa> listaPessoas, DefaultTableModel tableModel) {
        listaPessoas.forEach((Pessoa pessoa) -> {
            Vector<Object> data = new Vector<Object>();
            data.add(pessoa.getCpf());
            data.add(pessoa.getRg());
            data.add(pessoa.getNome());
            data.add(DateUtils.dateToString(pessoa.getDataNascimento()));
            data.add(pessoa.getCidadeNascimento());
            tableModel.addRow(data);
        });
    }

}
