package br.gov.ce.seas.fingerverify;

import br.gov.ce.seas.http.ServicoHttp;
import br.gov.ce.seas.util.Ini;
import java.awt.EventQueue;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JFrame;


public class BrGovCeSeasFingerverify extends JFrame{
    
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException, Exception {

        EventQueue.invokeLater(() -> {
            try {
                Verification frame = new Verification();
                frame.setVisible(true);
                frame.iniciar();
                ServicoHttp.sincronizarDados();
            } catch (Exception e) {}
        });
                
        do{
            try { 
                Ini ini = new Ini();
                Thread thr1 = Thread.currentThread();  
                int sincroniza_pontos = Integer.parseInt(ini.getValor("sincroniza_pontos"));
                int tempo = 60000 * sincroniza_pontos;
                thr1.sleep(tempo); 
                ServicoHttp.sincronizarDados();
            } catch (InterruptedException ex) {}
        }while(1==1);
    }
    
}