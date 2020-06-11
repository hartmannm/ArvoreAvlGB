package br.com.unisinos.arvoreavlgb.main;

import br.com.unisinos.arvoreavlgb.arvore.ArvoreAvl;
import br.com.unisinos.arvoreavlgb.pessoa.Pessoa;
import br.com.unisinos.arvoreavlgb.pessoa.fileReader.PessoaFileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    private static Scanner scanner;
    
    private static final String CAMINHO_ARQUIVO = "C:/pessoas.csv";

    private static final String FORMATO_DATA = "dd/MM/yyyy";

    private static final String DIVISOR = "-----------------------------------\n";

    //Opções do menu
    private static String MENU;
    private static final int OPCAO_MENU_BUSCA_CPF = 1;
    private static final int OPCAO_MENU_BUSCA_NOME = 2;
    private static final int OPCAO_MENU_BUSCA_DATA = 3;
    private static final int OPCAO_MENU_PERCURSO_PRE_ORDEM = 4;
    private static final int OPCAO_MENU_PERCURSO_ORDEM = 5;
    private static final int OPCAO_MENU_PERCURSO_POS_ORDEM = 6;
    private static final int OPCAO_MENU_SAIR = 0;

    public static void main(String[] args) {
        try {
            // Lê o arquivo e cria uma lista de pessoas
            List<Pessoa> lista = PessoaFileReader.importPessoasArquivo(CAMINHO_ARQUIVO);
            // Cria as árvores
            ArvoreAvl arvoreCpf = new ArvoreAvl<Long>();
            ArvoreAvl arvoreNome = new ArvoreAvl<String>();
            ArvoreAvl arvoreDataNascimento = new ArvoreAvl<Date>();
            // Popula os dados nas árvores
            lista.forEach((pessoa) -> {
                Integer indice = lista.indexOf(pessoa);
                arvoreCpf.inserir(pessoa.getCpf(), indice);
                arvoreNome.inserir(pessoa.getNome(), indice);
                arvoreDataNascimento.inserir(pessoa.getDataNascimento(), indice);
            });
            // Cria objetos auxiliares
            boolean exibeMenu = true;
            scanner = new Scanner(System.in);
            ArvorePrinter printer = new ArvorePrinter();
            // Exibe o menu
            while (exibeMenu) {
                printMenu();
                int opcaoMenu = scanner.nextInt();
                switch (opcaoMenu) {
                    case OPCAO_MENU_BUSCA_CPF:
                        System.out.print(DIVISOR);
                        System.out.print("Digite o CPF que deseja buscar:\n");
                        String cpfMenu = scanner.next();
                        //Confere se CPF digitado é válido.
                        if (cpfMenu.length() < 11) {
                            System.out.println("CPF Inválido.");
                        }
                        Integer indice = arvoreCpf.buscaporCpf(Long.valueOf(cpfMenu));
                        if (indice != null) {
                            printer.printPessoa(lista.get(indice));
                        } else {
                            System.out.println("Nenhum resultado encontrado!!!");
                        }
                        break;
                    case OPCAO_MENU_BUSCA_NOME:
                        System.out.print(DIVISOR);
                        System.out.print("Digite o nome que deseja buscar:\n");
                        String nomeMenu = scanner.next();
                        List<Pessoa> resultado = getListaFiltrada(lista, arvoreNome.buscaPorNome(nomeMenu));
                        printer.printData(resultado);
                        break;
                    case OPCAO_MENU_BUSCA_DATA:
                        System.out.print(DIVISOR);
                        System.out.print("Digite o PERÍODO(data nasc.) que deseja buscar(dd/mm/aaaa):\n");
                        System.out.print("De ");
                        Date dataInicial = new SimpleDateFormat(FORMATO_DATA).parse(scanner.next());
                        System.out.print("Até ");
                        Date dataFinal = new SimpleDateFormat(FORMATO_DATA).parse(scanner.next());
                        List<Integer> listaIndices = arvoreDataNascimento.buscaPorData(dataInicial, dataFinal);
                        printer.printData(getListaFiltrada(lista, listaIndices));
                        break;
                    case OPCAO_MENU_PERCURSO_PRE_ORDEM:
                        printPercurso(OPCAO_MENU_PERCURSO_PRE_ORDEM);
                        break;
                    case OPCAO_MENU_PERCURSO_ORDEM:
                        printPercurso(OPCAO_MENU_PERCURSO_ORDEM);
                        break;
                    case OPCAO_MENU_PERCURSO_POS_ORDEM:
                        printPercurso(OPCAO_MENU_PERCURSO_POS_ORDEM);
                        break;
                    case 0:
                        System.out.println("OBRIGADO!");
                        exibeMenu = false;
                        break;
                    default:
                        System.out.println("Opção Inválida.");
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static void printMenu() {
        // Cria o menu caso ainda não tenha sido criado
        if (MENU == null) {
            StringBuilder menuBuilder = new StringBuilder();
            menuBuilder.append("Busca de Pessoas:\n");
            menuBuilder.append(String.format("%d - Busca por CPF:\n", OPCAO_MENU_BUSCA_CPF));
            menuBuilder.append(String.format("%d - Busca por nome:\n", OPCAO_MENU_BUSCA_NOME));
            menuBuilder.append(String.format("%d - Busca por data de nascimento:\n", OPCAO_MENU_BUSCA_DATA));
            menuBuilder.append(String.format("%d - Sair:\n", OPCAO_MENU_SAIR));
            MENU = menuBuilder.toString();
        }
        System.out.println("\n" + MENU);
    }

    private static void printPercurso(int opcaoPercurso) {
        List<Integer> listaPercurso;

    }

    private static List<Pessoa> getListaFiltrada(List<Pessoa> listToFilter, List<Integer> indicesList) {
        return indicesList.stream()
                .map(index -> listToFilter.get(index))
                .collect(Collectors.toList());
    }

}
