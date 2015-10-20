package com.nikos.jml.rest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Account;
import model.AccountModel;


/*
 * A singleton in Java is a class for which only one instance can be created provides a global point of access this instance. 
 * The singleton pattern describe how this can be archived. 
 */


public enum  AccountsModelSingleton {

	instance;
	
	private AccountModel accountsMod ;
	private DbHelper dbh;
	private Account[] dbAccounts;
	
	
	private AccountsModelSingleton(){
		accountsMod = new AccountModel();
		dbh = new DbHelper();
		try {
			dbh.initConnection(); //initalize the connection
			int accNum = dbh.getNumberOfAccounts(); //get the number of accounts already in the database
			accountsMod.setMaxSize(accNum); // intialize the size of the array in the accounts modelobject
			
			dbAccounts = dbh.getAccountsAsArray();
			
			//we retrieve the existing accounts from the database and add them to our model of accounts
			for(Account ac : dbAccounts){
				if(ac != null)
				accountsMod.addAccount(ac.getBalance(),ac.getId(), ac.getName(),ac.getPassword());
			}//end of looping through the accounts of the database
			
			
		} catch (SQLException e) {		e.printStackTrace();	}
	}//end of constructor of the signleton
	
	
	
	/**
	 * 
	 * @return the AccountModel object of the system
	 */
	public AccountModel getAccountsModel(){
		return accountsMod;
	}//end of getAccountsModel
	
	
	
	/**
	 * 
	 * @return an account array containing all the accounts found in the database
	 * at intilization
	 */
	public  Account[] getDbAccounts(){
		dbAccounts = new Account[accountsMod.maxAccounts()];
		for(int i =0 ; i < accountsMod.maxAccounts(); i++){
			dbAccounts[i] = accountsMod.getAccount(i);
		}
		
		return dbAccounts;
	}
	
	
	
	
	/**
	 * inner class that provides functionallity to connect to the 
	 * Db and do appropriate stuff
	 * @author nikos
	 *
	 */
	private class DbHelper {

		Connection conn ;
		//	constructor
		// the parameter should be a String which contains the
		// absolute path to the database (access db)
		public DbHelper(){}
			
		/**
		 * initializes the connection to the database 
		 */
		public void initConnection() throws SQLException{
			int res = dbConnect("localhost/AccountsJML", "root","panathinaikos"); 
				if(res != 1) { 
					System.out.println("DbHelper.initConnection : Connection failed") ;
					throw new SQLException();
				}else {		
					System.out.println("DbHelper.initConnection : Connection succesful!");
				}
		}//end of initConnection
			
		//public class which builds a connection to the given at 
		// the constructor data base
		private int dbConnect(String dbUri, String uName, String uPass){
				try
		        {
		            Class.forName("com.mysql.jdbc.Driver").newInstance();
		            conn = DriverManager.getConnection
		            		("jdbc:mysql://"+dbUri+"?useUnicode=true&characterEncoding=UTF-8&user="+uName+"&password="+uPass); 
		            return 1;
		        }catch(Exception ex)
		        {
		        	ex.printStackTrace();
		        	return -1;
		        }
			}//END of accessConnect
		
		/**
		 * 
		 * @return the number or records that exist in the database table
		 * JmlAccounts
		 */
		private int getNumberOfAccounts(){
			String sql = " SELECT COUNT(*) FROM Accounts;";
			ResultSet rs = null;
			if(conn != null){
					try{
						Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
								ResultSet.CONCUR_READ_ONLY);
						rs  = stm.executeQuery(sql);
						while(rs.next()){
							System.out.println("AccountsModelSingleton.DbHelper.getNumOfAccounts::  the size is " + rs.getInt(1));
							return rs.getInt(1);
						}//end of  rs.next loop
						
					}catch(Exception e){
						System.out.println("AccountsModelSingleton. Error executing the query");
						e.printStackTrace();
					}//end of catching the exception
			}//end if the connection is not initiallized properly
			return -1;
		}//end of getNumberOfAccounts
		

		
		/**
		 * 
		 * @return an Array of Accounts containing the elements of the Accounts table as Account objects
		 */
		private Account[] getAccountsAsArray(){
			String sql = "Select * from Accounts";
			ResultSet rs = null;
			int numOfAccounts = getNumberOfAccounts();
			
			if(numOfAccounts >= 0){
				Account[] result = new Account[numOfAccounts];
				
				if(conn != null){
					try{
						Statement stm = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
								ResultSet.CONCUR_READ_ONLY);
						rs  = stm.executeQuery(sql);
						
						while(rs.next()){
							int id = rs.getInt("Id");
							String name = rs.getString("UserName");
							String pass = rs.getString("Pass");
							int balance = rs.getInt("Balance");
							accountsMod.addAccount(balance, id, name,pass);
						}//end of looping through the elements of the resultset
						
					}catch(Exception e){e.printStackTrace();}
				}//end if the connection was initialized correctly
				return result;
			}//end if the number of accounts is >= 0
			return null;
		}//end of getAccountsArray
		
		
		
	}//end of inner private class
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}//end of class
