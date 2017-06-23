package br.eti.hmagalhaes.bomclima.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ServletCommand {

	/**
	 * Executa a ação do comando.
	 */
	void run(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException;
}
