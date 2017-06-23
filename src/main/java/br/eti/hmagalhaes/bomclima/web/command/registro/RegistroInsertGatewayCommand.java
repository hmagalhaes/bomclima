package br.eti.hmagalhaes.bomclima.web.command.registro;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.eti.hmagalhaes.bomclima.model.entity.TipoRegistro;
import br.eti.hmagalhaes.bomclima.web.command.ServletCommand;
import br.eti.hmagalhaes.bomclima.web.util.RequestUtils;
import br.eti.hmagalhaes.bomclima.web.util.constant.ViewMapping;

@ApplicationScoped
public class RegistroInsertGatewayCommand implements ServletCommand {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("Iniciando serviço de inclusão de registro");

		final List<TipoRegistro> tipoList = Arrays.asList(TipoRegistro.values());

		logger.info("Chamando view => " + ViewMapping.REGISTER_INSERT_GATEWAY);

		request.setAttribute("tipoList", tipoList);

		RequestUtils.requestForward(request, response, ViewMapping.REGISTER_INSERT_GATEWAY);
	}
}
