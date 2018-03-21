package br.com.barcadero.genius.rest.util;

//import javax.ws.rs.client.Entity;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
//import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;



public class RESTClient {

	public static ResteasyClient 	client;
	public static ResteasyWebTarget target;
	/**
	 * Envia uma notificação para um dispositivo móvel.
	 * @param notification
	 * @param apiKey
	 */
//	public static void postNotification(NotificationJSON notification,String apiKey) {
//		ResteasyClient client 	= new ResteasyClientBuilder().build();
//		ResteasyWebTarget target= client.target("https://fcm.googleapis.com/fcm/send");
//		Response response 		= target.request()
//				//.header("Authorization", "key=AIzaSyD_pLyZSXPzxRMn9R-zS6V4JPP3XiHjM14")
//				.header("Authorization", "key="+apiKey)
//				.post(Entity.entity(notification, MediaType.APPLICATION_JSON));
//		if(response.getStatus() != 200){
//			System.err.println("Erro ao chamar o rest" + response.getStatus());
//		}
//		System.out.println(response.getStatus());
//		System.out.println(response.readEntity(String.class));
//	}
//	
//	public static void main(String[] args) {
//		try {
//			NotificationJSON notificationJSON = new NotificationJSON();
//			Notification notification = new Notification();
//			notification.setBody("5 to 1");
//			notification.setClick_action("http://localhost:8081");
//			notification.setIcon("firebase-logo.png");
//			notification.setTitle("Portugal vs. Denmark (2 X 0)");
//			notificationJSON.setNotification(notification);
//			notificationJSON.setTo("eRASvUv-7B0:APA91bFSDhBnL1e8TF7hz4aOM_o4l6f8H-LS7op1fFNoaELN7lGLIzJv37RCankK1f48jGL_JYouP1H-KmR91bHkXVGmk7_OH3O8RkBqAjivDkUD5zMmky_7wY4-HMOUPhNPjsWmz_Sr");
//			String apiKey = "key=AIzaSyD_pLyZSXPzxRMn9R-zS6V4JPP3XiHjM14";
//			postNotification(notificationJSON,apiKey);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
	

}
