package br.com.unisinos.arvoreavlgb.pessoa.fileReader;

import br.com.unisinos.arvoreavlgb.arvore.utils.DateUtils;
import br.com.unisinos.arvoreavlgb.pessoa.Pessoa;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por ler o arquivo de pessoas
 * 
 * @author Marcello Augusto Gava 
 * @author Mauricio Hartmann
 */
public class PessoaFileReader {

    /** Índice - CPF */
    private static final int CPF_INDEX = 0;
    /** Índice - RG */
    private static final int RG_INDEX = 1;
    /** Índice - Nome */
    private static final int NOME_INDEX = 2;
    /** Índice - Data de nascimento */
    private static final int DATA_NASC_INDEX = 3;
    /** Índice - Nome da cidade de nascimento */
    private static final int CIDADE_NASC_INDEX = 4;

    /**
     * Retorna uma lista de pessoas a partir de um arquivo
     *
     * @param caminhoArquivo Caminho do arquivo
     * @return {@code List<Pessoa>}
     * @throws Exception Erro ao abrir arquivo ou converter data de nascimento
     */
    public static List<Pessoa> importPessoasArquivo(String caminhoArquivo) throws Exception {
        List<Pessoa> lista = new ArrayList();
        String linha;
        // Abre o arquivo e inicia a leitura
        try (BufferedReader csvReader = new BufferedReader(new InputStreamReader(
                new FileInputStream(caminhoArquivo), "UTF-8"))) {
            // Lê as linhas do arquivo
            while ((linha = csvReader.readLine()) != null) {
                lista.add(parsePessoa(linha));
            }
        } catch (IOException e) {
            throw new Exception("Erro ao ler arquivo, verifique o caminho informado");
        } catch (ParseException e) {
            throw new Exception("Erro ao converter a data de nascimento, verifique os valores informados");
        }
        return lista;
    }

    /**
     * Converte os dados de uma linha em uma Pessoa
     *
     * @param linhaPessoa Linha a ser convertida
     * @return Pessoa Pessoa
     * @throws ParseException Erro ao fazer parse da data de nascimento
     */
    private static Pessoa parsePessoa(String linhaPessoa)
            throws ParseException {
        Pessoa pessoa = new Pessoa();
        // Divide a linha no caractere de ";"
        String[] pessoaArray = linhaPessoa.split(";");
        pessoa.setCpf(Long.valueOf(pessoaArray[CPF_INDEX]));
        pessoa.setRg(Long.valueOf(pessoaArray[RG_INDEX]));
        pessoa.setNome(pessoaArray[NOME_INDEX]);
        pessoa.setCidadeNascimento(pessoaArray[CIDADE_NASC_INDEX]);
        pessoa.setDataNascimento(DateUtils.getDataFromString(pessoaArray[DATA_NASC_INDEX]));
        return pessoa;
    }

}
