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

    private No root;

    public void apagar(T elemento) {
        No temp = root.apagar(new No(elemento));
        if (temp == root) {
            this.root = null;
        } else if (temp != null) {
            root = temp;
            root.executaBalanceamento();
        }
    }

    public void inserir(T elemento) {
        No temp = new No(elemento);
        if (root == null) {
            root = temp;
        } else {
            temp = root.inserir(temp);
        }
        if (temp != null) {
            root = temp;
        }
    }

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

//    public No busca(T element) {
//        if (root != null) {
//            return root.busca(new No(element));
//        } else {
//            System.out.println("This tree is empty");
//            return null;
//        }
//    }

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

    public void percursoEmOrdem() {
        if (root != null) {
            root.percursoEmOrdem();
        } else {
            System.out.println("This tree is empty");
        }
        System.out.println();
    }

    public void percursoPosOrdem() {
        if (root != null) {
            root.percursoPosOrdem();
        } else {
            System.out.println("This tree is empty");
        }
        System.out.println();
    }

    public void percursoPreOrdem() {
        if (root != null) {
            root.percursoPreOrdem();
        } else {
            System.out.println("This tree is empty");
        }
    }
    
}
