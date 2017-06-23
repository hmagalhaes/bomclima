package br.eti.hmagalhaes.bomclima.model.dao;

import java.util.List;

import br.eti.hmagalhaes.bomclima.model.dto.RegistroSearchParams;
import br.eti.hmagalhaes.bomclima.model.entity.Registro;

public interface RegistroDAO extends GenericDAO<Registro, Long> {

	List<Registro> find(RegistroSearchParams params);

}
