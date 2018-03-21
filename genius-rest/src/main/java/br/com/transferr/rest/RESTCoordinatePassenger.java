package br.com.transferr.rest;

import java.util.List;

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
import br.com.transferr.core.metadata.Coordinates;
import br.com.transferr.core.metadata.CoordinatesQuadrant;
import br.com.transferr.core.model.CoordinatePassenger;
import br.com.transferr.core.responses.ResponsePassengersOnline;
import br.com.transferr.core.role.RoleCoordinatePassenger;
import br.com.transferr.rest.util.RestUtil;




@Component
@Path("pass/coordinates")
public class RESTCoordinatePassenger extends ASuperRestClass<CoordinatePassenger>{
	@Autowired
	private RoleCoordinatePassenger roleCoordinatePassenger;
	
	public RESTCoordinatePassenger() {
		
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
	
	/**
	 * <p>Obter um medico pelo seu ID</p>
	 * <p>ex url: http://hostserver/rest/medico/1</p>
	 * @param id do tipo long
	 */
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response doGet(@PathParam("id") long id){
		CoordinatePassenger entidade=null;
		try {
			entidade= roleCoordinatePassenger.find(id);
		} catch (ValidationException e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}

		return Response.ok().entity(entidade).build();
	}
	/**
	 * <p>Salva ou atualiza uma entidade de medico. Se a entidade medico vier com id ele atualiza caso contrario o 
	 * mesmo é inserido como um novo registro</p>
	 * <p>ex url: http://hostserver/rest/medico/save/{JSON do medico}</p>
	 * @param exemplo JSON da entidade medico
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response save(CoordinatePassenger exemplo){
		
		return Response.ok().entity(exemplo).build();
	}
	
	
	/**
	 * <p>Deleta um medico pelo seu ID</p>
	 * <p>ex url: http://hostserver/rest/medico/delete/1</p>
	 * <p><strong>ATENÇÃO: Não poderá ser desfeita</strong></p>
	 * @param id do medico a ser deletado
	 */
	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") long id){
		try {
			roleCoordinatePassenger.delete(id);
		} catch (ValidationException e) {
			return RestUtil.getResponseValidationErro(e);
		}catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok().build();
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doUpdate(CoordinatePassenger coordinates){
		try {
			roleCoordinatePassenger.update(coordinates);
		} catch (ValidationException e) {
			return RestUtil.getResponseValidationErro(e);
		}catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok().entity(coordinates).build();
	}
	
	@GET
	@Path("online/{coord1lon}/{coord1lat}/{coord2lon}/{coord2lat}/{coord3lon}/{coord3lat}/{coord4lon}/{coord4lat}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response doGetCarsOnline(
			@PathParam("coord1lon") double coord1lon,
			@PathParam("coord1lat")       double coord1lat,
			@PathParam("coord2lon")       double coord2lon,
			@PathParam("coord2lat")       double coord2lat,
			@PathParam("coord3lon")       double coord3lon,
			@PathParam("coord3lat")       double coord3lat,
			@PathParam("coord4lon")       double coord4lon,
				@PathParam("coord4lat") double coord4lat) {
		List<ResponsePassengersOnline> carsOnline = null;
		
		try {
			
			CoordinatesQuadrant quadrant = new CoordinatesQuadrant();
			Coordinates coordFarLeft = new Coordinates();
			coordFarLeft.setLatitude(coord1lat);
			coordFarLeft.setLongitude(coord1lon);
			quadrant.setCoordFarLeft(coordFarLeft );
			Coordinates coordFarRight = new Coordinates();
			coordFarRight.setLatitude(coord2lat);
			coordFarRight.setLongitude(coord2lon);
			quadrant.setCoordFarRight(coordFarRight );
			Coordinates coordNearLeft = new Coordinates();
			coordNearLeft.setLatitude(coord3lat);
			coordNearLeft.setLongitude(coord3lon);
			quadrant.setCoordNearLeft(coordNearLeft );
			
			Coordinates coordNearRight = new Coordinates();
			coordNearRight.setLatitude(coord4lat);
			coordNearRight.setLongitude(coord4lon);
			quadrant.setCoordNearRight(coordNearRight );
			
			
			carsOnline = roleCoordinatePassenger.getPassengersOnline(quadrant);
		}catch (Exception e) {
			registrarErroGrave(e);
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok(carsOnline, MediaType.APPLICATION_JSON).build();
	}
	


}
