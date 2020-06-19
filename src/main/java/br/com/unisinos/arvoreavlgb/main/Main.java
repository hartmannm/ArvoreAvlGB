package br.com.unisinos.arvoreavlgb.main;

import br.com.unisinos.arvoreavlgb.arvore.gui.FileChooserForm;

/**
 * Classe responsável por executar a aplicação
 *
 * @author Marcelo Augusto Gava
 * @author Mauricio Hartmann
 */
public class Main {

    /**
     * Executa a aplicação
     * 
     * @param args Argumentos
     */
    public static void main(String[] args) {
        try {
            FileChooserForm fileChooseWindow = new FileChooserForm();
            fileChooseWindow.setVisible(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
