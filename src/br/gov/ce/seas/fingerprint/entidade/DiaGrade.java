/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.seas.fingerprint.entidade;

/**
 *
 * @author Eduardo
 */
public class DiaGrade {
    private Integer id;
    private Integer grade_id;
    private Integer dia;
    private String entrada_manha;
    private String saida_almoco;
    private String entrada_almoco;
    private String saida_tarde;
    private String created_at;
    private String updated_at;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getGradeId() {
        return grade_id;
    }
    public void setGradeId(Integer grade_id) {
        this.grade_id = grade_id;
    }
    public Integer getDia() {
        return dia;
    }
    public void setDia(Integer dia) {
        this.dia = dia;
    }
    public String getEntradaManha() {
        return entrada_manha;
    }
    public void setEntradaManha(String entrada_manha) {
        this.entrada_manha = entrada_manha;
    }
    public String getSaidaAlmoco() {
        return saida_almoco;
    }
    public void setSaidaAlmoco(String saida_almoco) {
        this.saida_almoco = saida_almoco;
    }
    public String getEntradaAlmoco() {
        return entrada_almoco;
    }
    public void setEntradaAlmoco(String entrada_almoco) {
        this.entrada_almoco = entrada_almoco;
    }
    public String getSaidaTarde() {
        return saida_tarde;
    }
    public void setSaidaTarde(String saida_tarde) {
        this.saida_tarde = saida_tarde;
    }
    public String getCreated_at() {
        return created_at;
    }
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
    public String getUpdated_at() {
        return updated_at;
    }
    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
    
}
