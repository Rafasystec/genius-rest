package br.com.transferr.rest;

import java.util.List;

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
import br.com.transferr.core.metadata.Coordinates;
import br.com.transferr.core.metadata.CoordinatesQuadrant;
import br.com.transferr.core.model.Car;
import br.com.transferr.core.role.RoleCar;

import br.com.transferr.rest.util.RestUtil;

import br.com.transferr.core.responses.*;
@Component
@Path("car")
public class RestCar  extends ASuperRestClass<Car> {
	
	@Autowired
	private RoleCar roleCar;
	
	public RestCar() {
		super();
}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response doGet(@PathParam("id") long id){
		Car entidade=null;
		try {
			entidade= roleCar.find(id);
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
	public Response save(Car car){
		if(car.getId()>0){
			try {
				roleCar.update(car);
			} catch (ValidationException e) {
				return RestUtil.getResponseValidationErro(e);
			}catch (Exception e) {
				registrarErroGrave(e);
				return RestUtil.getResponseErroInesperado(e);
			}
		}else{
			try {
				roleCar.insert(car);
			} catch (ValidationException e) {
				return RestUtil.getResponseValidationErro(e);
			}catch (Exception e) {
				registrarErroGrave(e);
				return RestUtil.getResponseErroInesperado(e);
			}
		}
		return Response.ok().entity(car).build();
}


	@Override
	public Response delete(long id) {
		return null;
	}

	@GET
	@Path("driver/{idDriver}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response doGetByDriver(@PathParam("idDriver") long id){
		Car entidade=null;
		try {
			entidade= roleCar.find(id);
		} catch (ValidationException e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			registrarErroGrave(e);
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok().entity(entidade).build();
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
		List<ResponseCarsOnline> carsOnline = null;
		
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
			
			
			carsOnline = roleCar.getAvailableCars(quadrant);
		}catch (Exception e) {
			registrarErroGrave(e);
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok(carsOnline, MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Path("by/user/{idUser}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response doGetByUser(@PathParam("idUser") Long id){
		Car entidade=null;
		try {
			entidade= roleCar.getCarByUser(id);
		} catch (ValidationException e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			registrarErroGrave(e);
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok().entity(entidade).build();
	}
	
	@PUT
	@Path("online")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doPutCarOnline(RequestCoordinatesUpdate request){
		try {
			roleCar.putCarOnlineOrOffline(request, true);
		} catch (ValidationException e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			registrarErroGrave(e);
			return RestUtil.getResponseErroInesperado(e);
		}
		return RestUtil.getResponseOK();
	}
	
	@PUT
	@Path("offline")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doPutCarOffline(RequestCoordinatesUpdate request){
		try {
			roleCar.putCarOnlineOrOffline(request, false);
		} catch (ValidationException e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			registrarErroGrave(e);
			return RestUtil.getResponseErroInesperado(e);
		}
		return RestUtil.getResponseOK();
	}
	
	
}
