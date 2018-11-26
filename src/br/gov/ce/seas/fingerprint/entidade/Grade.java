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
public class Grade {
    
    private Integer id;
    private String nome;
    private Integer tolerancia;
    private Integer tolerancia_periodo;
    private boolean restringir_periodo;
    private boolean restringir_horarios;
    private Integer horas_diarias;
    private boolean status_restringir_periodo;
    private String created_at;
    private String updated_at;
    
    public Integer getId() {
            return id;
    }
    public void setId(Integer id) {
            this.id = id;
    }
    public String getNome() {
            return nome;
    }
    public void setNome(String nome) {
            this.nome = nome;
    }
    public Integer getTolerancia() {
            return tolerancia;
    }
    public void setTolerancia(Integer tolerancia) {
            this.tolerancia = tolerancia;
    }
    public Integer getToleranciaPeriodo() {
            return tolerancia_periodo;
    }
    public void setToleranciaPeriodo(Integer toleranciaPeriodo) {
            this.tolerancia_periodo = toleranciaPeriodo;
    }
    public boolean getRestringirPeriodo() {
            return restringir_periodo;
    }
    public void setRestringirPeriodo(boolean restringir_periodo) {
            this.restringir_periodo = restringir_periodo;
    }
    public boolean getRestringirHorarios() {
            return restringir_horarios;
    }
    public void setRestringirHorarios(boolean restringir_horarios) {
            this.restringir_horarios = restringir_horarios;
    }
    public Integer getHorasDiarias() {
            return horas_diarias;
    }
    public void setHorasDiarias(Integer horas_diarias) {
            this.horas_diarias = horas_diarias;
    }
    public boolean getStatusRestringirPeriodo() {
            return status_restringir_periodo;
    }
    public void setStatusRestringirPeriodo(boolean status_restringir_periodo) {
            this.status_restringir_periodo = status_restringir_periodo;
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
