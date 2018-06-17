package br.com.barcadero.genius.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.context.ApplicationContext;

import br.com.barcadero.genius.persistence.model.Entidade;



/**
 * Super Classe para reutilizar metodos nas outras classes REST
 * @author Rafael Rocha
 * @since versao 1.0 - dia 02/12/2016 as 09:42.
 */
public abstract class ASuperRestClass <T extends Entidade> implements RESTInterface<T>{
	private static ApplicationContext context;
	@Context
	protected HttpServletRequest request;
	
	public static void setContext(ApplicationContext context) {
		ASuperRestClass.context = context;
	}
	

	/**
	 * 
	 * @param e
	 * @return
	 */
	protected Response getResponseException(Exception e) {
		StringBuilder error = new StringBuilder(e.getMessage());
		e.printStackTrace();
		if(e.getCause() != null){
			error.append(e.getCause().getMessage());
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(error.toString()).build();
	}

	public ApplicationContext getContext() {
		return context;
	}
	/**
	 * Chamar rest para registrar erros no servidor.
	 * @param e
	 */
	protected void registrarErroGrave(Throwable e) {
		try {
			e.printStackTrace();
			//RESTClient.onlyPostEntity(e, "support/register");
		} catch (Exception e1) {		
			e1.printStackTrace();
		}
	}
}
