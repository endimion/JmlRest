package model;


public class AccountResource {

	private /*@ spec_public non_null@*/  Account[] accounts ;
	/*@  public invariant accounts != null  && 0 <= accounts.length && (\typeof(accounts) == \type(Account[]) ) 
	 			&&	\elemtype(\type(Account[])) == \type(Account);
	 */
	
	public /*@ non_null @*/ Account acc ;

	public AccountResource(){
		accounts = new Account[0];
		acc = new Account("");
	}
	

	/*@ modifies \nothing;  
	     @   requires accounts != null && id < accounts.length && id >= 0 && accounts[id] != null;
	 	@ ensures \result == accounts[id];
	 	@also
	 	@   requires !( accounts != null && id < accounts.length && id >= 0 && accounts[id] != null);
	 	@ ensures \result == acc;
	 */
	public /*@ pure @*/ Account getAccount(int id){
		if(accounts != null && id < accounts.length && id >=0 && accounts[id] != null)
			return accounts[id];
		else return acc;
	}//end of getAccount
	
	/*@ ensures \result == accounts.length;
	 */
	public /*@ pure @*/ int maxAccounts(){return accounts.length;}

	
	/*@ensures
	@ \result == (n >= 0  &&  (n < maxAccounts()));
	@*/
	public /*@ pure @*/ boolean caddAccount(int n){
		return n >= 0 && n < maxAccounts() ;
	}
	
	public void deposit(int id, int id1, int id2, String s1, String s2){}
	
	
	
	/*@ requires  accounts != null &&  caddAccount(id) ;
	@ ensures 	(\forall  int id1;
	@ (  (id1 >= 0  &&  (id1  ==  id) && (id1 <  accounts.length)) ==> 
	@ getAccount(id1).getName()== s1 &&
	@ getAccount(id1).getPassword()== s2 &&
	@ getAccount(id1).getBalance()== 0 &&
	@ getAccount(id1).getId()== id )) ;
	*/
	public void addAccount(int id, /*@ non_null @*/ String na,  /*@ non_null @*/ String s1, /*@ non_null @*/ String s2){
		Account ac = new Account("na");
		
		if( ac != null &&  accounts != null && id < accounts.length && accounts.length > 0 && id >= 0){
			ac.setId(id);
			ac.setName(s1);
			ac.setPassword(s2);
			if(id < accounts.length) accounts[id] = ac;
		}
		
	}//end of addAccount
	
	
	
	
	
	
}//end of class
