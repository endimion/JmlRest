package model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;




@XmlRootElement(name = "account")
public class Account_old implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7331264155789392779L;
	
	
	private /*@ spec_public non_null@*/  String userName;
	private /*@ spec_public non_null@*/ String password;
	private /*@ spec_public @*/ int balance;
	
	private /*@ spec_public non_null @*/ String na = ""; 

	/*@ initially na != null;
	 
	 */
	
	// In order to do stuff with Strings because they are objects they have to be 
	// treated as objects, i.e. to check before hand that they are not null!!!!
	// 
	//
	// FOR SOME REASON IT DOESN'T PLAY WELL WITH STRINGS
	// SO MAYBE IT IS BEST WHEN YOU HAVE CONSTANTS OF HIDDEN SORT
	// TO  GIVE THEM AS PARAMETERS TO THE INITIAL STATE CONSTRUCTORS
	// LIKE HERE
	
	
	
	
	
	
	/*@ 
	 	@ ensures getBalance() == 0 && userName.equals(na) ;
	 */
	public Account_old( /*@ non_null @*/ String na){
		if(na!= null) this.userName = na;
		//this.password = s;
		this.balance = 0;
	}//end of constructor


	
	
	
	
	/*@ modifies \nothing; 
	  	ensures  \result == userName ;
   */
	public /*@ pure non_null @*/ String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/*@
	    ensures  \result == password;
	 */
	public /*@ pure non_null@*/ String getPassword() {
		return password;
	}

	
	public void setPassword(String password) {
		this.password = password;
	}

	/*@
	    ensures  \result == balance;
	 */
	public/*@ pure non_null @*/ int getBalance() {
		return balance;
	}

	
	/*@ normal_behavior
	     ensures \result == (B >= 0) ;
	 */
	public /*@ pure non_null @*/ boolean cSetBalance(int B){
		return B >= 0;
	}
	
	 /*@
	  	@ requires cSetBalance(B) ;
	  	 ensures getBalance() == B && getPassword().equals( \old(getPassword())) && getUserName() == \old(getUserName());
	  */
	public void setBalance(int B) {
		if(cSetBalance(B) ) this.balance = B;
	}
	
	
	
	
	
	
	
	
	
}//end of class