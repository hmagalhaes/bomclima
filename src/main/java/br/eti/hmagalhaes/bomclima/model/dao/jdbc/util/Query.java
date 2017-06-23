package br.eti.hmagalhaes.bomclima.model.dao.jdbc.util;

import org.apache.commons.lang3.ArrayUtils;

public class Query {

	private final String sql;
	private final Object[] params;

	public Query(String sql, Object... params) {
		this.sql = sql;
		this.params = params;
	}

	public String getSql() {
		return sql;
	}

	public Object[] getParams() {
		return params;
	}

	public boolean hasParams() {
		return !ArrayUtils.isEmpty(params);
	}
}
