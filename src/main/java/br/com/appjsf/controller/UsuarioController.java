package br.com.appjsf.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.appjsf.dao.UsuarioDao;
import br.com.appjsf.model.Usuario;
import br.com.appjsf.model.UsuarioWeb;
import br.com.appjsf.uteis.Util;

@Named(value = "usuarioController")
@RequestScoped
public class UsuarioController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Usuario usuario;
	@Inject
	private UsuarioDao usuarioDao;
	@Inject
	private UsuarioWeb usuarioWeb;

	public UsuarioWeb getUsuarioSession() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		return (UsuarioWeb) facesContext.getExternalContext().getSessionMap().get("usuarioAutenticado");
	}

	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/index.xhtml?faces-redirect=true";
	}

	public String efetuarLogin() {
		if (usuarioWeb.getUsuario() == null) {
			Util.MensagemInfo("Por favor informar o login");
			return null;
		} else if (usuarioWeb.getSenha() ==null) {
			Util.MensagemInfo("Por favor informar a senha");
			return null;
		} else {
			usuario = usuarioDao.validaUsuario(usuarioWeb);
			if (usuario != null) {
				usuarioWeb.setSenha(null);
				usuarioWeb.setCodigo(usuario.getCodigo());

				FacesContext facesContext = FacesContext.getCurrentInstance();
				facesContext.getExternalContext().getSessionMap().put("usuarioAutenticado", usuarioWeb);
				return "sistema/home?faces-redirect=true";
			} else {
				Util.MensagemAtencao("Não foi possivel efetuar o login com esse usuário");
				return null;
			}
		}

	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public UsuarioDao getUsuarioDao() {
		return usuarioDao;
	}

	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}

	public UsuarioWeb getUsuarioWeb() {
		return usuarioWeb;
	}

	public void setUsuarioWeb(UsuarioWeb usuarioWeb) {
		this.usuarioWeb = usuarioWeb;
	}

	public Usuario getUsuario() {
		return usuario;
	}

}
