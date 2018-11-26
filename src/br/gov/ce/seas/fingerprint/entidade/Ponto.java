/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.ce.seas.fingerprint.entidade;

import java.util.Date;

/**
 *
 * @author Eduardo
 */
public class Ponto {
    private Integer id;
    private Integer colaboradorId;
    private Date data;

    public Integer getId() {
            return id;
    }
    public void setId(Integer id) {
            this.id = id;
    }
    public Integer getColaboradorId() {
            return colaboradorId;
    }
    public void setColaboradorId(Integer colaboradorId) {
            this.colaboradorId = colaboradorId;
    }
    public Date getData() {
            return data;
    }
    public void setData(Date data) {
            this.data = data;
    }
    
}
