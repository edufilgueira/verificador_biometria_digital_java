/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.seas.fingerprint.modelo.dao;

import br.gov.ce.seas.fingerprint.entidade.SubArea;
import br.gov.ce.seas.fingerprint.framework.dao.Conexao;
import br.gov.ce.seas.fingerprint.framework.dao.CreateDaoException;
import br.gov.ce.seas.fingerprint.framework.dao.UpdateDaoException;
import br.gov.ce.seas.http.ServicoHttp;
import br.gov.ce.seas.util.Ini;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

/**
 *
 * @author Eduardo
 */
public class SubAreaDao {
    
    private Conexao conexao = new Conexao();
    private ResultSet rs;
    private final ServicoHttp servicoHttp = new ServicoHttp();
    private final Ini ini = new Ini();
    
    public SubAreaDao () {
        conexao = new Conexao();
    }
    
    
    public void inserir(SubArea subArea) throws ParseException, ClassNotFoundException {       
        try {
            conexao.beginTransaction();
            Integer id = (int) conexao.executePreparedUpdateAndReturnGeneratedKeys( conexao.getConnectionFromContext()
                        , "insert into adm_ponto.subareas "
                        + "(id, nome, area_id, created_at, updated_at) "
                        + "values ( ?, ? , ? , '"+subArea.getCreated_at()+"', '"+subArea.getUpdated_at()+"' ) "
                        , subArea.getId()
                        , subArea.getNome()
                        , subArea.getArea_id()
            );
            subArea.setId( id );
            conexao.endTransaction();
        } catch (SQLException e) {
            conexao.rollbackTransaction();
            System.out.println("erro: "+e.getMessage());
            throw new CreateDaoException("Não foi possivel realizar a transação", e);
        } 
    }

    public void atualizar (SubArea subArea) throws ParseException, ClassNotFoundException {        
        final String query = "update adm_ponto.subareas set "
                + "nome = ?, area_id = ?, "
                + "created_at = '"+subArea.getCreated_at()+"', updated_at = '"+subArea.getUpdated_at()+"' "
                + "where id = ? ";
        try {
            conexao.beginTransaction();
            conexao.executePreparedUpdate(query
                        , subArea.getNome()
                        , subArea.getArea_id()
                        , subArea.getId()
            );
            conexao.endTransaction();
        } catch (SQLException e) {
            conexao.rollbackTransaction();
            throw new UpdateDaoException("Não foi possível atualizar Areas", e);
        }
    }
    
        
    public SubArea buscarPorId(int id) throws SQLException, ClassNotFoundException{
        Connection con = conexao.getConnection();
        PreparedStatement st;
        st = con.prepareStatement("SELECT * FROM adm_ponto.subareas "
                                    + "where id = "+id);
        rs = st.executeQuery();
        
        SubArea subArea = new SubArea();
        while (rs.next()) {
            subArea.setId(rs.getInt("id"));
            subArea.setNome(rs.getString("nome"));
            subArea.setArea_id(rs.getInt("area_id")); 
            subArea.setCreated_at(rs.getString("created_at"));
            subArea.setUpdated_at(rs.getString("updated_at")); 
        }
        
        if (subArea.getId() == null)
            return null;
        else
            return subArea;
    }
    
    public List<SubArea> sincronizarTodos() throws IOException, SQLException, CreateDaoException, ClassNotFoundException, ParseException{
        if(servicoHttp.getValidarUrl(ini.getValor("api_url_principal")))
        { 
            String url = ini.getValor("api_subareas_get");
            HttpURLConnection jsonRequest = (HttpURLConnection) new URL(url).openConnection();
            String jsonString = servicoHttp.readResponse(jsonRequest);

            if(jsonString == null)
                return null;

            // TRANSFORMA UM JSON EM LISTA DE OBJETO COLABORADOR
            Gson gson = new Gson();            
            java.lang.reflect.Type collectionType = new TypeToken<List<SubArea>>() {}.getType();

            List<SubArea> subAreas = gson.fromJson(jsonString, collectionType);

            // REALIZA A IMPORTAÇÃO
            for(int i = 0; i < subAreas.size(); i++){
                if(buscarPorId(subAreas.get(i).getId()) == null)
                    inserir(subAreas.get(i));
                else
                    atualizar(subAreas.get(i));
            }
            
            return subAreas;
        }
        return null;
    }   
}
