package br.eti.hmagalhaes.bomclima.model.dao.jdbc.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class JDBCUtil {

	public static java.sql.Date toSQLDate(final Date date) {
		return date == null ? null : new java.sql.Date(date.getTime());
	}

	public static boolean isNullColumn(final ResultSet rs, final String columnName) throws SQLException {
		return null == rs.getObject(columnName);
	}
}
