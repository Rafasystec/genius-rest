package br.com.barcadero.genius.rest;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.com.barcadero.genius.core.model.Entidade;

public interface RESTInterface <T extends Entidade> {
	
	public Response doGet(@PathParam("id") long id);
	public Response save(T entity);
	public Response delete(@PathParam("id") long id);

}
