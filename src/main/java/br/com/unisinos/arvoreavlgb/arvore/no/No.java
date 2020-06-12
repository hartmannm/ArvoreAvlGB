package br.com.unisinos.arvoreavlgb.arvore.no;

import java.util.Date;
import java.util.List;

public class No<T> implements Comparable<No<T>> {

    private int altura;
    private int fatorBalanceamento;
    private final T valor;
    private Integer indice;
    private No<T> noEsquerdo;
    private No<T> noDireito;

    public No(T valor) {
        this.valor = valor;
        altura = 1;
        this.noEsquerdo = this.noDireito = null;
    }

    public No(T valor, int indice) {
        this.indice = indice;
        this.valor = valor;
        altura = 1;
        this.noEsquerdo = this.noDireito = null;
    }

    public int getAltura() {
        return this.altura;
    }

    public int getFatorBalanceamento() {
        return this.fatorBalanceamento;
    }

    public T getValor() {
        return this.valor;
    }

    public Integer getIndice() {
        return this.indice;
    }

    public void setIndice(Integer indice) {
        this.indice = indice;
    }

    public No<T> getNoEsquerdo() {
        return this.noEsquerdo;
    }

    public void setNoEsquerdo(No<T> no) {
        this.noEsquerdo = no;
    }

    public No<T> getNoDireito() {
        return this.noDireito;
    }

    public void setNoDireito(No<T> no) {
        this.noDireito = no;
    }

    private void calculaFatorBalancemanto() {
        int alturaEsquerda = possuiNoEsquerdo() ? noEsquerdo.getAltura() : 0;
        int alturaDireita = possuiNoDireito() ? noDireito.getAltura() : 0;
        this.fatorBalanceamento = alturaEsquerda - alturaDireita;
    }

    private void calculaAltura() {
        if (isFolha()) {
            this.altura = 1;
        }
        int alturaEsquerda = 0;
        int alturaDireita = 0;
        if (noEsquerdo != null) {
            alturaEsquerda = noEsquerdo.getAltura();
        }
        if (noDireito != null) {
            alturaDireita = noDireito.getAltura();
        }
        if (alturaEsquerda > alturaDireita) {
            this.altura = alturaEsquerda + 1;
        } else {
            this.altura = alturaDireita + 1;
        }
    }

    @Override
    public int compareTo(No<T> no) {
        int resultado = 0;
        if (valor instanceof String) {
            resultado = ((String) no.getValor()).compareTo((String) valor);
        } else if (valor instanceof Integer) {
            resultado = ((Integer) valor).compareTo((Integer) no.getValor());
        } else if (valor instanceof Long) {
            resultado = ((Long) valor).compareTo((Long) no.getValor());
        } else if (valor instanceof Date) {
            resultado = ((Date) valor).compareTo((Date) no.getValor());
        }
        return resultado;
    }

    public boolean possuiNoEsquerdo() {
        return this.noEsquerdo != null;
    }

    public boolean possuiNoDireito() {
        return this.noDireito != null;
    }

    public boolean isFolha() {
        return this.noEsquerdo != null && this.noDireito != null;
    }

    public No<T> executaBalanceamento() {
        this.calculaAltura();
        this.calculaFatorBalancemanto();
        if (fatorBalanceamento > 1) {
            noEsquerdo.calculaFatorBalancemanto();
            if (noEsquerdo.getFatorBalanceamento() > 0) {
                // Rotação simples a direita
                return this.rotacaoDireita();
            } else if (noEsquerdo.getFatorBalanceamento() < 0) {
                // Rotação dupla a direita
                noEsquerdo = noEsquerdo.rotacaoEsquerda();
                return this.rotacaoDireita();
            } else if (noEsquerdo.getFatorBalanceamento() == 0) {
                // Rotação dupla a direita
                return this.rotacaoDireita();
            }
        } else if (fatorBalanceamento < -1) {
            noDireito.calculaFatorBalancemanto();
            if (noDireito.getFatorBalanceamento() < 0) {
                // Rotação simples a esquerda
                return this.rotacaoEsquerda();
            } else if (noDireito.getFatorBalanceamento() > 0) {
                // Rotação dupla a esquerda
                noDireito = noDireito.rotacaoDireita();
                return this.rotacaoEsquerda();
            } else if (noDireito.getFatorBalanceamento() == 0) {
                // Rotação dupla a esquerda
                return this.rotacaoEsquerda();
            }
        }
        return null;

    }

    public No<T> inserir(No<T> no) {
        if (this.compareTo(no) == 0) {
            return null;
        }
        if (this.compareTo(no) < 0) {
            if (noDireito == null) {
                noDireito = no;
            } else {
                No<T> aux = noDireito.inserir(no);
                if (aux != null) {
                    noDireito = aux;
                }
            }
        } else if (this.compareTo(no) > 0) {
            if (noEsquerdo == null) {
                noEsquerdo = no;
            } else {
                No<T> aux = noEsquerdo.inserir(no);
                if (aux != null) {
                    noEsquerdo = aux;
                }
            }
        }
        return this.executaBalanceamento();
    }

    private No<T> rotacaoEsquerda() {
        No<T> temp = noDireito;
        if (noDireito.getNoEsquerdo() == null) {
            noDireito.setNoEsquerdo(this);
            this.noDireito = null;
        } else {
            this.noDireito = temp.getNoEsquerdo();
            temp.noEsquerdo = this;
        }
        this.calculaAltura();
        temp.calculaAltura();
        return temp;
    }

    private No<T> rotacaoDireita() {
        No<T> temp = noEsquerdo;
        if (noEsquerdo.getNoDireito() == null) {
            noEsquerdo.setNoDireito(this);
            this.noEsquerdo = null;
        } else {
            this.noEsquerdo = temp.getNoDireito();
            temp.noDireito = this;
        }
        this.calculaAltura();
        temp.calculaAltura();
        return temp;
    }

    /**
     * Preenche uma lista com os dados que se encaixam no filtro de datas
     *
     * @param dataInicial Data inicial
     * @param dataFinal Data final
     * @param lista Lista a ser preenchida
     */
    public void buscaPorData(Date dataInicial, Date dataFinal, List<Integer> lista) {
        // Se possui nó esquerdo
        if (possuiNoEsquerdo()) {
            noEsquerdo.buscaPorData(dataInicial, dataFinal, lista);
        }
        // Se a data está entre a data inicial e final
        if (((Date) this.valor).before(dataFinal) && ((Date) this.valor).after(dataInicial)) {
            lista.add(this.indice);
        }
        // Se possui nó direito
        if (possuiNoDireito()) {
            noDireito.buscaPorData(dataInicial, dataFinal, lista);
        }
    }

    /**
     * Preenche uma lista com os valores que iniciam com o valor pesquisado
     *
     * @param filtroPesquisa Nome a ser pesquisado
     * @param lista Lista a ser preenchida
     */
    public void buscaPorNome(String filtroPesquisa, List<Integer> lista) {
        // Se possui nó esquerdo
        if (possuiNoEsquerdo()) {
            noEsquerdo.buscaPorNome(filtroPesquisa, lista);
        }
        // Se o nome inicia com o valor pesquisado
        if (((String) this.valor).toLowerCase().startsWith(filtroPesquisa.toLowerCase())) {
            lista.add(this.indice);
        }
        // Se possui nó direito
        if (possuiNoDireito()) {
            noDireito.buscaPorNome(filtroPesquisa, lista);
        }
    }

    /**
     * Retorna o indice do nó caso o valor seja igual ao CPF informado
     *
     * @param cpf CPF
     * @return Long
     */
    public Integer buscaPorCpf(Long cpf) {
        Long valorAux = Long.valueOf(this.valor.toString());
        if (cpf.equals(valorAux)) {
            return indice;
        } else if (cpf < valorAux) {
            if (possuiNoEsquerdo()) {
                return noEsquerdo.buscaPorCpf(cpf);
            } else {
                return null;
            }
        } else if (cpf > valorAux) {
            if (possuiNoDireito()) {
                return noDireito.buscaPorCpf(cpf);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public void percursoPreOrdem(List<Integer> lista) {
        lista.add(this.indice);
        if (possuiNoEsquerdo()) {
            noEsquerdo.percursoPreOrdem(lista);
        }
        if (possuiNoDireito()) {
            noDireito.percursoPreOrdem(lista);
        }
    }

    public void percursoEmOrdem(List<Integer> lista) {
        if (possuiNoEsquerdo()) {
            noEsquerdo.percursoEmOrdem(lista);
        }
        lista.add(this.indice);
        if (possuiNoDireito()) {
            noDireito.percursoEmOrdem(lista);
        }
    }

    public void percursoPosOrdem(List<Integer> lista) {
        if (possuiNoEsquerdo()) {
            noEsquerdo.percursoPosOrdem(lista);
        }
        if (possuiNoDireito()) {
            noDireito.percursoPosOrdem(lista);
        }
        lista.add(this.indice);
    }

}
