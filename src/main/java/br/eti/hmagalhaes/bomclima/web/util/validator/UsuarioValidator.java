package br.eti.hmagalhaes.bomclima.web.util.validator;

import static org.apache.commons.lang3.StringUtils.isBlank;

import javax.enterprise.context.ApplicationScoped;

import br.eti.hmagalhaes.bomclima.model.entity.Usuario;

@ApplicationScoped
public class UsuarioValidator {

	/**
	 * @return Mensagem de erro, ou {@code null} se não houver erro.
	 */
	public String validate(final Usuario usuario) {
		if (usuario == null) {
			return "Os dados do usuário não foram informados";
		}
		if (usuario.getId() < 0) {
			return "O ID do usuário é inválido";
		}
		if (isBlank(usuario.getLogin())) {
			return "Login não pode ficar em branco";
		}
		if (isBlank(usuario.getSenha())) {
			return "Senha não pode ficar em branco";
		}
		if (isBlank(usuario.getNome())) {
			return "Nome não pode ficar em branco";
		}
		return null;
	}
}
