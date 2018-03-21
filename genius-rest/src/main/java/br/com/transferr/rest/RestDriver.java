package br.com.transferr.rest;

import javax.ws.rs.Consumes;
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

import br.com.transferr.core.constants.ConstantServicesREST;
import br.com.transferr.core.exceptions.ValidationException;
import br.com.transferr.core.model.Driver;
import br.com.transferr.core.role.RoleDriver;
import br.com.transferr.core.util.AnexoPhoto;
import br.com.transferr.rest.util.RestUtil;

@Component
@Path("driver")
public class RestDriver extends ASuperRestClass<Driver> {


	@Autowired
	private RoleDriver roleDriver;

	public RestDriver() {
		super();
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response doGet(@PathParam("id") long id){
		Driver entidade=null;
		try {
			entidade= roleDriver.find(id);
		} catch (ValidationException e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			registrarErroGrave(e);
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok().entity(entidade).build();
	}

	@POST
	@Path(ConstantServicesREST.REST_SAVE)
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response save(Driver driver){
		if(driver.getId()>0){
			try {
				roleDriver.update(driver);
			} catch (ValidationException e) {
				return RestUtil.getResponseValidationErro(e);
			}catch (Exception e) {
				registrarErroGrave(e);
				return RestUtil.getResponseErroInesperado(e);
			}
		}else{
			try {
				roleDriver.insert(driver);
			} catch (ValidationException e) {
				return RestUtil.getResponseValidationErro(e);
			}catch (Exception e) {
				registrarErroGrave(e);
				return RestUtil.getResponseErroInesperado(e);
			}
		}
		return Response.ok().entity(driver).build();
	}


	@Override
	public Response delete(long id) {
		return null;
	}

	@PUT
	@Path("profile/photo")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveProfilePhoto(AnexoPhoto anexo){
		try {
			roleDriver.saveProfilePhotosOnFileSystem(anexo.getIdentity() , anexo.getAnexoBase64());
			return RestUtil.getResponseOK();
		}catch (Exception e) {
			registrarErroGrave(e);
			return RestUtil.getResponseErroInesperado(e);
		}

	}	

	@GET
	@Path("by/car/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response doGetDriverByCar(@PathParam("id") Long id){
		Driver entidade=null;
		try {
			entidade= roleDriver.getDriverByCar(id);
		} catch (ValidationException e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			registrarErroGrave(e);
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok().entity(entidade).build();
	}



}
