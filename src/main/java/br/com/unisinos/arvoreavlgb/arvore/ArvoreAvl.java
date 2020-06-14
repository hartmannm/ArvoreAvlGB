package br.com.unisinos.arvoreavlgb.arvore;

import br.com.unisinos.arvoreavlgb.arvore.no.No;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Árvore AVL
 *
 * @author Marcello Augusto Gava
 * @author Mauricio Hartmann
 * @param <T> Tipo de dado da árvore
 */
public class ArvoreAvl<T> {

    /** Mensagem de erro - árvore vazia */
    private static final String ERRO_ARVORE_VAZIA = "A árvore está vazia!";
    /** Nó raiz */
    private No root;

    /**
     * Insere um novo nó na árvore
     *
     * @param valor Valor do nó
     * @param indice Índice do nó em uma lista
     */
    public void inserir(T valor, Integer indice) {
        No aux = new No(valor, indice);
        // Se não possui raiz
        if (root == null) {
            root = aux;
        } else {
            aux = root.inserir(aux);
        }
        if (aux != null) {
            root = aux;
        }
    }

    /**
     * Remove um nó da árvore
     *
     * @param valor Valor do nó a ser removido
     */
    public void remover(T valor) {
        No<T> temp = root.remover(new No<T>(valor));
        // Se já existe um nó com o mesmo valor
        if (temp == root) {
            this.root = null;
        } else if (temp != null) {
            root = temp;
            root.executaBalanceamento();
        }
    }

    /**
     * Realiza uma busca por data na áravore e retorna uma lista com os indices
     * dos nós que se encaixam no filtro
     *
     * @param dataInicial Data incial
     * @param dataFinal Data final
     * @return {@code List<Integer>}
     */
    public List<Integer> buscaPorData(Date dataInicial, Date dataFinal) {
        List<Integer> lista = new ArrayList();
        root.buscaPorData(dataInicial, dataFinal, lista);
        return lista;
    }

    /**
     * Realiza uma busca por nome na árvore e retorna uma lista com os indices
     * dos nós que o nome começa com o valor informado
     *
     * @param nome Nome para pesquisa
     * @return {@code List<Integer>}
     */
    public List<Integer> buscaPorNome(String nome) {
        List<Integer> lista = new ArrayList();
        root.buscaPorNome(nome, lista);
        return lista;
    }

    /**
     * Realiza uma busca na árvoree e retorna o indice do nó que possui o CPF
     *
     * @param cpf CPF
     * @return Integer
     */
    public Integer buscaPorCpf(Long cpf) {
        return root.buscaPorCpf(cpf);
    }

    /**
     * Realiza o percurso em pré-ordem
     *
     * @return {@code List<Integer>}
     */
    public List<Integer> percursoPreOrdem() {
        List<Integer> lista = new ArrayList();
        // Se a árvore possui dados
        if (root != null) {
            root.percursoPreOrdem(lista);
        } else {
            System.out.println(ERRO_ARVORE_VAZIA);
        }
        return lista;
    }

    /**
     * Realiza o percurso em ordem
     *
     * @return {@code List<Integer>}
     */
    public List<Integer> percursoEmOrdem() {
        List<Integer> lista = new ArrayList();
        // Se a árvore possui dados
        if (root != null) {
            root.percursoEmOrdem(lista);
        } else {
            System.out.println(ERRO_ARVORE_VAZIA);
        }
        return lista;
    }

    /**
     * Realiza o percurso em pós-ordem
     *
     * @return {@code List<Integer>}
     */
    public List<Integer> percursoPosOrdem() {
        List<Integer> lista = new ArrayList();
        // Se a árvore possui dados
        if (root != null) {
            root.percursoPosOrdem(lista);
        } else {
            System.out.println(ERRO_ARVORE_VAZIA);
        }
        return lista;
    }

}
