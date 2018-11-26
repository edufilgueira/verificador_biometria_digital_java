/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.seas.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Eduardo
 */
public class Utilidade {
    
    public static String addZerosEsquerda(int casas, String texto){
        while (texto.length() < casas){
            texto = "0" + texto;
        }
        return texto;
    }
    
    public static int diaDaSemana(Date data){
	Calendar c = new GregorianCalendar();
        c.setTime(data);
        int numero = 0;
        int dia = c.get(Calendar.DAY_OF_WEEK);
        switch(dia){
          case Calendar.SUNDAY: numero = 6;break;//"Domingo"
          case Calendar.MONDAY: numero = 0;break;//"Segunda"
          case Calendar.TUESDAY: numero = 1;break;//"Terça"
          case Calendar.WEDNESDAY: numero = 2;break;//"Quarta"
          case Calendar.THURSDAY: numero = 3;break;//"Quinta"
          case Calendar.FRIDAY: numero = 4;break;//"Sexta"
          case Calendar.SATURDAY: numero = 5;break;//"sábado"
        }
        return numero;
    }
    
    public static String converterDateEmTime(String datatime) throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date data = format.parse(datatime.replace("T", " "));

        SimpleDateFormat form = new SimpleDateFormat("HH:mm:ss");
        String time = form.format(data);
        
        return time;
    }
    
    public static Calendar somarMinutosNumaData(Date data, String hora_grade, int tolerancia, int operador) throws ParseException{
        //FORMATA DATA ATUAL SEM HORA
        DateFormat FormatoDtAtualSemHora = new SimpleDateFormat("yyyy-MM-dd");
        String stringDataAtual = FormatoDtAtualSemHora.format(data);
        //FORMATA DATA ATUAL COM A HORA DA 'DIA_GRADES'
        DateFormat FormatoDtAtualComHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        //CAPTURA DAS 4 MARCAÇÕES PADRÃO DA GRADE
        Calendar DataAtual_EntradaManhaAdd = Calendar.getInstance();
        DataAtual_EntradaManhaAdd.setTime(FormatoDtAtualComHora.parse(stringDataAtual + " " + hora_grade));
        DataAtual_EntradaManhaAdd.add(Calendar.MINUTE, operador * tolerancia);
        
        return DataAtual_EntradaManhaAdd;
    }
    
    public static Calendar somarMinutosNumaData(Date data, int tolerancia, int operador) throws ParseException{
        //FORMATA DATA ATUAL SEM HORA
        DateFormat FormatoDt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String stringDataAtual = FormatoDt.format(data);
        
        //CAPTURA DAS 4 MARCAÇÕES PADRÃO DA GRADE
        Calendar DataAtual_EntradaManhaAdd = Calendar.getInstance();
        DataAtual_EntradaManhaAdd.setTime(FormatoDt.parse(stringDataAtual));
        DataAtual_EntradaManhaAdd.add(Calendar.MINUTE, operador * tolerancia);
        
        return DataAtual_EntradaManhaAdd;
    }
}
