/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.seas.fingerprint.modelo.dao;

import br.gov.ce.seas.fingerprint.entidade.Area;
import br.gov.ce.seas.fingerprint.entidade.Colaborador;
import br.gov.ce.seas.fingerprint.entidade.Grade;
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
public class GradeDao {
    
    private Conexao conexao = new Conexao();
    private Grade grade;
    private final ColaboradorDao colaboradorDao = new ColaboradorDao();
    private ResultSet rs;
    private final ServicoHttp servicoHttp = new ServicoHttp();
    private final Ini ini = new Ini();
    
    public GradeDao () {
        conexao = new Conexao();
    }
    
    public Grade verificarRestricaoNaGrade(int idColaborador) throws SQLException, ClassNotFoundException{
        Connection con = conexao.getConnection();
        int grade_colaborador;
        PreparedStatement st;
        grade = new Grade();
        grade_colaborador = colaboradorDao.buscarPorId(idColaborador).getGrade_id();
        
        //RETORNA A GRADE DE UM COLABORADOR=================================
        st = con.prepareStatement("SELECT * FROM adm_ponto.grades "
                                    + "where id = "+grade_colaborador);
        rs = st.executeQuery();
        while (rs.next()) {
            grade.setId(rs.getInt("id"));
            grade.setNome(rs.getString("nome"));
            grade.setHorasDiarias(rs.getInt("horas_diarias"));
            grade.setRestringirHorarios(rs.getBoolean("restringir_horarios")); 
            grade.setTolerancia(rs.getInt("tolerancia"));
            grade.setToleranciaPeriodo(rs.getInt("tolerancia_periodo"));
            grade.setRestringirPeriodo(rs.getBoolean("restringir_periodo"));
        }
        return grade;
    }
    
    public void inserir(Grade grade) throws ParseException, ClassNotFoundException {       
        try {
            conexao.beginTransaction();
            Integer id = (int) conexao.executePreparedUpdateAndReturnGeneratedKeys( conexao.getConnectionFromContext()
                        , "insert into adm_ponto.grades "
                        + "(id, nome, tolerancia, horas_diarias, restringir_horarios, tolerancia_periodo, restringir_periodo, created_at, updated_at) "
                        + "values ( ?, ? , ? , ?, ?, ?, ?, '"+grade.getCreated_at()+"' , '"+grade.getUpdated_at()+"' ) "
                        , grade.getId()
                        , grade.getNome()
                        , grade.getTolerancia()
                        , grade.getHorasDiarias()
                        , grade.getRestringirHorarios()
                        , grade.getToleranciaPeriodo()
                        , grade.getRestringirPeriodo()
            );
            grade.setId( id );
            conexao.endTransaction();
        } catch (SQLException e) {
            conexao.rollbackTransaction();	
            throw new CreateDaoException("Não foi possivel realizar a transação", e);
        } 
    }

    public void atualizar (Grade grade) throws ParseException, ClassNotFoundException {        
        final String query = "update adm_ponto.grades set "
                + "nome = ?, tolerancia = ?, horas_diarias = ?, "
                + "restringir_horarios = ?, tolerancia_periodo = ?, restringir_periodo = ?, "
                + "created_at = '"+grade.getCreated_at()+"', updated_at = '"+grade.getUpdated_at()+"' "
                + "where id = ? ";
        try {
            conexao.beginTransaction();
            conexao.executePreparedUpdate(query
                        , grade.getNome()
                        , grade.getTolerancia()
                        , grade.getHorasDiarias()
                        , grade.getRestringirHorarios()
                        , grade.getToleranciaPeriodo()
                        , grade.getRestringirPeriodo()
                        , grade.getId()
            );
            conexao.endTransaction();
        } catch (SQLException e) {
            conexao.rollbackTransaction();
            throw new UpdateDaoException("Não foi possível atualizar Areas", e);
        }
    }
    
        
    public Grade buscarPorId(int id) throws SQLException, ClassNotFoundException{
        Connection con = conexao.getConnection();
        PreparedStatement st;
        st = con.prepareStatement("SELECT * FROM adm_ponto.grades "
                                    + "where id = "+id);
        rs = st.executeQuery();
        
        Grade grade = new Grade();
        while (rs.next()) {
            grade.setId(rs.getInt("id"));
            grade.setNome(rs.getString("nome"));
            grade.setTolerancia(rs.getInt("tolerancia"));
            grade.setHorasDiarias(rs.getInt("horas_diarias"));
            grade.setRestringirHorarios(rs.getBoolean("restringir_horarios"));
            grade.setToleranciaPeriodo(rs.getInt("tolerancia_periodo"));
            grade.setRestringirPeriodo(rs.getBoolean("restringir_periodo")); 
            grade.setCreated_at(rs.getString("created_at"));
            grade.setUpdated_at(rs.getString("updated_at")); 
        }
        
        if (grade.getId() == null)
            return null;
        else
            return grade;
    }
    
    public List<Grade> sincronizarTodos() throws IOException, SQLException, CreateDaoException, ClassNotFoundException, ParseException{
        if(servicoHttp.getValidarUrl(ini.getValor("api_url_principal")))
        { 
            String url = ini.getValor("api_grades_get");
            HttpURLConnection jsonRequest = (HttpURLConnection) new URL(url).openConnection();
            String jsonString = servicoHttp.readResponse(jsonRequest);

            if(jsonString == null)
                return null;

            // TRANSFORMA UM JSON EM LISTA DE OBJETO COLABORADOR
            Gson gson = new Gson();            
            java.lang.reflect.Type collectionType = new TypeToken<List<Grade>>() {}.getType();
        
            List<Grade> grades = gson.fromJson(jsonString, collectionType);

            // REALIZA A IMPORTAÇÃO
            for(int i = 0; i < grades.size(); i++){
                if(buscarPorId(grades.get(i).getId()) == null)
                    inserir(grades.get(i));
                else
                    atualizar(grades.get(i));
            }
            
            return grades;
        }
        return null;
    }
    
}
