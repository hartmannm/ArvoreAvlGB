/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.unisinos.arvoreavlgb.arvore;

import br.com.unisinos.arvoreavlgb.arvore.no.No;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArvoreAvl<T> {
    
    private static final String ERRO_ARVORE_VAZIA = "A árvore está vazia!";

    private No root;

    public void inserir(T elemento, Integer indice) {
        No temp = new No(elemento, indice);
        if (root == null) {
            root = temp;
        } else {
            temp = root.inserir(temp);
        }

        if (temp != null) {
            root = temp;
        }
    }

    public List<Integer> buscaPorData(Date dataInicial, Date dataFinal) {
        List<Integer> list = new ArrayList();
        root.buscaPorData(dataInicial, dataFinal, list);

        return list;
    }

    public List<Integer> buscaPorNome(String nome) {
        List<Integer> list = new ArrayList();
        root.buscaPorNome(nome, list);

        return list;
    }
    
    public Integer buscaporCpf(Long cpf) {
        return root.buscaPorCpf(cpf);
    }

    public void percursoPreOrdem() {
        // Se a árvore possui dados
        if (root != null) {
            List<Integer> lista = new ArrayList();
            root.percursoPreOrdem(lista);
        } else {
            System.out.println(ERRO_ARVORE_VAZIA);
        }
    }
    
    public void percursoEmOrdem() {
        // Se a árvore possui dados
        if (root != null) {
            List<Integer> lista = new ArrayList();
            root.percursoEmOrdem(lista);
        } else {
            System.out.println(ERRO_ARVORE_VAZIA);
        }
    }

    public void percursoPosOrdem() {
        // Se a árvore possui dados
        if (root != null) {
            List<Integer> lista = new ArrayList();
            root.percursoPosOrdem(lista);
        } else {
            System.out.println(ERRO_ARVORE_VAZIA);
        }
    }
    
}
