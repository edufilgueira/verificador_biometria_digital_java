/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.seas.fingerprint.modelo.dao;

import br.gov.ce.seas.fingerprint.entidade.DiaGrade;
import br.gov.ce.seas.fingerprint.framework.dao.Conexao;
import br.gov.ce.seas.fingerprint.framework.dao.CreateDaoException;
import br.gov.ce.seas.fingerprint.framework.dao.UpdateDaoException;
import br.gov.ce.seas.http.ServicoHttp;
import br.gov.ce.seas.util.Ini;
import br.gov.ce.seas.util.Utilidade;
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
public class DiaGradeDao {
    
    private final Conexao conexao;
    private DiaGrade diaGrade = new DiaGrade();
    private ResultSet rs;
    private final ServicoHttp servicoHttp = new ServicoHttp();
    private final Ini ini = new Ini();
	
    public DiaGradeDao () {
        conexao = new Conexao();
    }
    
    public DiaGrade buscarDiaDaGrade(int id, int diaDaSemana) throws SQLException, ClassNotFoundException{
        Connection con = conexao.getConnection();
        diaGrade = new DiaGrade();
        PreparedStatement st;
        st = con.prepareStatement("SELECT * FROM adm_ponto.dia_grades "
                                    + "where grade_id='"+ id +"' AND dia = "+ diaDaSemana +" limit 1 ");
        rs = st.executeQuery();
        while (rs.next()) {
            diaGrade.setId(rs.getInt("id"));
            diaGrade.setDia(rs.getInt("dia"));
            diaGrade.setEntradaManha(rs.getString("entrada_manha"));
            diaGrade.setSaidaAlmoco(rs.getString("saida_almoco"));
            diaGrade.setEntradaAlmoco(rs.getString("entrada_almoco"));
            diaGrade.setSaidaTarde(rs.getString("saida_tarde"));
        }
        return diaGrade;
    }
    
    public void inserir(DiaGrade diaGrade) throws ParseException, ClassNotFoundException {       
        try {
            conexao.beginTransaction();

            String entrada_manha = Utilidade.converterDateEmTime(diaGrade.getEntradaManha());
            String saida_almoco = Utilidade.converterDateEmTime(diaGrade.getSaidaAlmoco());
            String entrada_almoco = Utilidade.converterDateEmTime(diaGrade.getEntradaAlmoco());
            String saida_tarde = Utilidade.converterDateEmTime(diaGrade.getSaidaTarde());
            
            Integer id = (int) conexao.executePreparedUpdateAndReturnGeneratedKeys( conexao.getConnectionFromContext()
                        , "insert into adm_ponto.dia_grades "
                        + "(id, dia, entrada_manha, saida_almoco, entrada_almoco, saida_tarde, grade_id, created_at, updated_at) "
                        + "values ( ?, ?, '"+entrada_manha+"', '"+saida_almoco+"', '"+entrada_almoco+"', '"+saida_tarde+"', ?, '"+diaGrade.getCreated_at()+"', '"+diaGrade.getUpdated_at()+"' ) "
                        , diaGrade.getId()
                        , diaGrade.getDia()
                        , diaGrade.getGradeId()
            );
            diaGrade.setId( id );
            conexao.endTransaction();
        } catch (SQLException e) {
            conexao.rollbackTransaction();
            throw new CreateDaoException("Não foi possivel realizar a transação", e);
        } 
    }
    
    public void atualizar (DiaGrade diaGrade) throws ParseException, ClassNotFoundException, IOException { 
        
        String entrada_manha = Utilidade.converterDateEmTime(diaGrade.getEntradaManha());
        String saida_almoco = Utilidade.converterDateEmTime(diaGrade.getSaidaAlmoco());
        String entrada_almoco = Utilidade.converterDateEmTime(diaGrade.getEntradaAlmoco());
        String saida_tarde = Utilidade.converterDateEmTime(diaGrade.getSaidaTarde());
            
        final String query = "update adm_ponto.dia_grades set dia = ?, "
                + "entrada_manha = '"+entrada_manha+"', saida_almoco = '"+saida_almoco+"', entrada_almoco = '"+entrada_almoco+"', saida_tarde = '"+saida_tarde+"', grade_id = ?, "
                + "created_at = '"+diaGrade.getCreated_at()+"', updated_at = '"+diaGrade.getUpdated_at()+"' "
                + "where id = ? ";
        try {
            conexao.beginTransaction();
            conexao.executePreparedUpdate(query
                        , diaGrade.getDia()
                        , diaGrade.getGradeId()
                        , diaGrade.getId()
            );
            conexao.endTransaction();
        } catch (SQLException e) {
            conexao.rollbackTransaction();
            throw new UpdateDaoException("Não foi possível atualizar Dia Grades", e);
        }
    }
    
        
    public DiaGrade buscarPorId(int id) throws SQLException, ClassNotFoundException{
        Connection con = conexao.getConnection();
        PreparedStatement st;
        st = con.prepareStatement("SELECT * FROM adm_ponto.dia_grades "
                                    + "where id = "+id);
        rs = st.executeQuery();
        
        DiaGrade diaGrade = new DiaGrade();
        while (rs.next()) {
            diaGrade.setId(rs.getInt("id"));
            diaGrade.setDia(rs.getInt("dia"));
            diaGrade.setEntradaManha(rs.getString("entrada_manha"));
            diaGrade.setSaidaAlmoco(rs.getString("saida_almoco"));
            diaGrade.setEntradaAlmoco(rs.getString("entrada_almoco"));
            diaGrade.setSaidaTarde(rs.getString("saida_tarde"));
            diaGrade.setGradeId(rs.getInt("grade_id"));
            diaGrade.setCreated_at(rs.getString("created_at"));
            diaGrade.setUpdated_at(rs.getString("updated_at")); 
        }
        
        if (diaGrade.getId() == null)
            return null;
        else
            return diaGrade;
    }
    
    public List<DiaGrade> sincronizarTodos() throws IOException, SQLException, CreateDaoException, ClassNotFoundException, ParseException{
        if(servicoHttp.getValidarUrl(ini.getValor("api_url_principal")))
        { 
            String url = ini.getValor("api_dia_grades_get");
            HttpURLConnection jsonRequest = (HttpURLConnection) new URL(url).openConnection();
            String jsonString = servicoHttp.readResponse(jsonRequest);

            if(jsonString == null)
                return null;

            // TRANSFORMA UM JSON EM LISTA DE OBJETO COLABORADOR
            Gson gson = new Gson();            
            java.lang.reflect.Type collectionType = new TypeToken<List<DiaGrade>>() {}.getType();
        
            List<DiaGrade> diaGrades = gson.fromJson(jsonString, collectionType);

            // REALIZA A IMPORTAÇÃO
            for(int i = 0; i < diaGrades.size(); i++){
                if(buscarPorId(diaGrades.get(i).getId()) == null)
                    inserir(diaGrades.get(i));
                else
                    atualizar(diaGrades.get(i));
            }
            return diaGrades;
        }
        return null;
    }
    
}