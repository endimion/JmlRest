package com.nikos.jml.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

// full path to view methods on local host http://localhost:8080/JmlRest/jmlrest/WebAccounts

@Path("/WebAccounts")
public class AccountsResource {
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	
	//Return a list of all accounts of the system
	@GET
	@Produces(MediaType.TEXT_XML)
	public  List<Account> getAllAccounts(){
		List<Account> accounts = new ArrayList<Account>();
		for (Account ac: AccountsModelSingleton.instance.getDbAccounts()){
			accounts.add(ac);
		}
		
		Account ac = new Account("");
		ac.setId(1);
		ac.setBalance(0);
		ac.setName("Nikos");
		ac.setPassword("adsfasd");
		//accounts.add(ac);
		System.out.println("AccountsResource:: called" + ac.toString());
		

		return accounts;
	}//end of getAllAccounts

	
	
	
	
	@GET
	@ Path("hello")
	public Response respond(){
		 String output = "Hello from: " ;
	        return Response.status(200).entity(output).build();
	}
	
	
	
	




}
