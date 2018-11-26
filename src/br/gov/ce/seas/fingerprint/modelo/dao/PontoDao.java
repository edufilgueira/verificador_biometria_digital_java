/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.seas.fingerprint.modelo.dao;

import br.gov.ce.seas.util.Utilidade;
import br.gov.ce.seas.fingerprint.entidade.DiaGrade;
import br.gov.ce.seas.fingerprint.entidade.Grade;
import br.gov.ce.seas.fingerprint.entidade.Ponto;
import br.gov.ce.seas.fingerprint.framework.dao.Conexao;
import br.gov.ce.seas.fingerprint.framework.dao.CreateDaoException;
import br.gov.ce.seas.http.ServicoHttp;
import br.gov.ce.seas.util.Ini;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Eduardo
 */
public class PontoDao {
    
    private final Conexao conexao;
    private ResultSet rs;
    private Grade grade;
    private DiaGrade diaGrade = new DiaGrade();
    private final DiaGradeDao diaGradeDao = new DiaGradeDao();
    private final ServicoHttp servicoHttp = new ServicoHttp();
    private final GradeDao gradeDao = new GradeDao();
    private final Ini ini = new Ini();
    
    public PontoDao () {
        conexao = new Conexao();
    }
    
    public String marcarPonto(int idColaborador) throws ClassNotFoundException, SQLException, ParseException, Exception {
        Date ultimo_ponto = null;
        Date novo_ponto = new Date();
        
        //LISTA A DATA DO ULTIMO PONTO DO COLABORADOR NA DATA ATUAL
        ultimo_ponto = listaUltimoPontoMarcadoDoColaborador(idColaborador);
        
        //CAPTURA A GRADE DO COLABORADOR
        grade = gradeDao.verificarRestricaoNaGrade(idColaborador);
        
        //VERIFICA SE TEM TOLERANCIA NO PERÍODO
        grade = VerificarToleranciaPeriodo(ultimo_ponto, novo_ponto, grade);

        /**
         * TODO: IMPLEMENTAR COLABORADOR DE FÉRIAS
         */

        //RESTRINGE A TOLERANCIA DE UM PERIODO (MANHA OU TARDE)
        if(grade.getRestringirPeriodo())
            if(grade.getStatusRestringirPeriodo())
                return "Fora do período para registro";
        
        //RESTRINGE TOLERANCIA NOS HORÁRIO DE BATIDA
        if(grade.getRestringirHorarios())
        {
            //VERIFICA SE EXISTE ULTIMO_PONTO FOI MARCADO NO INTERVALO DOS DIA_GRADES
            if(ultimo_ponto != null)
                if(bateuPontoMaisDeUmaVez(ultimo_ponto, grade))
                    return "Ponto ja foi marcado.";
            //VERIFICA SE O NOVO_PONTO ESTA DENTRO DO INTERVALO DOS DIA_GRADES
            if(VerificarToleranciaBatida(novo_ponto, grade))
                return "Fora da tolerância de "+grade.getTolerancia()+"min.";
        }
        
        Ponto ponto = new Ponto();
        ponto.setColaboradorId(idColaborador);
        ponto.setData(novo_ponto);
        inserir(ponto);
        return "OK";
    }    
    
    private Date listaUltimoPontoMarcadoDoColaborador(int id) throws SQLException, ClassNotFoundException{
        Connection con = conexao.getConnection();
        Date ultimo_ponto = null;
        PreparedStatement st;
        st = con.prepareStatement("SELECT data FROM adm_ponto.pontos "
                                    + "where colaborador_id = '"+id+"' "
                                    + "and data > CURRENT_DATE "
                                    + "order by data desc limit 1 ");
        rs = st.executeQuery();
        
        while (rs.next()) {
            ultimo_ponto = rs.getTimestamp("data");
        }
        return ultimo_ponto;
    }
    
    private Grade VerificarToleranciaPeriodo(Date ultimo_ponto, Date novo_ponto, Grade grade){
        if(ultimo_ponto != null)
        {            
            //VERIFICA SE HORA TEM DIFERENÇA DE 60MIM PARA MARCAR NOVAMENTE=====
            long horas = (novo_ponto.getTime() - ultimo_ponto.getTime()) / 3600000;
            long minutos = (novo_ponto.getTime() - ultimo_ponto.getTime() - 36000) / 60000;
            
            if(minutos > grade.getToleranciaPeriodo())
                grade.setStatusRestringirPeriodo(true);
            else
                grade.setStatusRestringirPeriodo(false);
        }
        else 
            grade.setStatusRestringirPeriodo(false);
        
        return grade;
    }
    
    private boolean VerificarToleranciaBatida(Date data, Grade grade) throws SQLException, ParseException, ClassNotFoundException{        
        int diaDaSemana = Utilidade.diaDaSemana(data);
        boolean temRestricaoNaToleranciaDaBetida;
        diaGrade = new DiaGrade();
        diaGrade = diaGradeDao.buscarDiaDaGrade(grade.getId(), diaDaSemana);

        //CAPTURA DAS 4 MARCAÇÕES PADRÃO DA GRADE
        Calendar DataAtual_EntradaManhaAdd = Utilidade.somarMinutosNumaData(data, diaGrade.getEntradaManha(), grade.getTolerancia(), 1);
        Calendar DataAtual_EntradaManhaDec = Utilidade.somarMinutosNumaData(data, diaGrade.getEntradaManha(), grade.getTolerancia(), -1);
        
        Calendar DataAtual_SaidaAlmocoAdd = Utilidade.somarMinutosNumaData(data, diaGrade.getSaidaAlmoco(), grade.getTolerancia(), 1);
        Calendar DataAtual_SaidaAlmocoDec = Utilidade.somarMinutosNumaData(data, diaGrade.getSaidaAlmoco(), grade.getTolerancia(), -1);

        Calendar DataAtual_EntradaAlmocoAdd = Utilidade.somarMinutosNumaData(data, diaGrade.getEntradaAlmoco(), grade.getTolerancia(), 1);
        Calendar DataAtual_EntradaAlmocoDec = Utilidade.somarMinutosNumaData(data, diaGrade.getEntradaAlmoco(), grade.getTolerancia(), -1);

        Calendar DataAtual_SaidaTardeAdd = Utilidade.somarMinutosNumaData(data, diaGrade.getSaidaTarde(), grade.getTolerancia(), 1);
        Calendar DataAtual_SaidaTardeDec = Utilidade.somarMinutosNumaData(data, diaGrade.getSaidaTarde(), grade.getTolerancia(), -1);

        temRestricaoNaToleranciaDaBetida = true;
        if(data.after(DataAtual_EntradaManhaDec.getTime()) && data.before(DataAtual_EntradaManhaAdd.getTime())) {
            temRestricaoNaToleranciaDaBetida = false;
        }
        else if(data.after(DataAtual_SaidaAlmocoDec.getTime()) && data.before(DataAtual_SaidaAlmocoAdd.getTime())){
            temRestricaoNaToleranciaDaBetida = false;
        }
        else if(data.after(DataAtual_EntradaAlmocoDec.getTime()) && data.before(DataAtual_EntradaAlmocoAdd.getTime())){
            temRestricaoNaToleranciaDaBetida = false;
        }
        else if(data.after(DataAtual_SaidaTardeDec.getTime()) && data.before(DataAtual_SaidaTardeAdd.getTime())){
            temRestricaoNaToleranciaDaBetida = false;
        }
        
        return temRestricaoNaToleranciaDaBetida;
    }
    
    private boolean bateuPontoMaisDeUmaVez(Date ultima_data, Grade grade) throws SQLException, ParseException, ClassNotFoundException{        
        int diaDaSemana = Utilidade.diaDaSemana(ultima_data);
        boolean bateuPontoMaisDeUmaVez;
        diaGrade = new DiaGrade();
        diaGrade = diaGradeDao.buscarDiaDaGrade(grade.getId(), diaDaSemana);
        Date data_atual = new Date();
        
        //CAPTURA DAS 4 MARCAÇÕES PADRÃO DA GRADE
        //Calendar DataAtual_Add = Utilidade.somarMinutosNumaData(data_atual, grade.getTolerancia(), 1);
        //Calendar DataAtual_Dec = Utilidade.somarMinutosNumaData(data_atual, grade.getTolerancia(), -1);
        
        Calendar DataAtual_EntradaManhaAdd = Utilidade.somarMinutosNumaData(data_atual, diaGrade.getEntradaManha(), grade.getTolerancia(), 1);
        Calendar DataAtual_EntradaManhaDec = Utilidade.somarMinutosNumaData(data_atual, diaGrade.getEntradaManha(), grade.getTolerancia(), -1);
        
        Calendar DataAtual_SaidaAlmocoAdd = Utilidade.somarMinutosNumaData(data_atual, diaGrade.getSaidaAlmoco(), grade.getTolerancia(), 1);
        Calendar DataAtual_SaidaAlmocoDec = Utilidade.somarMinutosNumaData(data_atual, diaGrade.getSaidaAlmoco(), grade.getTolerancia(), -1);

        Calendar DataAtual_EntradaAlmocoAdd = Utilidade.somarMinutosNumaData(data_atual, diaGrade.getEntradaAlmoco(), grade.getTolerancia(), 1);
        Calendar DataAtual_EntradaAlmocoDec = Utilidade.somarMinutosNumaData(data_atual, diaGrade.getEntradaAlmoco(), grade.getTolerancia(), -1);

        Calendar DataAtual_SaidaTardeAdd = Utilidade.somarMinutosNumaData(data_atual, diaGrade.getSaidaTarde(), grade.getTolerancia(), 1);
        Calendar DataAtual_SaidaTardeDec = Utilidade.somarMinutosNumaData(data_atual, diaGrade.getSaidaTarde(), grade.getTolerancia(), -1);

        bateuPontoMaisDeUmaVez = false;
        if(data_atual.after(DataAtual_EntradaManhaDec.getTime()) && data_atual.before(DataAtual_EntradaManhaAdd.getTime())) {
            if(ultima_data.after(DataAtual_EntradaManhaDec.getTime()) && data_atual.before(DataAtual_EntradaManhaAdd.getTime()))
                bateuPontoMaisDeUmaVez = true;
        }
        else if(data_atual.after(DataAtual_SaidaAlmocoDec.getTime()) && data_atual.before(DataAtual_SaidaAlmocoAdd.getTime())){
            if(ultima_data.after(DataAtual_SaidaAlmocoDec.getTime()) && data_atual.before(DataAtual_SaidaAlmocoAdd.getTime()))
                bateuPontoMaisDeUmaVez = true;
        }
        else if(data_atual.after(DataAtual_EntradaAlmocoDec.getTime()) && data_atual.before(DataAtual_EntradaAlmocoAdd.getTime())){
            if(ultima_data.after(DataAtual_EntradaAlmocoDec.getTime()) && data_atual.before(DataAtual_EntradaAlmocoAdd.getTime()))
                bateuPontoMaisDeUmaVez = true;
        }
        else if(data_atual.after(DataAtual_SaidaTardeDec.getTime()) && data_atual.before(DataAtual_SaidaTardeAdd.getTime())){
            if(ultima_data.after(DataAtual_SaidaTardeDec.getTime()) && data_atual.before(DataAtual_SaidaTardeAdd.getTime()))
                bateuPontoMaisDeUmaVez = true;
        }
        return bateuPontoMaisDeUmaVez;
    }
    
    public void inserir(Ponto ponto) throws SQLException, ClassNotFoundException, Exception {
        DateFormat FormatoDtAtualSemHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String stringDataAtual = FormatoDtAtualSemHora.format(ponto.getData());
        
        try {
            conexao.beginTransaction();
            Integer id = (int) conexao.executePreparedUpdateAndReturnGeneratedKeys( conexao.getConnectionFromContext()
                        , "insert into adm_ponto.pontos (colaborador_id, data, created_at, updated_at) values ( ?, '"+stringDataAtual+"', now(), now() )"
                        , ponto.getColaboradorId());
            ponto.setId( id );
            System.out.printf("ID: "+id);
            conexao.endTransaction();
            
            //SE ATUALIZAR A API REALIZA UPDATE NO PONTO LOCAL
            if(exportarBatidaParaPontoApi(ponto.getColaboradorId(), stringDataAtual))
                atualizarPontoLocalAposMigracao(id);
            
        } catch (SQLException e) {
                conexao.rollbackTransaction();	
                throw new CreateDaoException("Não foi possivel realizar a transação", e);
        } 
    }
    
    public void sincronizarTodos() throws IOException, SQLException, ClassNotFoundException, Exception{
        if(servicoHttp.getValidarUrl(ini.getValor("api_url_principal")))
        {
            Connection con = conexao.getConnection();
            PreparedStatement st;
            st = con.prepareStatement("SELECT * FROM adm_ponto.pontos "
                                        + "where migrado != true OR migrado is null "
                                        + "order by id");
            rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int colaboradorId = rs.getInt("colaborador_id");
                String data = rs.getString("data");
                System.out.println(data);
                if(exportarBatidaParaPontoApi(colaboradorId, data))
                    atualizarPontoLocalAposMigracao(id);
            }
        }
    }

    public boolean exportarBatidaParaPontoApi(int colaboradorId, String data) throws IOException, Exception{
        if(servicoHttp.getValidarUrl(ini.getValor("api_url_principal")))
        {
            String url = ini.getValor("api_ponto_post");
            String jsonPost = "{\"colaborador_id\":\""+colaboradorId+"\",\"data\":\""+data+"\"}";
            servicoHttp.enviarPost(url, jsonPost);
            return true;
        }
        return false;
    }
        
    private void atualizarPontoLocalAposMigracao(int pontoId) throws SQLException, ClassNotFoundException{
        Connection con = conexao.getConnection();
        PreparedStatement st;
        st = con.prepareStatement("UPDATE adm_ponto.pontos SET migrado = 'true' "
                                    + "where id = '"+pontoId+"' ");
        st.execute();
    }
   
}
