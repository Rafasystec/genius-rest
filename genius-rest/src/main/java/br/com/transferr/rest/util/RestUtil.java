package br.com.transferr.rest.util;

import javax.ws.rs.core.Response;

import br.com.transferr.core.enums.EnumFailSystem;
import br.com.transferr.core.exceptions.ValidationException;
import br.com.transferr.core.responses.ResponseOK;





public class RestUtil {

	public static Response getResponseValidationErro(ValidationException ex){
		//ex.printStackTrace();
		return getResponseValidationErro(ex.getMessage(),ex.getCampoErro());
	}
	
	public static Response getResponseValidationErro(String mensagem,String campo){
		MapErroRetornoRest mapErroRetornoRest=new MapErroRetornoRest(EnumFailSystem.ERRO_VALIDACAO, mensagem);
		String [] field= {campo};
		mapErroRetornoRest.setField(field);
		return Response.status(Response.Status.BAD_REQUEST).entity(mapErroRetornoRest).build();// VER O TAMANHO DO PROBLEMA EM TROCAR BAD_REQUEST POR OK
	} 
	
	public static Response getResponseValidationErro(String mensagem){
		MapErroRetornoRest mapErroRetornoRest=new MapErroRetornoRest(EnumFailSystem.ERRO_VALIDACAO, mensagem);
	
		return Response.status(Response.Status.BAD_REQUEST).entity(mapErroRetornoRest).build();
	} 
	
	
	public static Response getResponseErroInesperado(Throwable ex){
		return getResponseErroInesperado(ex.getMessage()); 
	}
	
	public static Response getResponseErroInesperado(String mensagem){
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new MapErroRetornoRest(EnumFailSystem.ERRO_INESPERADO,mensagem)).build();
	}
	
	public static Response getResponseOK(){
		return Response.status(Response.Status.OK).entity(new ResponseOK("OK")).build();
	}
	
	public static Response getResponseUnauthorized(String mensagem){
		return Response.status(Response.Status.UNAUTHORIZED).entity(new MapErroRetornoRest(EnumFailSystem.ERRO_INESPERADO,mensagem)).build();
	}
	
	public static Response getResponseUnauthorized(Throwable ex){
		return getResponseUnauthorized(ex.getMessage());
	}

	  
}
