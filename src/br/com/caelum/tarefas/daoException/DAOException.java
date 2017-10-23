package br.com.caelum.tarefas.daoException;

import java.sql.SQLException;

public class DAOException extends RuntimeException {
	public final static int _FAIL_TO_INSERT = 4;
	public final static int _UPDATE_FAILED = 56;
	public final static int _SQL_ERROR = 1276;
	public static final int _ERROR_GETTING_LIST = 68365;
	public static final int _ERRO_NA_PESQUISA = 5;
	private int errorCode;
	public DAOException(SQLException e, int errorCode, String message) {
		super(message, e);
		this.errorCode = errorCode;
	}
	public int getErrorCode() {
		return errorCode;
	}
}
