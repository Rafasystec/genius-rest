package br.com.transferr.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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

import br.com.transferr.core.exceptions.ValidationException;
import br.com.transferr.core.model.CoordinateCar;
import br.com.transferr.core.responses.RequestCoordinatesUpdate;
import br.com.transferr.core.role.RoleCoordinateCar;
import br.com.transferr.rest.util.RestUtil;




@Component
@Path("/coordinatescar")
public class RESTCoordinateCar extends ASuperRestClass<CoordinateCar>{
	@Autowired
	private RoleCoordinateCar roleCoordinate;
	
	public RESTCoordinateCar() {
		
	}
	/**
	 * Verifica se tal serviço REST está on-line
	 * @return retorna OK se o servico ta no ar
	 */
	@GET
	@Path("on")
	@Produces(MediaType.APPLICATION_JSON)
	public Response doGetOn(){
		System.out.println("Server is OK!!!");
		return Response.ok("OK").build();
	}
	

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response doGet(@PathParam("id") long id){
		CoordinateCar entidade=null;
		try {
			entidade= roleCoordinate.find(id);
		} catch (ValidationException e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}

		return Response.ok().entity(entidade).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response save(CoordinateCar exemplo){
		if(exemplo.getId()>0){
			try {
				roleCoordinate.update(exemplo);
			} catch (ValidationException e) {
				return RestUtil.getResponseValidationErro(e);
			}catch (Exception e) {
				return RestUtil.getResponseErroInesperado(e);
			}
		}else{
			try {
				roleCoordinate.insert(exemplo);
			} catch (ValidationException e) {
				return RestUtil.getResponseValidationErro(e);
			}catch (Exception e) {
				return RestUtil.getResponseErroInesperado(e);
			}
		}
		return Response.ok().entity(exemplo).build();
	}
	
	

	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") long id){
		try {
			roleCoordinate.delete(id);
		} catch (ValidationException e) {
			return RestUtil.getResponseValidationErro(e);
		}catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok().build();
	}
	
	
	@PUT
	@Path("actual/location")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateLocation(RequestCoordinatesUpdate request){
		try {
			roleCoordinate.updateCoordinateByCar(request.getIdCar().longValue(), request.getLatitude(), request.getLongitude());
		} catch (ValidationException e) {
			return RestUtil.getResponseValidationErro(e);
		}catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
	
		return Response.ok().entity(request).build();
	}


}
