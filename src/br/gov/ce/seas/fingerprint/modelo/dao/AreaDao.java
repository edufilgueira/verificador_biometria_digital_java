/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.seas.fingerprint.modelo.dao;

import br.gov.ce.seas.fingerprint.entidade.Area;
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
public class AreaDao {
    private final Conexao conexao;
    private ResultSet rs;
    private final ServicoHttp servicoHttp = new ServicoHttp();
    private final Ini ini = new Ini();
	
    public AreaDao () {
        conexao = new Conexao();
    }

    public void inserir(Area area) throws ParseException, ClassNotFoundException {       
        try {
            conexao.beginTransaction();
            Integer id = (int) conexao.executePreparedUpdateAndReturnGeneratedKeys( conexao.getConnectionFromContext()
                        , "insert into adm_ponto.areas "
                        + "(id, nome, sigla, created_at, updated_at) "
                        + "values ( ?, ? , ? , '"+area.getCreated_at()+"' , '"+area.getUpdated_at()+"' ) "
                        , area.getId()
                        , area.getNome()
                        , area.getSigla()
            );
            area.setId( id );
            conexao.endTransaction();
        } catch (SQLException e) {
            conexao.rollbackTransaction();
            throw new CreateDaoException("Não foi possivel realizar a transação", e);
        } 
    }

    public void atualizar (Area area) throws ParseException, ClassNotFoundException, IOException {        
        final String query = "update adm_ponto.areas set nome = ?, sigla = ?, "
                + "created_at = '"+area.getCreated_at()+"', updated_at = '"+area.getUpdated_at()+"' "
                + "where id = ? ";
        try {
            conexao.beginTransaction();
            conexao.executePreparedUpdate(query
                        , area.getNome()
                        , area.getSigla()
                        , area.getId()
            );
            conexao.endTransaction();
        } catch (SQLException e) {
            conexao.rollbackTransaction();
            throw new UpdateDaoException("Não foi possível atualizar Areas", e);
        }
    }
    
        
    public Area buscarPorId(int id) throws SQLException, ClassNotFoundException{
        Connection con = conexao.getConnection();
        PreparedStatement st;
        st = con.prepareStatement("SELECT * FROM adm_ponto.areas "
                                    + "where id = "+id);
        rs = st.executeQuery();
        
        Area area = new Area();
        while (rs.next()) {
            area.setId(rs.getInt("id"));
            area.setNome(rs.getString("nome"));
            area.setSigla(rs.getString("sigla"));
            area.setCreated_at(rs.getString("created_at"));
            area.setUpdated_at(rs.getString("updated_at")); 
        }
        
        if (area.getId() == null)
            return null;
        else
            return area;
    }
    
    public List<Area> sincronizarTodos() throws IOException, SQLException, CreateDaoException, ClassNotFoundException, ParseException{
        if(servicoHttp.getValidarUrl(ini.getValor("api_url_principal")))
        { 
            String url = ini.getValor("api_areas_get");
            HttpURLConnection jsonRequest = (HttpURLConnection) new URL(url).openConnection();
            String jsonString = servicoHttp.readResponse(jsonRequest);

            if(jsonString == null)
                return null;

            // TRANSFORMA UM JSON EM LISTA DE OBJETO COLABORADOR
            Gson gson = new Gson();            
            java.lang.reflect.Type collectionType = new TypeToken<List<Area>>() {}.getType();
        
            List<Area> areas = gson.fromJson(jsonString, collectionType);

            // REALIZA A IMPORTAÇÃO
            for(int i = 0; i < areas.size(); i++){
                if(buscarPorId(areas.get(i).getId()) == null)
                    inserir(areas.get(i));
                else
                    atualizar(areas.get(i));
            }
            
            return areas;
        }
        return null;
    }
}
