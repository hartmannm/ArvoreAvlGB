package br.com.unisinos.arvoreavlgb.main;

import br.com.unisinos.arvoreavlgb.pessoa.Pessoa;
import java.text.SimpleDateFormat;
import java.util.List;

public class ArvorePrinter {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");

    public void printData(List<Pessoa> pessoas) {
        if (!pessoas.isEmpty()) {
            pessoas.forEach((pessoa) -> {
                printPessoa(pessoa);
            });
        } else {
            System.out.println("Nenhum resultado encontrado!!");
        }
    }

    public void printPessoa(Pessoa pessoa) {
        System.out.println(String.format("CPF: %s", pessoa.getCpf()));
        System.out.println(String.format("RG: %s", pessoa.getRg()));
        System.out.println(String.format("Nome: %s", pessoa.getNome()));
        System.out.println(String.format("Data de nascimento: %s",
                dateFormat.format(pessoa.getDataNascimento())));
        System.out.println(String.format("Cidade de nascimento: %s", pessoa.getCidadeNascimento()));
        System.out.println("-------------------------------------------------------------");
    }

}
