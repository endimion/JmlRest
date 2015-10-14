package com.nikos.jml.rest;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "account")
@XmlAccessorType(XmlAccessType.PROPERTY) // this annotation is required because the class defines methods other than the standar BEAN get/set methods
public class Account implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -4415783730304744651L;
	
	private /*@spec_public non_null @*/ String name;
	private /*@spec_public non_null @*/ String password;
	private /*@spec_public non_null @*/ int balance;
	private /*@ spec_public non_null @*/ int id;
	
	/*@ ensures 
	@ getName() == na;
	@ also
	@ ensures 
	@ getPassword() == na;
	@ also
	@ ensures 
	@ getBalance() == 0;
	@ also
	@ ensures 
	@ getId() == -1;
	@*/
	public Account(/*@ non_null @*/ String na){
		this.name = na;
		this.password = na;
		this.balance = 0;
		this.id = -1;
	}//end of constructor

	//empty constructor required for the deserialization of the class by JAXB
	public Account(){}
	
	
	/*@ ensures \result == name;
	 */
	public /*@ pure non_null@*/ String getName(){return this.name;}

	/*@ ensures \result == password;
	 */
	public /*@ pure non_null@*/ String getPassword(){return this.password;}
	

	/*@ ensures \result == balance;
	 */
	public /*@ pure non_null@*/ int getBalance(){return this.balance;}
	
	/*@ ensures \result == id;
	 */
	public /*@ pure non_null@*/ int getId(){return this.id;}
	
	/*@ensures
	@ \result == b >= 0;
	@*/
	public /*@ pure non_null@*/ boolean csetBalance(int b){return b >= 0;}
	
	/*@ ensures
	 	\result == n >=  0;
	*/
	public /*@ pure @*/ boolean cremove(int n){return n > 0;}


	
	
	/*@ ensures 
	@ getName() == s;
	@ also
	@ ensures 
	@ getPassword() == \old(getPassword());
	@ also
	@ ensures 
	@ getBalance() == \old(getBalance());
	@ also
	@ ensures 
	@ getId() == \old(getId()) ;
	@*/
	public void setName(/*@ non_null @*/ String s){
		this.name = s;
	}//end of setName
	
	/*@ ensures 
	@ getName() == \old(getName());
	@ also
	@ ensures 
	@ getPassword() == s;
	@ also
	@ ensures 
	@ getBalance() == \old(getBalance());
	@ also
	@ ensures 
	@ getId() == \old(getId());
	@*/
	public void setPassword(/*@ non_null @*/ String s){
		this.password = s;
	}//end of setPassword
	
	/*@ requires csetBalance(b);
	@ ensures 
	@(
	@ getName() == \old(getName()));
	@ also
	@ requires csetBalance(b);
	@ ensures 
	@(
	@ getPassword() == \old(getPassword()));
	@ also
	@ requires csetBalance(b);
	@ ensures 
	@(
	@ getBalance() == b);
	@ also
	@ requires csetBalance(b);
	@ ensures 
	@(
	@ getId() == \old(getId()));
	@*/
	public void setBalance(int b){
		if(csetBalance(b)) this.balance = b;
	}//end of setBalance
	
	/*@ ensures 
	@ getName() == \old(getName());
	@ also
	@ ensures 
	@ getPassword() == \old(getPassword());
	@ also
	@ ensures 
	@ getBalance() == \old(getBalance());
	@ also
	@ ensures 
	@ getId() == n;
	@*/
	public void setId(int n){this.id = n;}


	

	/*@ requires n > 0; 
	 ensures 
	@ getName() == \old(getName());
	@ also
	@ ensures 
	@ getPassword() == \old(getPassword());
	@ also
	@ requires n > 0; 
	@ ensures 
	@ getBalance() == \old(getBalance()) + n;
	@ also
	@ ensures 
	@ getId() == \old(getId());
	@*/
	public void add(int n){
		if(n > 0) this.balance = this.balance + n;
	}
	
	
	/*@ requires cremove(n);
	@ ensures 
	@ getName() == \old(getName());
	@ also
	@ requires cremove(n);
	@ ensures 
	@ getPassword() == \old(getPassword());
	@ also
	@ requires cremove(n);
	@ ensures 
	@(
	@ getBalance() == \old((getBalance() - n)));
	@ also
	@ requires cremove(n);
	@ ensures 
	@ getId() == \old(getId());
	*/
	public void remove(int n){
		if(cremove(n)){
			this.balance = this.balance - n;
		}
	}
	
	

}//end of class
