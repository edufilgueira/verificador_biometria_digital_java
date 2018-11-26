package br.gov.ce.seas.fingerprint.modelo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import br.gov.ce.seas.fingerprint.entidade.Colaborador;
import br.gov.ce.seas.fingerprint.framework.dao.CreateDaoException;
import br.gov.ce.seas.fingerprint.framework.dao.Conexao;
import br.gov.ce.seas.fingerprint.framework.dao.QueryMapper;
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
import java.text.ParseException;

public class ColaboradorDao {
	
    private final Conexao conexao;
    private PreparedStatement st;
    private ResultSet rs;
    private final ServicoHttp servicoHttp = new ServicoHttp();
    private final Ini ini = new Ini();
	
    public ColaboradorDao () {
        conexao = new Conexao();
    }

    public void inserir(Colaborador colaborador) throws ParseException, ClassNotFoundException {       
        try {
            conexao.beginTransaction();
            Integer id = (int) conexao.executePreparedUpdateAndReturnGeneratedKeys( conexao.getConnectionFromContext()
                        , "insert into adm_ponto.colaboradores "
                        + "(id, nome, cpf, area_id, role, created_at, updated_at, grade_id, password_digest, email, ativo, data_desvinculacao, subarea_id) "
                        + "values ( ?, ? , ? , ? , ? , '"+colaborador.getCreated_at()+"' , '"+colaborador.getUpdated_at()+"' , ? , ? , ? , ? , ? , ? ) "
                        , colaborador.getId()
                        , colaborador.getNome()
                        , colaborador.getCpf()
                        , colaborador.getArea_id()
                        , colaborador.getRole()
                        , colaborador.getGrade_id()
                        , colaborador.getPassword_digest()
                        , colaborador.getEmail()
                        , colaborador.isAtivo()
                        , colaborador.getData_desvinculacao()
                        , colaborador.getSubarea_id()
            );
            colaborador.setId( id );
            conexao.endTransaction();
        } catch (SQLException e) {
            conexao.rollbackTransaction();	
            throw new CreateDaoException("Não foi possivel realizar a transação", e);
        } 
    }

    public void atualizar (Colaborador colaborador) throws ParseException, ClassNotFoundException {        
        final String query = "update adm_ponto.colaboradores set nome = ?, cpf = ?, area_id = ?, role = ?, "
                + "created_at = '"+colaborador.getCreated_at()+"', updated_at = '"+colaborador.getUpdated_at()+"', grade_id = ?, password_digest = ?, email = ?, ativo = ?, "
                + "data_desvinculacao = ?, subarea_id = ?"
                + "where id = ? ";
        try {
            conexao.beginTransaction();
            conexao.executePreparedUpdate(query
                        , colaborador.getNome()
                        , colaborador.getCpf()
                        , colaborador.getArea_id()
                        , colaborador.getRole()
                        , colaborador.getGrade_id()
                        , colaborador.getPassword_digest()
                        , colaborador.getEmail()
                        , colaborador.isAtivo()
                        , colaborador.getData_desvinculacao()
                        , colaborador.getSubarea_id() 
                        , colaborador.getId()
            );
            conexao.endTransaction();
        } catch (SQLException e) {
            conexao.rollbackTransaction();
            throw new UpdateDaoException("Não foi possível atualizar Paciente", e);
        }
    }
    
        
    public Colaborador buscarPorId(int id) throws SQLException, ClassNotFoundException{
        Connection con = conexao.getConnection();
        PreparedStatement st;
        st = con.prepareStatement("SELECT * FROM adm_ponto.colaboradores "
                                    + "where id = "+id);
        rs = st.executeQuery();
        
        Colaborador colaborador = new Colaborador();
        while (rs.next()) {
            colaborador.setId(rs.getInt("id"));
            colaborador.setNome(rs.getString("nome"));
            colaborador.setCpf(rs.getString("cpf"));
            colaborador.setArea_id(rs.getInt("area_id"));
            colaborador.setRole(rs.getString("role"));
            colaborador.setCreated_at(rs.getString("created_at"));
            colaborador.setUpdated_at(rs.getString("updated_at"));
            colaborador.setGrade_id(rs.getInt("grade_id"));
            colaborador.setPassword_digest(rs.getString("password_digest"));
            colaborador.setEmail(rs.getString("email"));
            colaborador.setAtivo(rs.getBoolean("email"));
            colaborador.setData_desvinculacao(rs.getString("data_desvinculacao"));
            colaborador.setSubarea_id(rs.getInt("subarea_id")); 
        }
        
        if (colaborador.getId() == null)
            return null;
        else
            return colaborador;
    }

    public PreparedStatement listarColaboradoresComDigitaisAtivos() throws SQLException, ClassNotFoundException, IOException{
        //Conexao conexao = new Conexao();
        Connection con = conexao.getConnection();
        String area = ini.getValor("area");
        st = con.prepareStatement("SELECT * FROM adm_ponto.colaboradores "
                                + "WHERE digitaldireita IS NOT null AND digitalesquerda IS NOT null "
                                + "AND ativo = true AND area_id = '"+area+"'");
        return st;
    }

    public List<Colaborador> listaTodosColaboradores() throws ClassNotFoundException, IOException {
        final List<Colaborador> colaboradores = new ArrayList<Colaborador>();
        try {
            conexao.executePreparedQuery("select * from adm_ponto.colaboradores", new QueryMapper<Colaborador>() {
                @Override
                public List<Colaborador> mapping(ResultSet rset) throws SQLException {
                    while (rset.next()) {
                        Colaborador colaborador = new Colaborador();
                        colaborador.setId( rset.getInt("id") );
                        colaborador.setNome( rset.getString("nome") );
                        colaborador.setDigitalDireita( rset.getBytes("digitaldireita") );
                        colaborador.setDigitalEsquerda( rset.getBytes("digitalesquerda") );
                        //paciente.setRg( rset.getString("rg") );
                        //paciente.setSexo( SexoType.valueOf( rset.getString("sexo") ) );
                        colaboradores.add(colaborador);
                    }
                    return colaboradores;
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
                //ignore exception
        }
        return colaboradores;
    }
        
    public List<Colaborador> sincronizarTodos() throws IOException, SQLException, CreateDaoException, ClassNotFoundException, ParseException{
        if(servicoHttp.getValidarUrl(ini.getValor("api_url_principal")))
        { 
            String url = ini.getValor("api_colaboradores_get");
            HttpURLConnection jsonRequest = (HttpURLConnection) new URL(url).openConnection();
            String jsonString = servicoHttp.readResponse(jsonRequest);

            if(jsonString == null)
                return null;

            // TRANSFORMA UM JSON EM LISTA DE OBJETO COLABORADOR
            Gson gson = new Gson();            
            java.lang.reflect.Type collectionType = new TypeToken<List<Colaborador>>() {}.getType();
        
            List<Colaborador> colaboradores = gson.fromJson(jsonString, collectionType);

            // REALIZA A IMPORTAÇÃO
            for(int i = 0; i < colaboradores.size(); i++){
                if(buscarPorId(colaboradores.get(i).getId()) == null)
                    inserir(colaboradores.get(i));
                else
                    atualizar(colaboradores.get(i));
            }
            
            return colaboradores;
        }
        return null;
    }
	
}
