/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.seas.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Eduardo
 */
public class Ini {
    
    public String getValor(String valor) throws FileNotFoundException, IOException{
        Properties p = new Properties();
        //p.load(new FileInputStream("./config.ini"));
        InputStream in = this.getClass().getResourceAsStream("/config/config.ini");
        p.load(in);
        return p.getProperty(valor);
    }
    
}
