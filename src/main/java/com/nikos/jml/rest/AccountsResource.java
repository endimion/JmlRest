package com.nikos.jml.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import model.Account;

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
				return accounts;
	}//end of getAllAccounts

	
	@GET
	@Produces(MediaType.TEXT_XML)
	@Path("{id}")
	public Account getAccountById(@PathParam("id") String id){
		Account resAccount = null;
		for (Account ac: AccountsModelSingleton.instance.getDbAccounts()){
			if(String.valueOf(ac.getId()).equals(id) ){
				resAccount = ac;
			}
		}//end of looping through the accounts
		
		return resAccount;
	}
	
	
	
	
	
	
	@GET
	@ Path("hello")
	public Response respond(){
		 String output = "Hello from: " ;
	        return Response.status(200).entity(output).build();
	}
	
	
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void newAccount(@FormParam("name") String name, @FormParam("password") String password, 
			@FormParam("balance")  String balance){
		
		if(name != null && password!= null && balance != null){
			int b = Integer.parseInt(balance);
			int pos = AccountsModelSingleton.instance.getAccountsModel().maxAccounts();
			String s1 = name;
			String s2 = password;
			AccountsModelSingleton.instance.getAccountsModel().addAccount(b, pos, s1, s2);
		}
	}//end of newAccount
		
		
		@POST
		@Produces(MediaType.TEXT_HTML)
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		@Path("tansfer")
		public void transfer(@FormParam("fromId") String fromId, @FormParam("toId") String toId,
				@FormParam("fromName") String fromName, @FormParam("fromPassword") String fromPassword, 
				@FormParam("amount")  String amount){
			
			System.out.println("AccountRescource.transfer:: TransferCalled");
			
			if(fromName != null && fromPassword!= null && amount != null){
				
				int b = Integer.parseInt(amount);
				int fId = Integer.parseInt(fromId);
				int destId = Integer.parseInt(toId);
				
				
				AccountsModelSingleton.instance.
						getAccountsModel().deposit(fId, destId, b, fromName, fromPassword);
			}
	}//end of transfer
	




}
