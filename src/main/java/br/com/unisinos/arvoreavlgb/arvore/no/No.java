package br.com.unisinos.arvoreavlgb.arvore.no;

import java.util.Date;
import java.util.List;

/**
 * Nó
 *
 * @author Marcello Augusto Gava
 * @author Mauricio Hartmann
 * @param <T> Tipo de dados do nó
 */
public class No<T> implements Comparable<No<T>> {

    /** Altura */
    private int altura;
    /** Fator de balanceamento */
    private int fatorBalanceamento;
    /** Valor do nó */
    private T valor;
    /** Índice do nó na lista */
    private Integer indice;
    /** Chave primária do objeto */
    private Long chavePrimaria;
    /** Nó filho a esquerda */
    private No<T> noEsquerdo;
    /** Nó filho a direita */
    private No<T> noDireito;

    public No(T valor, Long chavePrimaria) {
        this.valor = valor;
        this.chavePrimaria = chavePrimaria;
        altura = 1;
        this.noEsquerdo = this.noDireito = null;
    }

    public No(T valor, int indice, Long chavePrimaria) {
        this.indice = indice;
        this.valor = valor;
        this.chavePrimaria = chavePrimaria;
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

    public Long getChavePrimaria() {
        return chavePrimaria;
    }

    public void setChavePrimaria(Long chavePrimaria) {
        this.chavePrimaria = chavePrimaria;
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

    /**
     * Calcula o fator de balanceamento do nó atual
     */
    private void calculaFatorBalancemanto() {
        int alturaEsquerda = this.possuiNoEsquerdo() ? noEsquerdo.getAltura() : 0;
        int alturaDireita = this.possuiNoDireito() ? noDireito.getAltura() : 0;
        this.fatorBalanceamento = alturaEsquerda - alturaDireita;
    }

    /**
     * Calcula a altura do nó atual
     */
    private void calculaAltura() {
        // Se o nó não possui filhos
        if (isFolha()) {
            this.altura = 1;
        }
        int alturaEsquerda = 0;
        int alturaDireita = 0;
        // Se existe nó esquerdo
        if (this.possuiNoEsquerdo()) {
            alturaEsquerda = noEsquerdo.getAltura();
        }
        // Se existe nó direito
        if (this.possuiNoDireito()) {
            alturaDireita = noDireito.getAltura();
        }
        // Se a altura da esquerda for maior que da direita
        if (alturaEsquerda > alturaDireita) {
            this.altura = alturaEsquerda + 1;
        } else {
            this.altura = alturaDireita + 1;
        }
    }

    /**
     * Compara o valor do nó
     *
     * @param no Nó a ser comparado
     * @return int
     */
    @Override
    public int compareTo(No<T> no) {
        int resultado = 0;
        // Realiza a comparação de acordo com o tipo
        if (valor instanceof String) {
            resultado = ((String) no.getValor()).compareTo((String) valor);
        } else if (valor instanceof Integer) {
            resultado = ((Integer) valor).compareTo((Integer) no.getValor());
        } else if (valor instanceof Long) {
            resultado = ((Long) valor).compareTo((Long) no.getValor());
        } else if (valor instanceof Date) {
            resultado = ((Date) valor).compareTo((Date) no.getValor());
        }
        // Se o valor dos nós for igual
        if (resultado == 0) {
            return this.chavePrimaria == no.chavePrimaria ? resultado : -1;
        }
        return resultado;
    }

    /**
     * Retorna se possui nó filho a esquerda
     *
     * @return booelan
     */
    public boolean possuiNoEsquerdo() {
        return this.noEsquerdo != null;
    }

    /**
     * Retorna se possui nó filho a direita
     *
     * @return booelan
     */
    public boolean possuiNoDireito() {
        return this.noDireito != null;
    }

    /**
     * Retorna se o nó não possui filhos
     *
     * @return boolean
     */
    public boolean isFolha() {
        return this.noEsquerdo != null && this.noDireito != null;
    }

    /**
     * Executa o balanceamento do nó
     *
     * @return {@code No<T>}
     */
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

    /**
     * Insere e retorna o nó inserido
     *
     * @param no Nó a ser inserido
     * @return {@code No<T>}
     */
    public No<T> inserir(No<T> no) {
        // Se o nó já existe
        if (this.compareTo(no) == 0) {
            return null;
        }
        // Se o nó atual possui um valor menor
        if (this.compareTo(no) < 0) {
            // Se não existe nó a direita
            if (!this.possuiNoDireito()) {
                noDireito = no;
            } else {
                No<T> aux = noDireito.inserir(no);
                if (aux != null) {
                    noDireito = aux;
                }
            }
        } else if (this.compareTo(no) > 0) {
            // Se não possui nó esquerdo
            if (!this.possuiNoEsquerdo()) {
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

    /**
     * Remove e retorna umn nó
     *
     * @param no Nó a ser removido
     * @return {@code No<T>}
     */
    public No<T> remover(No<T> no) {
        // Se o nó atual possui valor maior
        if (this.compareTo(no) < 0) {
            No<T> aux = this.noDireito.remover(no);
            // Se o nó a ser removido é o nó direito
            if (aux == this.noDireito) {
                this.noDireito = null;
            } else if (aux != null) {
                this.noDireito = aux;
                this.noDireito.calculaAltura();
            }
        } else if (this.compareTo(no) > 0) {
            // Se o nó atual possui valor menor
            No<T> aux = this.noEsquerdo.remover(no);
            // Se o nó esquerdo deve ser removido
            if (aux == this.noEsquerdo) {
                this.noEsquerdo = null;
            } else if (aux != null) {
                this.noEsquerdo = aux;
                this.noEsquerdo.calculaAltura();
            }
            // Se o nó atual é o nó a ser removido
        } else if (this.compareTo(no) == 0) {
            // Se existe nó direito
            if (this.possuiNoDireito()) {
                // Se o nó direito não possui nó esquerdo
                if (!this.noDireito.possuiNoEsquerdo()) {
                    this.noDireito.setNoEsquerdo(this.noEsquerdo);
                    return this.noDireito;
                } else {
                    this.valor = (T) (this.noDireito.getNoEsquerdo()).getValor();
                    this.noDireito.remover(new No<T>(this.valor, this.chavePrimaria));
                }
            } else if (this.possuiNoEsquerdo()) {
                // Se possui nó esquerdo
                return this.noEsquerdo;
            } else {
                return this;
            }
        }
        return this.executaBalanceamento();
    }

    /**
     * Realiza a rotação a esquerda
     *
     * @return {@code No<T>}
     */
    private No<T> rotacaoEsquerda() {
        No<T> temp = noDireito;
        // Se o nó a direita não possui um nó esquerdo
        if (!noDireito.possuiNoEsquerdo()) {
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

    /**
     * Realiza a rotação a esquerda
     *
     * @return {@code No<T>}
     */
    private No<T> rotacaoDireita() {
        No<T> temp = noEsquerdo;
        // Se o nó direito não possui nó a esquerda
        if (!noEsquerdo.possuiNoDireito()) {
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
     * @param nome Nome a ser pesquisado
     * @param lista Lista a ser preenchida
     */
    public void buscaPorNome(String nome, List<Integer> lista) {
        // Se possui nó esquerdo
        if (possuiNoEsquerdo()) {
            noEsquerdo.buscaPorNome(nome, lista);
        }
        // Se o nome inicia com o valor pesquisado
        if (((String) this.valor).toLowerCase().startsWith(nome.toLowerCase())) {
            lista.add(this.indice);
        }
        // Se possui nó direito
        if (possuiNoDireito()) {
            noDireito.buscaPorNome(nome, lista);
        }
    }

    /**
     * Retorna o indice do nó caso o valor seja igual ao CPF informado
     *
     * @param cpf CPF
     * @return Integer
     */
    public Integer buscaPorCpf(Long cpf) {
        Long valorAux = Long.valueOf(this.valor.toString());
        // Se os valores de CPF forem iguais
        if (cpf.equals(valorAux)) {
            return indice;
        } else if (cpf < valorAux) {
            // Se possui nó a esquerda
            if (possuiNoEsquerdo()) {
                return noEsquerdo.buscaPorCpf(cpf);
            } else {
                return null;
            }
        } else if (cpf > valorAux) {
            // Se possui nó a direita
            if (possuiNoDireito()) {
                return noDireito.buscaPorCpf(cpf);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public Integer buscaPorChaveprimaria(No<T> no) {
        if (this.chavePrimaria.equals(no.getChavePrimaria())) {
            return this.indice;
        } else if (this.compareTo(no) > 0) {
            if (this.possuiNoEsquerdo()) {
                return this.noEsquerdo.buscaPorChaveprimaria(no);
            } else {
                return null;
            }
        } else if (this.compareTo(no) < 0) {
            if (this.possuiNoDireito()) {
                return this.noDireito.buscaPorChaveprimaria(no);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Realiza o percurso em pré-ordem
     *
     * @param lista Lista de indices
     */
    public void percursoPreOrdem(List<Integer> lista) {
        lista.add(this.indice);
        // Se possui nó esquerdo
        if (possuiNoEsquerdo()) {
            noEsquerdo.percursoPreOrdem(lista);
        }
        // Se possui nó direito
        if (possuiNoDireito()) {
            noDireito.percursoPreOrdem(lista);
        }
    }

    /**
     * Realiza o percurso em ordem
     *
     * @param lista Lista de indices
     */
    public void percursoEmOrdem(List<Integer> lista) {
        // Se possui nó esquerdo
        if (possuiNoEsquerdo()) {
            noEsquerdo.percursoEmOrdem(lista);
        }
        lista.add(this.indice);
        // Se possui nó direito
        if (possuiNoDireito()) {
            noDireito.percursoEmOrdem(lista);
        }
    }

    /**
     * Realiza o percurso em pós-ordem
     *
     * @param lista Lista de indices
     */
    public void percursoPosOrdem(List<Integer> lista) {
        // Se possui nó esquerdo
        if (possuiNoEsquerdo()) {
            noEsquerdo.percursoPosOrdem(lista);
        }
        // Se possui nó direito
        if (possuiNoDireito()) {
            noDireito.percursoPosOrdem(lista);
        }
        lista.add(this.indice);
    }

}
