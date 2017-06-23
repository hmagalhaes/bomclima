package br.eti.hmagalhaes.bomclima.util.factory;

import br.eti.hmagalhaes.bomclima.model.dao.RegistroDAO;
import br.eti.hmagalhaes.bomclima.model.dao.UsuarioDAO;

public interface DAOFactory {

	UsuarioDAO createUsuarioDAO();
	RegistroDAO createRegistroDAO();
}
