package br.com.unisinos.arvoreavlgb.arvore.gui.facade;

import br.com.unisinos.arvoreavlgb.arvore.gui.ArvoreConsultaForm;
import br.com.unisinos.arvoreavlgb.arvore.utils.GuiUtils;
import br.com.unisinos.arvoreavlgb.pessoa.Pessoa;
import br.com.unisinos.arvoreavlgb.pessoa.fileReader.PessoaFileReader;
import java.io.File;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Classe responsável pela execução de lógicas do componente de seleção de
 * arquivos
 *
 * @author Marcello Augusto Gava
 * @author Mauricio Hartmann
 */
public class FileChooseFormFacade {

    /**
     * Mensagem de erro - Formato de arquivo inválido
     */
    private static final String ERRO_FORMATO_ARQUIVO = "Por favor, selecione um arquivo no formato .csv";
    /**
     * Mensagem de erro - Falha ao abrir componente de seleção de arquivo
     */
    private static final String ERRO_ACESSO_RECURSOS = "Falha ao acessar recursos do sistema";
    /**
     * Extensão de arquivo .csv
     */
    private static final String EXTENSAO_CSV = "csv";

    /**
     * Retorna se o formato do arquivo é válido
     *
     * @param caminhoArquivo Caminho do arquivo
     * @return boolean
     */
    public boolean isFormatoValido(String caminhoArquivo) {
        return caminhoArquivo.endsWith(EXTENSAO_CSV);
    }

    /**
     * Retorna o caminho do arquivo a ser carregado
     *
     * @return String
     * @throws Exception Erro ao abrir componente de seleção de arquivos
     */
    public String getCaminhoArquivo() throws Exception {
        try {
            JFileChooser fileChooser = getFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            // Se o usuário selecionou um arquivo
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                // Se o formato do arquivo não é válido
                if (!isFormatoValido(selectedFile.getAbsolutePath())) {
                    GuiUtils.exibeAlerta(null, ERRO_FORMATO_ARQUIVO);
                } else {
                    return selectedFile.getAbsolutePath();
                }
            }
            return null;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Cria e exibe a janela de consulta de pessoas
     *
     * @param caminhoArquivo Caminho do arquivo a ser carregado
     */
    public void exibeJanelaConsulta(String caminhoArquivo) throws Exception {
        ArvoreConsultaForm formConsulta = new ArvoreConsultaForm();
        List<Pessoa> listaPessoas = PessoaFileReader.importPessoasArquivo(caminhoArquivo);
        formConsulta.setListaPessoas(listaPessoas);
        formConsulta.setVisible(true);
    }

    /**
     * Cria e retorna um componente de seleção de arquivos
     *
     * @return JFileChooser
     * @throws Exception Erro ao criar componente
     */
    private JFileChooser getFileChooser() throws Exception {
        try {
            // Configura e retorna o componente de seleção de arquivos
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter extensionFilter
                    = new FileNameExtensionFilter(EXTENSAO_CSV.toUpperCase(), EXTENSAO_CSV);
            fileChooser.setFileFilter(extensionFilter);
            return fileChooser;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException
                | UnsupportedLookAndFeelException e) {
            throw new Exception(ERRO_ACESSO_RECURSOS);
        }
    }

}
