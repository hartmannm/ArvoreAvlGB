/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.unisinos.arvoreavlgb.arvore.utils;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 * Classe com rotinas gerais para interface gráfica
 *
 * @author Marcelo Augusto Gava
 * @author Mauricio Hartmann
 */
public class GuiUtils {

    /** Título para janela de alerta */
    private static final String TITULO_ALERT = "Atenção!!!";

    /**
     * Exibe janela de alerta
     * 
     * @param parentComponent Componente pai
     * @param mensagem Mensagem a ser exibida
     */
    public static void exibeAlerta(Component parentComponent, String mensagem) {
        JOptionPane.showMessageDialog(parentComponent, mensagem, TITULO_ALERT, 2);
    }

}
