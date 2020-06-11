package br.com.unisinos.arvoreavlgb.main;

import br.com.unisinos.arvoreavlgb.arvore.ArvoreAvl;
import br.com.unisinos.arvoreavlgb.pessoa.Pessoa;
import br.com.unisinos.arvoreavlgb.pessoa.fileReader.PessoaFileReader;
import java.util.Date;
import java.util.List;

public class Main {

    private static final String CAMINHO_ARQUIVO = "C:/pessoas.csv";

    public static void main(String[] args) {
        try {
            List<Pessoa> lista = PessoaFileReader.importPessoasArquivo(CAMINHO_ARQUIVO);

            ArvoreAvl arvoreCpf = new ArvoreAvl<Long>();
            ArvoreAvl arvoreNome = new ArvoreAvl<String>();
            ArvoreAvl arvoreDataNascimento = new ArvoreAvl<Date>();

            lista.forEach((pessoa) -> {
                Integer indice = lista.indexOf(pessoa);
                arvoreCpf.inserir(pessoa.getCpf(), indice);
                arvoreNome.inserir(pessoa.getNome(), indice);
                arvoreDataNascimento.inserir(pessoa.getDataNascimento(), indice);
            });
            
            arvoreNome.percursoEmOrdem();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
