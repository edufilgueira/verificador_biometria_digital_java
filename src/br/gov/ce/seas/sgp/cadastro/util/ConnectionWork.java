package br.gov.ce.seas.sgp.cadastro.util;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionWork<T> {

    T execute(Connection conn, String esquema) throws SQLException;
}
