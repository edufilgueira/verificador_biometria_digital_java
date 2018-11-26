/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.gov.ce.seas.http;

import br.gov.ce.seas.fingerprint.modelo.dao.AreaDao;
import br.gov.ce.seas.fingerprint.modelo.dao.ColaboradorDao;
import br.gov.ce.seas.fingerprint.modelo.dao.GradeDao;
import br.gov.ce.seas.fingerprint.modelo.dao.DiaGradeDao;
import br.gov.ce.seas.fingerprint.modelo.dao.PontoDao;
import br.gov.ce.seas.fingerprint.modelo.dao.SubAreaDao;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;

/**
 *
 * @author marcelosiedler
 */
public class ServicoHttp {
 
    private final String USER_AGENT = "Mozilla/5.0";
    
    /**
     * Verifica se a url é válida
     * @param url
     * @return
     * @throws IOException 
     */
    public boolean getValidarUrl(String url) throws IOException {
        try{
            URL mandarMail = new URL(url);
            URLConnection conn = mandarMail.openConnection();
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.connect();
            int x = httpConn.getResponseCode();
            if(x == 200){
                return true;
            }
        }
        catch(java.net.MalformedURLException urlmal){
            return false;
        }
        catch(java.io.IOException ioexcp){
            return false;
        }
        return false;
    }
 
    // HTTP POST request
    public String enviarPost(String url, String json) throws MinhaException {
        try {
            // Cria um objeto HttpURLConnection:
            HttpURLConnection request = (HttpURLConnection) new URL(url).openConnection();
            try {
                // Define que a conexão pode enviar informações e obtê-las de volta:
                request.setDoOutput(true);
                request.setDoInput(true);
                // Define o content-type:
                request.setRequestProperty("Content-Type", "application/json");
                // Define o método da requisição:
                request.setRequestMethod("POST");
                // Conecta na URL:
                request.connect();
                // Escreve o objeto JSON usando o OutputStream da requisição:
                try (OutputStream outputStream = request.getOutputStream()) {
                    outputStream.write(json.getBytes("UTF-8"));
                }
                // Caso você queira usar o código HTTP para fazer alguma coisa, descomente esta linha.
                //int response = request.getResponseCode();
                return readResponse(request);
            } finally {
                request.disconnect();
            }
        } catch (IOException ex) {
            throw new MinhaException(ex);
        }
    }

    public String readResponse(HttpURLConnection request) throws IOException {
        ByteArrayOutputStream os;
        try (InputStream is = request.getInputStream()) {
            os = new ByteArrayOutputStream();
            int b;
            while ((b = is.read()) != -1) {
                os.write(b);
            }
        }
        return new String(os.toByteArray());
    }

    public static class MinhaException extends Exception {
        private static final long serialVersionUID = 1L;

        public MinhaException(Throwable cause) {
            super(cause);
        }
    }

     
    // HTTP GET request
    public String enviarGet(String url) throws Exception { 
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());
        return response.toString();
    }
    
    
    public static void sincronizarDados() throws IOException, SQLException, ClassNotFoundException, Exception{
        	
        PontoDao pontoDao = new PontoDao();
        ColaboradorDao colaboradorDao = new ColaboradorDao();
        AreaDao areaDao = new AreaDao();
        SubAreaDao subAreaDao = new SubAreaDao();
        GradeDao gradeDao = new GradeDao();
        DiaGradeDao diaGradeDao = new DiaGradeDao();
        
        areaDao.sincronizarTodos();
        subAreaDao.sincronizarTodos();
        gradeDao.sincronizarTodos();
        diaGradeDao.sincronizarTodos();
        colaboradorDao.sincronizarTodos();
        pontoDao.sincronizarTodos();
        
    }

}
