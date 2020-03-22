package br.com.appjsf.dao;

import java.io.Serializable;

import javax.persistence.Query;

import br.com.appjsf.model.Usuario;
import br.com.appjsf.model.UsuarioWeb;
import br.com.appjsf.uteis.Util;

public class UsuarioDao implements Serializable {

	private static final long serialVersionUID = 1L;

	public Usuario validaUsuario(UsuarioWeb usuarioWeb) {

		try {
			Query query = Util.JpaEntityManager().createNamedQuery("Usuario.findUser", Usuario.class);

			query.setParameter("usuario", usuarioWeb.getUsuario());
			query.setParameter("senha", usuarioWeb.getSenha());

			return (Usuario) query.getSingleResult();

		} catch (Exception e) {
			return null;
		}

	}
}
