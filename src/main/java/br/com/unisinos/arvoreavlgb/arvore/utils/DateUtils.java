package br.com.unisinos.arvoreavlgb.arvore.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classes com rotinas gerais para manipulação de datas
 *
 * @author Marcelo Augusto Gava
 * @author Mauricio Hartmann
 */
public class DateUtils {

    /** Objeto responsável por formatar a data */
    private static final DateFormat formater = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Retorna uma data a partir de uma sting
     * 
     * @param dateString String com a data
     * @return Date
     * @throws ParseException Falha de conversão
     */
    public static Date getDataFromString(String dateString) throws ParseException {
        return formater.parse(dateString);
    }

    /**
     * Retorna uma string formatada a partir de uma data
     * 
     * @param date Data
     * @return String
     */
    public static String dateToString(Date date) {
        return formater.format(date);
    }

}
