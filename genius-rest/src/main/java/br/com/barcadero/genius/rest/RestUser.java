package br.com.barcadero.genius.rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.barcadero.genius.core.exceptions.ValidationException;
import br.com.barcadero.genius.core.model.Credentials;
import br.com.barcadero.genius.core.model.User;
import br.com.barcadero.genius.core.responses.ResponseLogin;
import br.com.barcadero.genius.core.role.RoleUser;
import br.com.barcadero.genius.rest.util.RestUtil;

@Component
@Path("/user")
public class RestUser extends ASuperRestClass<User>{

	
	@Autowired
	private RoleUser roleUser;
	
	public RestUser() {
		super();
}
	
	@POST
	@Path("login")
	@Produces(MediaType.APPLICATION_JSON)
	public Response doLogin(Credentials credentials){
		ResponseLogin responseLogin = null;
		try {
			responseLogin = roleUser.doLogin(credentials.getUsername(), credentials.getPassword());
		}catch(ValidationException e){
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			registrarErroGrave(e);
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok(responseLogin).build();
	}
	
	@GET
	@Path("recover/password/{email}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response recuperarSenha(@PathParam("email")String email){
		
		try {
			roleUser.recoverPassWord(email);
			return RestUtil.getResponseOK();
		} catch (ValidationException e) {
			return RestUtil.getResponseValidationErro(e);
		}catch (Exception e) {
			registrarErroGrave(e);
			return RestUtil.getResponseErroInesperado(e);
		}
		
	}
	
	
	@PUT
	@Path("change/password/{idUser}/{actualPassword}/{newpassword}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response doUpdateChangePassword(@PathParam("idUser")long idUser, @PathParam("actualPassword")String actualPassword,@PathParam("newpassword") String newPassword) {
		try {
			roleUser.changePassword(idUser, actualPassword, newPassword);
			return RestUtil.getResponseOK();
		} catch (ValidationException e) {
			return RestUtil.getResponseValidationErro(e);
		}catch (Exception e) {
			registrarErroGrave(e);
			return RestUtil.getResponseErroInesperado(e);
		}
}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response doGet(@PathParam("id") long id){
		User entidade=null;
		try {
			entidade= roleUser.find(id);
		} catch (ValidationException e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			registrarErroGrave(e);
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok().entity(entidade).build();
}

	@Override
	public Response save(User entity) {
		return null;
	}

	@Override
	public Response delete(long id) {
		return null;
	}
}
